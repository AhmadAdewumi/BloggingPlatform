# Personal Blogging Platform (Java + Spring Boot)

A personal blog project built with Java, Spring Boot, and MySQL.


> This version has no authentication. 

##  Tech Stack

- **Backend**: Java 17, Spring Boot
- **Database**: MySQL
- **ORM**: Spring Data JPA (Hibernate)
- **Build Tool**: Maven

## Architecture

- MVC structure: `Controller → Service → Repository`
- DTOs used to bind form data cleanly
- Spring Data JPA for all DB interactions
- Configuration via `application.properties`

##  How to Run (Using Maven)

### 1. Prerequisites

- Java 17+
- Maven installed
- MySQL running locally with a database created

### 2. Configure `application.properties`

Update this file with your database details:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/blog_db
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```
### You can also find the project here 
 - https://roadmap.sh/projects/personal-blog

