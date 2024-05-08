
#### Introduction
The project is a comprehensive application designed to demonstrate the integration of Spring Security with a Spring Boot web application. This project leverages Spring Data JPA for database interactions, Thymeleaf for dynamic web pages, and provides robust authentication and authorization for different user roles.

#### Key Features
- **User Registration and Authentication**: Secure login and registration functionality for users.
- **Role-Based Access Control**: Distinct access controls for `ADMIN` and `USER` roles.
- **CRUD Operations**: Admin users can perform create, read, update, and delete operations on movie data.
- **Pagination and Sorting**: List movies with options to paginate and sort the data.
- **Filtering**: Users can filter movies based on attributes like name, country, and wins.

#### Technologies Used
- Spring Boot 3.2.5
- Spring Security for Authentication and Authorization
- Spring Data JPA with H2 Database
- Thymeleaf template engine
- Maven for dependency management
- Lombok library to reduce boilerplate code

#### Project Structure
The application's source code is organized into several packages:
- `config`: Contains Java configuration classes for MVC, security, and beans.
- `controller`: Web controllers to handle HTTP requests.
- `model`: Entity and DTO classes.
- `repo`: Spring Data JPA repositories.
- `service`: Service layer for business logic.
- `init`: Contains classes for initializing the database with default users.

#### How to Setup and Run
1. **Pre-requisites**:
   - JDK 17
   - Git (optional, for cloning the repository)

2. **Clone the Repository (if using Git)**:
   ```bash
   git clone https://github.com/your-username/SpringBootSecurityFrameworkDemo.git
   ```

3. **Build the Project**:
   ```bash
   ./gradlew clean build
   ```

4. **Run the Application**:
   ```bash
   ./gradlew bootRun
   ```
   This command starts the Spring Boot application on `localhost:8080`.

5. **Accessing the Application**:
   - Open a web browser and visit `http://localhost:8080`.
   - Navigate to `/login` for logging in, `/registration` for new user sign up.

6. **Using H2 Console**:
   - The H2 console can be accessed at `http://localhost:8080/h2`.
   - Use JDBC URL `jdbc:h2:mem:umsDB` to connect to the in-memory database.

#### User Roles and Permissions
- **Admin** (`ROLE_ADMIN`): Can access admin-specific pages, create, update, and delete movie entries.
- **User** (`ROLE_USER`): Can view movies and their details.

#### Logging In
- Default admin credentials: Username: `nsadili`, Password: `12345`
- Default user credentials: Username: `shrek`, Password: `shrek123`
- New users can register through the registration page.

#### Video Link
- **link**: `https://www.youtube.com/watch?v=nAbTqG0UweE`

#### Documentation
The Javadoc documentation for this application is automatically generated during the build process using Gradle. To view the documentation:
- Navigate to the `build/docs/javadoc` directory in the project folder.
- Open the `index.html` file in a web browser.
