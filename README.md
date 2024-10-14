# Titanic Passengers Service

This service is responsible for managing and retrieving data related to Titanic passengers. It allows loading passenger
data from a CSV file and provides filtering capabilities based on the survival status and passenger class.

## Table of Contents

- [Technologies Used](#technologies-used)
- [Features](#features)
- [API Endpoints](#api-endpoints)
- [Service Overview](#service-overview)
- [Improvement List](#improvement-list)
- [Observation](#observation)

## Technologies Used

- Java
- Spring Boot
- Lombok
- JUnit
- Mockito

## Features

- Load passenger data from a CSV file upon startup.
- Filter passengers based on survival status and class.
- Cache results for improved performance on repeated queries.

## API Endpoints

### Get Filtered Passengers

`GET /passengers`

This endpoint retrieves a list of passengers filtered by survival status and passenger class.

**Parameters:**

- `survived` (optional): A Boolean indicating if the passengers survived.
- `pclass` (optional): The class of the passengers. Possible values:
    - `VIP`
    - `FIRST_CLASS`
    - `SECOND_CLASS`
    - `POPULAR`
    - `NO_CLASS`

**Example Request:**

````agsl
curl --location 'http://localhost:8080/api/passengers?pclass=FIRST_CLASS'

````

**Response:**

````
[
    {
        "id": 1,
        "name": "John Smith",
        "survived": true,
        "pclass": "FIRST_CLASS"
    }
]
````

### Refresh Cache

`POST /api/passengers/refresh-cache`

This endpoint refreshes the cache for passenger data, ensuring that subsequent requests will retrieve fresh data.

**Example Request:**

````agsl
curl --location --request POST 'http://localhost:8080/api/passengers/refresh-cache'
````

**Response:**

````agsl
Cache refreshed successfully.
````

## Service Overview

### PassengersController

The `PassengersController` class handles incoming requests and delegates the business logic to the `PassengersService`.
It is responsible for filtering passengers based on the provided parameters.

### PassengersServiceImpl

The `PassengersServiceImpl` class contains the core logic for managing passenger data:

- **loadPassengerData()**: Invoked on startup to read passengers from a CSV file and save them to the database.
- **getFilteredPassengers()**: Retrieves passengers based on survival status and class.

### Data Handling

- **CSV Reading**: The service reads passenger data from a specified CSV file. Each row is parsed into a `Passengers`
  object, which includes the passenger's name, survival status, and class.
- **Error Handling**: If an error occurs during CSV reading, an empty list is returned, and an error is logged.

### Improvement List

While the project is functional, several enhancements could be implemented to improve its performance, usability, and
maintainability:

- **Extra Methods**: Add methods to support creating, editing, and removing passengers from the database.
- **Implement Data Management with Liquibase**: Use Liquibase to manage database migrations and ensure smooth updates and
  version control of the database schema.
- **Add Validations**: Implement validation logic to ensure the correctness of passenger data during creation and updates (
  e.g., non-null fields, correct data types).
- **Cache Improvement**: Enhance the caching mechanism to optimize performance and reduce database queries for frequently
  requested data.
- **Security**: Integrate security measures, such as user authentication and authorization, to protect sensitive data and
  ensure secure access to APIs.
- **Docker**: Containerize the application using Docker to simplify deployment and ensure a consistent environment across
  different stages (development, testing, production).
- **User Interface**: Build a user-friendly front-end interface that allows users to manage passengers, upload CSV files,
  and apply filters easily.

## Observation
The original exercise description indicated that there was a link to a CSV file containing example data. 

However, the instructions were provided as a PNG image, and I did not have access to the referenced CSV. 

To facilitate the development of this project, I created a basic CSV file that contains sample passenger data. This file serves as the foundation for loading and processing passenger information within the application.


````agsl
name,pclass,survived
John Smith,1,1
Jane Doe,3,0
William Anderson,2,1
James Smith,3,0
Mary Johnson,1,1
Patricia Brown,2,0
Jennifer Davis,3,1
Michael Miller,1,1
Elizabeth Wilson,2,0
Linda Moore,3,1
Barbara Taylor,1,0
David Thomas,2,1
Richard Jackson,3,0
Charles White,1,1
Joseph Harris,2,0
Thomas Martin,1,1
Christopher Thompson,3,0
Daniel Garcia,2,1
Matthew Martinez,1,1
Anthony Robinson,3,0
Mark Clark,1,0
Donald Rodriguez,2,1
Steven Lewis,3,1
Paul Lee,1,0
Andrew Walker,2,1
Joshua Hall,3,0
Kevin Allen,1,1
Brian Young,2,0
George King,3,1
Edward Wright,1,1
Ronald Scott,2,0
Timothy Green,1,1
Jason Adams,3,0
Jeffrey Baker,2,1
Ryan Gonzalez,1,1
Jacob Nelson,3,0
Gary Carter,2,1
Nicholas Mitchell,1,1
Eric Perez,3,0
Stephen Roberts,2,1
Larry Turner,1,0
Justin Phillips,3,1
Scott Campbell,1,0
Brandon Parker,2,1
Frank Evans,3,0
Gregory Edwards,1,1
Raymond Edwards,2,0
Patrick Collins,1,1
Jack Stewart,3,0
Dennis Sanchez,2,1
Walter Morris,1,1
Peter Rogers,3,0
````