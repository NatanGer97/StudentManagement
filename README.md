# Student management system
This is a  student management system that allows you to:
- Crud operations on students
- **Upload and image for a student (AWS S3)**
- **Email a student or all students (in microservice manner)**
- View a student grade and average
- Filter students
- etc..

## Tech Stack
* **Backend**: Spring-boot (java)
* **Database**: Postgres
* **Containerization**: Docker
* **Cloud**: AWS (EC2, S3)
* **Security**: JWT - spring security
* _microservices: email service_

## Includes: 
    1. Pagination and sorting
    2. JWT authentication
    3. Database connectivity via Postgres
    4. Email service (**microservice**)
    5. AWS integration (S3)
    6. Dockerized and  deployed to AWS



## Run Locally - without microservices
* note: you need to have docker installed

Clone the project 

```bash
  git clone git@github.com:NatanGer97/StudentManagement.git
```

Go to the project directory

```bash
  cd my-project
```

run docker-compose 
```bash
  docker-compose docker-compose -f docker-compose-local.yml up --force-recreate
```

## Run Locally - with microservices
* note: you need to have docker installed

Clone the project

```bash
  git clone git@github.com:NatanGer97/StudentManagement.git
```

Go to the project directory

```bash
  cd my-project
```

run docker-compose
```bash
  docker-compose docker-compose -f docker-compose-local.yml up --force-recreate
```
```bash
  docker ps
  dokcer stop <id>
  start server locally  (with intellij)
```