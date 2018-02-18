package com.gavronek.toyrobot;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static java.util.Objects.requireNonNull;

@RestController
@Api(value = "robot", description = "Operations to simulate a toy robot")
public class RobotController {

    private final Table table;

    @Autowired
    public RobotController(final Table table) {
        this.table = table;
    }


    @ApiOperation(value = "Retrieve the current position of the robot", response = Position.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieve a position"),
            @ApiResponse(code = 400, message = "Robot has not yet been placed on the table. Use the PUT operation")
    })
    @GetMapping(value = "/robot", produces = APPLICATION_JSON_VALUE)
    public Position report() {
        return table.getRobot().getPosition();
    }


    @ApiOperation(value = "Place a robot to the position sent in the body.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully retrieve a position"),
            @ApiResponse(code = 400, message = "Position does not meet the syntactic requirements"),
            @ApiResponse(code = 422, message = "Position is invalid, it exceeds the limits of the table")
    })
    @PutMapping(value = "/robot", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void place(
            @ApiParam(name = "Position", required = true, value = "New position of the robot")
            @RequestBody final Position position) {
        table.apply(r -> requireNonNull(position));
    }


    @ApiOperation(value = "Executes a command on the robot.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Command successfully executed"),
            @ApiResponse(code = 400, message = "Unknown command was sent or robot has not yet been placed on the table"),
            @ApiResponse(code = 422, message = "Command is ignored as the new position would exceed the limits of the table")
    })
    @PostMapping(value = "/robot/commands", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void command(
            @ApiParam(name = "Command", required = true, value = "Command to be executed by the robot")
            @RequestBody final RobotCommandDTO commandDTO) {
        table.apply(commandDTO.getCommand());
    }


    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public ApiError handleValidationException(IllegalArgumentException ex) {
        return new ApiError(HttpStatus.UNPROCESSABLE_ENTITY, "Validation failed. Move ignored.", ex);
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiError handleStateException(IllegalStateException ex) {
        return new ApiError(HttpStatus.BAD_REQUEST, "Preconditions for move did not meet.", ex);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiError handleOtherException(Exception ex) {
        return new ApiError(HttpStatus.BAD_REQUEST, ex);
    }
}
