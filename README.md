
# github-api-consumer
Simple spring boot app for fetching data about repositories from Github Api.

## How to build
Project can be compiled with JDK 17 and above. \
To compile just do mvn clean package.\
Application starts on localhost:8011.

## Tech Stack


**Server:** Spring Boot Reactive


## API Reference
#### Get User Repositories Without Forks

```http
  GET /api/v1/github/users/{username}/repositories
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `username` | `string` | **Required**. Github username |



