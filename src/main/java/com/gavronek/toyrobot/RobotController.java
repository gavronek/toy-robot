package com.gavronek.toyrobot;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RobotController {

    private final Table table;

    @Autowired
    public RobotController(final Table table) {
        this.table = table;
    }

    @GetMapping(value = "/robot")
    public Position report() {
        return table.getRobot().getPosition();
    }

    @PutMapping(value = "/robot")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void place(@RequestBody final Position position) {
        table.apply(r -> position);
    }

    @PostMapping(value = "/robot/commands")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void command(@RequestBody final RobotCommandDTO commandDTO) {
        table.apply(commandDTO.getCommand());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public ApiError handleValidationException(IllegalArgumentException ex) {
        return new ApiError(HttpStatus.UNPROCESSABLE_ENTITY, "Validation failed. Move ignored." ,ex);
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
