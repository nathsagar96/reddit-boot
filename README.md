# **Reddit Boot Backend** 🚀

A **scalable, secure, and efficient** backend system for a Reddit-like web application built using **Java Spring Boot 3**, **PostgreSQL**, and **Docker**.
The backend supports **user management**, **post creation**, **subreddit management**, and more, offering a robust API that powers the platform.

## **Key Features** 📋

- **User Management**: Secure user registration, login, JWT-based authentication, password reset, and profile
  management.
- **Subreddit Management**: Users can create and manage subreddits, with support for multiple subreddit admins.
- **Post Management**: Users can create, edit, and delete posts, with rich-text formatting and subreddit categorization.
- **Voting System**: A voting system allows users to upvote and downvote posts and comments.
- **Commenting System**: Nested, threaded commenting allows users to reply to posts and other comments.
- **Comprehensive API Documentation**: Interactive Swagger UI powered by SpringDoc OpenAPI.

---

## **Technologies Used** 🛠

- **Java 21** and **Spring Boot 3**: Core framework for building the backend.
- **Spring Security**: Provides authentication and authorization via JWT.
- **PostgreSQL**: Relational database to manage data.
- **Docker**: Containerization for easy deployment.
- **Maven**: Dependency management and build tool.
- **SpringDoc OpenAPI**: Automatically generates interactive API documentation via Swagger UI.

---

## **Installation** 💻

### Prerequisites

- **Java 21** installed on your machine.
- **Maven** installed for building and running the project.
- **Docker** for running Mail Server and PostgreSQL.

### Step-by-Step Instructions

1. **Clone the Repository**:

    ```bash
    git clone https://github.com/nathsagar96/reddit-boot-backend.git
    cd reddit-boot-backend
    ```

2. **Set Up PostgreSQL**:
    - Create a new PostgreSQL database named `reddit`.
    - Configure your database connection settings in `application.properties` or `application.yml`:

    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/reddit_clone
    spring.datasource.username=your-username
    spring.datasource.password=your-password
    ```

3. **Build the Project**:

    ```bash
    mvn clean install
    ```

4. **Run the Application**:

      ```bash
      mvn spring-boot:run
      ```

5. **Access Swagger UI**:
    - Once the application is running,
      visit [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) to explore the API
      documentation.

---

## **Usage** 🧑‍💻

### User Registration & Login

- Register a new user:

    ```bash
    POST /api/auth/register
    {
      "username": "johndoe",
      "email": "johndoe@example.com",
      "password": "password123"
    }
    ```

- Login to get a JWT token:

    ```bash
    POST /api/auth/login
    {
      "username": "johndoe",
      "password": "password123"
    }
    ```

  Use the returned JWT token to authenticate other API requests by adding it to the `Authorization` header:
  `Bearer <JWT_TOKEN>`.

### Create a Subreddit

- Create a new subreddit:

    ```bash
    POST /api/subreddits
    {
      "name": "technology",
      "description": "A subreddit for all things tech!"
    }
    ```

### Create a Post

- Create a new post in a subreddit:

    ```bash
    POST /api/posts
    {
      "title": "My First Post",
      "content": "This is my first post on Reddit Boot!",
      "subredditId": 1
    }
    ```

### Upvote a Post

- Upvote a post:

    ```bash
    POST /api/posts/1/vote?username=janedoe&voteType=UPVOTE
    ```

---

## **Contributing** 🤝

We welcome contributions to make this project better! Here's how you can contribute:

1. **Report Bugs**: If you encounter any bugs,
   please [create an issue](https://github.com/nathsagar96/reddit-boot-backend/issues) with detailed information.
2. **Suggest Features**: Have an idea for a
   feature? [Suggest a feature](https://github.com/nathsagar96/reddit-boot-backend/issues) and let’s discuss how we
   can implement it.
3. **Submit a Pull Request**: Contributions are always welcome! If you want to fix a bug or implement a new feature,
   please follow these steps:
    - Fork the repository.
    - Create a feature branch (`git checkout -b feature-branch`).
    - Commit your changes (`git commit -m 'Add new feature'`).
    - Push to the branch (`git push origin feature-branch`).
    - Open a pull request to the main branch.

---

## **License** 📜

This project is licensed under the **MIT License** – see the [LICENSE](LICENSE) file for details.

---

## **Acknowledgements** 🙌

A big thank you to the following resources and individuals:

- **[Spring Boot Documentation](https://spring.io/projects/spring-boot)** for the core framework used to build this
  backend.
- **[SpringDoc OpenAPI](https://springdoc.org/)** for providing easy-to-use OpenAPI documentation integration.
- **Contributors**: Special thanks to all the contributors who helped build and improve this project.
