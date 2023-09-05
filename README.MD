# WhatHappened-backend

This is the repository for the backend of WhatHappened, a Spring Boot application that provides news and weather information. The backend includes controllers for news, user management, and weather data. <br>
For the frontend application, please refer to [WhatHappened-frontend](https://github.com/d104601/whathappened-frontend).

## Getting Started
The backend application is deployed on AWS EC2 through Elastic Beanstalk. To access the application deployed, please use frontend application deployed at [here](http://what-happened-frontend.s3-website-us-east-1.amazonaws.com/).
To run the application locally, please follow the instructions below.

### Prerequisites
- Java 17
- Maven
- MongoDB(for local storage)

### Installation
1. Clone the repository
2. Configure envrionments
    - Create a file named `application-database.properties` under `src/main/resources` and add the following lines:
        ```
        spring.data.mongodb.uri=<your mongodb uri>
        spring.data.mongodb.database=<your mongodb database name>
        ```
    - Create a file named `application-other.properties` under `src/main/resources` and add the following lines:
        ```
        api.rapidapi.key=<your bing news api key from rapidapi>
        api.openweather.key=<your openweather api key>
        jwt.secret-key=<your jwt secret key>
        jwt.expiration=604800000
        ```
3. Build the application
    ```
    mvn clean install
    ```
   
4. Run the application
    ```
    java -jar target/whatHappend-backend-1.0.0.jar
    ```

## API Reference
The API documentation is generated using Springdoc OpenAPI and is available at http://localhost:5000/swagger-ui.html when the application is running.

## Technologies Used
- Java 17
- Spring Boot
- MongoDB
- Spring Security
- Springdoc OpenAPI
- Maven
- jjwt
- Bing News API
- OpenWeather API