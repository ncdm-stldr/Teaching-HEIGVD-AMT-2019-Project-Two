# Teaching-HEIGVD-AMT-2019-Project-Two
## Objectives
[link](./doc/objectives.md)

## Functional aspects
The idea was to implement a REST api that would allow some users to index software packages and tag them. A tag here is an association between a label and a package. One can add labels, create tags (which means associate labels to packages).
A further step (not implemented) would be to query packages using labels. It would also be useful to merge labels sometimes, if these have the same meaning or are closely related.

## How to run

### Run with Docker
#### Prerequisites:
Having docker-compose installed and docker should have ports 80 and 9090 free.
#### How to run
From the root folder of this project, execute ```docker-compose up```. After some time, the services should be ready.
You should now be able to use the two apis at urls: http://localhost/auth-api and http://localhost/packages-api (replace localhost by the address at which docker is available on your computer if needed. For instance, on windows, the first link could become http://192.168.99.100/auth-api instead).
(At the moment, the services reset the databases each time they start. To change this behaviour, there is a field in the application.properties files that is called spring.jpa.hibernate.ddl-auto. Its value could be put to __validate__ once the database schema is created. From then, the data should persist, but the containers should be build again (or modified in another way))

#### Use the api
Swagger 2.0 has been integrated with the spring applications. The simplest way to use the api manually is to use a browser to connect to the endpoints. Swagger will then return a page that shows the api routes, and provides an interface to help you perform the queries from the browser.
##### How to use the authentification
Some routes needs an authentification token to work. When one post an existing user to the login/ endpoint, a jwt token is returned.
For routes that requires authorization, this token should then be added to the http header __Authorization: Bearer <jwt_token>__, where jwt_token has to be replaced by the jwt token returned by the login/ endpoint. Swagger allows to do this easily: click on the __Authorize__ button. Then, in the popup form write: __Bearer jwt__ where jwt has to be replaced by the appropriate jwt token (the one returned by the /login endpoint). Validate. Now the authorization header is added automatically.

## How to test
### Cucumber tests
#### Prerequisites
auth-api service should be running.
#### Run the tests
In folder auth-specs, their is a pom.xml file. Depending on the address the service is available at on your machine, you may need to change the value of tag ```<ch.heigvd.amt.project_two.auth.server.url>``` in the pom.xml file.
Then run ```mvn clean install```. This will execute some cucumber tests against the user-api service.
