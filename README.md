# Exam for cash online

This is an exam of cash back of a Java application that uses Spring Boot 2 and Maven.

For a live version you can try it at:

[Heroku](https://cash-online.herokuapp.com/)

## Clarifications

<ul>
    <li>It's assumed that you have a local postgres database running or your are going to add the respective env variables</li>
    <li>Default credentials:</li>
        <ul>
            <li>Username: postgres</li>
            <li>Password: postgres</li>
            <li>Database name: cashonline</li>
            <li>Port: 5432</li>
        </ul>
    <li>It's assumed that you have maven installed.</li>
    <li>It's assumed that you have java installed.</li>
    <li>At the start, 50 users and 150 loans will be charged if the id are available.
For tests of users without loans, the user with id 50 is not added or a new user can be added.</li>  
    <li>To modify the quantity of loans that is inserted you can modify start.inserts.loans variable on application.properties.</li>
    <li>The database reset on every run, to change that you can change spring.jpa.hibernate.ddl-auto from create-drop to update.</li>
    <li>For setting another database you need to set the next env variables:
        <ul>
            <li>DB_HOST</li>
            <li>DB_PORT</li>
            <li>DB_NAME</li>
            <li>DB_USERNAME</li>
            <li>DB_PASSWORD</li>
        </ul>
    </li>
</ul>

## Instructions

To compile (also runs unit tests)

```
mvn package
```

## To run the app manually

```
mvn spring-boot:run
```

....and navigate your browser to  http://localhost:8080/

## To run tests

```
mvn spring-boot:run
mvn test
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

### In case of errors on valid endpoint:

**On error:**

```json
{
  "error": "detail of error"
}
```

## Database

### Entity-Relation diagram

![db diagram](/files/cashonline.png)

Enjoy!
