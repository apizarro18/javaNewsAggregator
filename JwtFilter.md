# JwtFilter

`JwtFilter` extends `OncePerRequestFilter`, which guarantees it runs exactly once per request. Every HTTP request that hits the backend passes through `doFilterInternal` before reaching any controller. Think of it as a security checkpoint at the door.

## Flow

### Step 1 — Check for a token
```java
String authHeader = request.getHeader("Authorization");
if (authHeader == null || !authHeader.startsWith("Bearer ")) {
    filterChain.doFilter(request, response);
    return;
}
```
Look for an `Authorization` header. If it's missing or doesn't start with `"Bearer "`, let the request through unauthenticated and return early. Spring Security handles it from there — public routes pass, protected routes get rejected.

### Step 2 — Extract the raw token
```java
String token = authHeader.substring(7);
```
The header looks like `"Bearer eyJhbGci..."`. Strip the first 7 characters (`"Bearer "`) to get the raw token.

### Step 3 — Verify and parse the token
```java
String username = Jwts.parser()
        .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
        .build()
        .parseSignedClaims(token)
        .getPayload()
        .getSubject();
```
This does two things at once — verifies the token wasn't tampered with (using the secret key from `application.properties`), and extracts the username that was embedded when the token was generated in `UserService.generateToken()`. If the token is fake or expired, this throws an exception.

### Step 4 — Tell Spring Security who this is
```java
UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
        username, null, List.of()
);
SecurityContextHolder.getContext().setAuthentication(authentication);
```
`SecurityContextHolder` is Spring's way of tracking who is currently logged in for this request. An authentication object is created with the username and stored there. Any controller can then call `SecurityContextHolder.getContext().getAuthentication().getName()` to get the username.

### Step 5 — Pass the request along
```java
filterChain.doFilter(request, response);
```
Done with the check. This hands the request off to the next filter in the chain, eventually reaching the controller.

## Mental Model

This filter runs before your controller, silently attaches an identity to the request, and your controller can read that identity. The filter doesn't block or allow requests itself — it just annotates them with who the user is, and Spring Security enforces access rules downstream.
