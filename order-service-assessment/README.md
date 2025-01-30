# order-service-assessment
Technology Stack Used: 
▪ Spring Boot 3.3.4
▪ Java 21
▪ Spring Data JPA (with an H2 in-memory database for simplicity)
▪ REST APIs with OpenAPI documentation
▪ Unit and integration testing with JUnit and Mockito
▪ Docker for containerizing the services

we can access the swagger documentation at http://localhost:8080/swagger-ui.html

Steps to run the application:
1. Clone the repository
2. Run the following command to build the application
    ```shell
     cd order-service-assessment
    ./mvnw clean package -DskipTests
   cd.. 
    cd inventory-service-assessment
    ./mvnw clean package -DskipTests
   
    ```
3. Run the following command to build the docker image
    ```shell
   docker-compose up
    ```
