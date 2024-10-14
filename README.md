# Titanic Passengers Service

This service is responsible for managing and retrieving data related to Titanic passengers. It allows loading passenger data from a CSV file and provides filtering capabilities based on the survival status and passenger class.

## Table of Contents

- [Technologies Used](#technologies-used)
- [Features](#features)
- [API Endpoints](#api-endpoints)
- [Service Overview](#service-overview)

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

The `PassengersController` class handles incoming requests and delegates the business logic to the `PassengersService`. It is responsible for filtering passengers based on the provided parameters.

### PassengersServiceImpl

The `PassengersServiceImpl` class contains the core logic for managing passenger data:

- **loadPassengerData()**: Invoked on startup to read passengers from a CSV file and save them to the database.
- **getFilteredPassengers()**: Retrieves passengers based on survival status and class.
### Data Handling

- **CSV Reading**: The service reads passenger data from a specified CSV file. Each row is parsed into a `Passengers` object, which includes the passenger's name, survival status, and class.
- **Error Handling**: If an error occurs during CSV reading, an empty list is returned, and an error is logged.
