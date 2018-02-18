# Toy Robot Simulator

## Introduction

This is a solution for the Toy Robot Simulator challenge for my job application. It is not a production-ready service, many things are simplified and guessed from the challenge description. In normal circumstances these assumptions need to be clarified.

## Task
Create a Spring Boot application with a REST interface to simulate a toy robot, with the following functionality on the robot:
- PLACE x,y,facing
- MOVE
- LEFT
- RIGHT
- REPORT

The robot is moving on an empty 5x5 table. Any actions, which would lead the robot to fall off the table, must be prevented.

## API documentation
The application is deployed on Heroku and provides a Swagger definition. 
Swagger UI is available: 

https://toy-robot.herokuapp.com/swagger-ui.html

## Example calls

##### Report position
```bash
curl -X GET "https://toy-robot.herokuapp.com/robot" -H "accept: application/json"
```

##### Place on a position
```bash
curl -X PUT "https://toy-robot.herokuapp.com/robot" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"x\": 2, \"y\": 2, \"direction\": \"NORTH\"}"
```

##### Execute a command
```bash
curl -X POST "https://toy-robot.herokuapp.com/robot/commands" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"command\": \"MOVE\"}"
```

## Maintenance

### Build
```bash
./gradlew clean build
```
### Run
```bash
./gradlew bootRun
```

## Design decisions and assumptions
- No bulk operation needs to be supported
- Service is manipulating one and only one robot moving on 5x5 table
- Invalid moves do return HTTP errors and never alter the state of the robot
- No need for persistence 
- Because the scope is very limited, classes are not structured in packages

## Room for improvements
- User-friendly validation (now: fail at first validation violation)
- Transactional operations
- Monitoring: health and metrics
