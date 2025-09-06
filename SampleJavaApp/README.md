# Sample Java Application

This is a simple Spring Boot application created as a sample project.

## Project Structure

```
SampleJavaApp
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── example
│   │   │           └── SampleJavaAppApplication.java
│   │   └── resources
│   │       └── application.properties
│   └── test
│       └── java
│           └── com
│               └── example
│                   └── SampleJavaAppApplicationTests.java
├── build.gradle
├── settings.gradle
└── README.md
```

## Prerequisites

- Java 11 or higher
- Gradle 6.0 or higher

## Building the Application

To build the application, navigate to the project directory and run:

```
./gradlew build
```

## Running the Application

To run the application, use the following command:

```
./gradlew bootRun
```

## Testing the Application

To run the tests, execute:

```
./gradlew test
```

## Configuration

You can configure the application settings in the `src/main/resources/application.properties` file.

## License

This project is licensed under the MIT License.