# ECP Strava Integration 🚴‍♂️

Welcome to the ECP Strava Integration project! This project integrates with the Strava API to authenticate athletes and manage their activities. The application is built using Java, Spring Boot, and Maven.

## Features ✨

- **Strava Authentication**: Authenticate athletes using Strava OAuth.
- **Activity Management**: Fetch and manage athlete activities.
- **Team Management**: Create and manage teams for athletes.
- **Leaderboards**: Display leaderboards for athletes and teams.

## Architecture Overview 🏗️

The application consists of the following components:

1. **Spring Boot Application**: The main application that handles authentication, activity management, and team management.
2. **Strava Webhooks**: Used to receive real-time updates about athlete activities.
3. **Cloud Run Functions**: Hosts the webhook endpoint to process activity data.

### Strava Webhooks 🚀

Strava Webhooks are used to receive real-time updates about athlete activities. When an athlete records a new activity, Strava sends a notification to our webhook endpoint hosted on Cloud Run Functions. The webhook endpoint processes the activity data and updates the athlete's information in the database.

### Application Flow 🔄

1. **Authentication**: Athletes authenticate with Strava using OAuth. The `StravaController` handles the OAuth flow and exchanges the authorization code for an access token.
2. **Activity Data**: Once authenticated, the application can fetch and manage the athlete's activities.
3. **Webhooks**: Strava sends activity updates to the webhook endpoint on Cloud Run Functions. The endpoint processes the data and updates the athlete's information in the database.
4. **Team Management**: Athletes can be assigned to teams, and the application manages team information and leaderboards.

## Getting Started 🚀

### Prerequisites

- Java 21
- Maven
- Google Cloud SDK

### Setup

1. **Clone the repository**:
   ```sh
   git clone https://github.com/robeves95/ecp-strava.git
   cd ecp-strava
   ```

2. **Configure application properties**:
   Update the `src/main/resources/application.properties` file with your Strava and database credentials.

3. **Build and run the application**:
   ```sh
   mvn clean package
   mvn spring-boot:run
   ```

### Deployment

The application is deployed to Google App Engine. The deployment process is automated using GitHub Actions. The workflow file `.github/workflows/maven.yml` defines the steps to build and deploy the application.

## Contributing 🤝

Contributions are welcome! Please open an issue or submit a pull request.

## License 📄

This project is licensed under the MIT License.

---

Happy coding! 💻