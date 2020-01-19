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

### Run locally
#### Prerequisite:
Having maven installed. Having jdk installed. jdk version 8 should work.
#### How to run
There are two services: auth-api and packages-api. From one of the corresponding folder, execute command ```mvn clean install```.
Then, you'll find a .jar executable in the generated target/ folder. From this folder, execute it with ```java -jar swagger-spring-1.0.0.jar```.
If you would like to run both service at the same time, you should change the port the server is listening on for one of them.
Here is an example to explain how to do that: suppose you would like to change the port auth-api is listening on: change the port number written in application.properties file found in folder auth-api/src/main/resources/ and then execute maven to regenerate the .jar (alternatively, you could modify application.properties directly in the jar, if you already have it).

## How to test
### Cucumber tests
#### Prerequisites
auth-api service should be running.
#### Run the tests
In folder auth-specs, their is a pom.xml file. Depending on the address the service is available at on your machine, you may need to change the value of tag ```<ch.heigvd.amt.project_two.auth.server.url>``` in the pom.xml file.
Then run ```mvn clean install```. This will execute some cucumber tests against the service api.
