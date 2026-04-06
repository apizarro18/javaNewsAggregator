# Task List

## Tier 1 — Connect the existing pieces (MVP)

- [ ] **JWT validation on protected routes** — Add a `JwtFilter` that intercepts requests and authenticates the user from the token header. The backend generates tokens but never validates them on incoming requests.

- [ ] **Persist the JWT in the frontend** — After login, store the token in `localStorage` (or a cookie). Attach it as a `Bearer` header on all subsequent API calls.

- [ ] **Wire TopicPicker to the backend** — Add a `POST /api/users/{id}/topics` endpoint and have `TopicPicker.jsx` call it after signup to actually invoke `followTopic()` in `UserService`.

- [ ] **Build the personalized news feed** — Add a `GET /api/users/{id}/feed` endpoint that loads the logged-in user's followed topics, calls `NewsService.fetchNews()` for each, and returns a merged, deduplicated article list.

- [ ] **Build the Home page** — Make `Home.jsx` fetch from `/api/users/{id}/feed` using the stored JWT and render the articles.

---

## Tier 2 — Make it feel like a real newspaper

- [ ] **Daily digest / scheduled fetch** — Use Spring's `@Scheduled` to pre-fetch news for all users' topics once per day and cache results, making the "daily newspaper" concept real instead of on-demand fetching.

- [ ] **Article detail view** — Let users click an article to read a summary or get redirected to the source URL.

- [ ] **Saved articles** — Add a `saved_articles` join table, a save button on the frontend, and endpoints to save/unsave/list saved articles.

- [ ] **Onboarding flow** — Connect the pages: signup → TopicPicker → Home. Right now these are disconnected routes with no guided flow.

---

## Tier 3 — Long-term (Bias Algorithm)

- [ ] **Source bias tagging** — Create a `Source` entity with a `biasRating` field (e.g., left/center/right). Tag articles by source domain when pulling from NewsAPI. Seed bias data from a public dataset like AllSides or Ad Fontes.

- [ ] **Balanced feed mode** — Add a `balancedMode` user preference. When enabled, fetch articles from at least one left-leaning and one right-leaning source per topic.

- [ ] **Bias visualization** — Show a small indicator (color or label) on each article card in the frontend displaying where the source falls on the political spectrum.
