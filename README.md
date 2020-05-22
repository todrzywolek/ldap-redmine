# How to start the app

## Start ldap server
`docker-compose -f ./redmine-backend/unboundid_ldap_server/docker-compose.yaml up`

## Run backend app
In directory redmine-backend run:

`mvn clean install`

`mvn spring-boot:run`

## Run frontend app
In directory redmine-frontend run:

`npm install`

`npm start`

Postman collection for backend REST API available at redmine-backend/resources