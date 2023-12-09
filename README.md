# github-api-consumer

Simple spring boot app for fetching data about repositories from Github Api.
## Task
Acceptance criteria:
As an api consumer, given username and header "Accept: application/json", I would like to list all his github repositories, which are not forks. Information, which I require in the response, is:

Repository Name
Owner Login
For each branch it’s name and last commit sha

As an api consumer, given not existing github user, I would like to receive 404 response in such a format:
{
"status": ${responseCode}
"Message": ${whyHasItHappened}
}

As an api consumer, given header "Accept: application/xml", I would like to receive 406 response in such a format:
{
"status": ${responseCode}
"Message": ${whyHasItHappened}
}

Notes:
Please full-fill the given acceptance criteria, delivering us your best code compliant with industry standards.
Please use https://developer.github.com/v3 as a backing API
Application should have a proper README.md file
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

| Parameter  | Type     | Description                   |
|:-----------|:---------|:------------------------------|
| `username` | `string` | **Required**. Github username |



