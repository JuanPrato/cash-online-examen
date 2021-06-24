# Exam for cash online

This is an exam of cash back of a Java application that uses Spring Boot 2 and Maven.

## Clarifications

<ul>
    <li>It's assumed that you have a local postgres database running with the credentials mentioned on application.properties</li>
    <li>It's assumed that you have maven installed</li>
</ul>


## Instructions

To compile (also runs unit tests)

```
mvn package
```

## To run the webapp manually

```
mvn spring-boot:run
```

....and navigate your browser to  http://localhost:8080/

## To run integration tests

```
mvn spring-boot:run
mvn verify
```

## Endpoints

##- User:

### Starting path: /users/

**Method: Get**
#### Specific path: /{id}

**Response body:**
```json
{
  "id": 1,
  "email": "example@mail.com.ar",
  "firstName": "Juan",
  "lastName": "Proto",
  "loans": [
    {
      "id": 1,
      "total": 2.500,
      "userId": 1
    }
  ]
}
```
<br/>

**Method: Post**

#### Specific path: /

**Request body:**

```json
{
  "email": "example@mail.com.ar",
  "firstName": "Juan",
  "lastName": "Proto"
}
```

**Response body:**

```json
{
  "id": 1,
  "email": "example@mail.com.ar",
  "firstName": "Juan",
  "lastName": "Proto",
  "loans": []
}
```

**Method: Delete**

#### Specific path: /{id}

**Response body:**
```json
{
  "id": 1
}
```

##- Loans:

### Starting path: /loans

**Method: Get**

| Params        | Example       | Required |
| ------------- |:-------------:|:---------:|
| page          | 1             |Yes        |
| size          | 50            |Yes        |
| user_id       | 2             |No         |

**Response body:**

```json
{
  "items": [
    {
      "id": 1,
      "total": 2500.00,
      "user_id": 1
    },
    {
      "id": 2,
      "total": 1000.00,
      "user_id": 1
    },
    {
      "id": 3,
      "total": 500.75,
      "user_id": 1
    }
  ],
  "paging": {
    "page": 2,
    "size": 50,
    "total": 1500
  }
}
```

## Entity-Relation diagram

![db diagram](/files/cashonline.png)

Enjoy!
