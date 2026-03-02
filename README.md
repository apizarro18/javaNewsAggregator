📰 Java News Aggregator

Full-Stack Sentiment News Explorer

A full-stack application built during my Junior year to aggregate, filter, and display global news. This project bridges a Spring Boot backend with a React + Tailwind frontend, focusing on secure user authentication and clean API integration.

🚀 The Tech Stack

    Frontend: React 18, Tailwind CSS (v4), React Router v7

    Backend: Java 21, Spring Boot 3.x, Spring Security

    Database: H2 (Development) / SQLite Cloud (Production)

    API: [Insert Name of News API]

🛠️ Key Features

    Secure Signup: Implemented server-side validation using jakarta.validation to handle user errors gracefully.

    Responsive Design: A custom-styled UI using Tailwind CSS featuring a video-hero landing page.

    Clean Architecture: RESTful API design with separation of concerns between Controllers, Services, and Repositories.

    Environment Safety: Sensitive API keys and database credentials are managed via environment variables to ensure security.

🏗️ System Architecture

The app follows a decoupled architecture:

    React Frontend captures user input and handles client-side routing.

    Spring Boot API validates data and communicates with the News Service.

    H2/SQLite persists user data and preferences.

📈 Future Roadmap

    [ ] Implement JWT (JSON Web Tokens) for persistent login sessions.

    [ ] Add a "Saved Articles" feature for registered users.

    [ ] Integrate a sentiment analysis library to color-code news by mood
