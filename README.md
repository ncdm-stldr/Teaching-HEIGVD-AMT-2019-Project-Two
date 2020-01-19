# Teaching-HEIGVD-AMT-2019-Project-Two
## Objectives
[link](./doc/objectives.md)

## How to run

### Run with Docker
#### Prerequisite:
Having docker-compose installed and docker should have ports 80 and 9090 free.
#### How to run
From the root folder of this project, execute ```docker-compose up```. After some time, the services should be ready.
You should now be able to use the two apis at urls: http://localhost/auth-api and http://localhost/packages-api (replace localhost by the address at which docker is available on your computer if needed. For instance, on windows, the first link could become http://192.168.99.100/auth-api instead).

## How to test
### Cucumber tests
#### Prerequisites
auth-api service should be running.
#### Run the tests
In folder auth-specs, their is a pom.xml file. Depending on the address the service is available at on your machine, you may need to change the value of tag ```<ch.heigvd.amt.project_two.auth.server.url>``` in the pom.xml file.
Then run ```mvn clean install```. This will execute some cucumber tests against the service api.
