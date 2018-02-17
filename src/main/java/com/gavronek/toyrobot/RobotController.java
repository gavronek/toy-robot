package com.gavronek.toyrobot;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RobotController {

    private final Table table;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    public RobotController(final Table table) {
        this.table = table;
    }

    @GetMapping(value = "/robot")
    public Position report() {
        return table.getRobot().getPosition()
                .orElseThrow(() -> new IllegalStateException("Robot is not on table"));
    }

    @PutMapping(value = "/robot")
    public void place(@RequestBody final Position position) {
        table.apply(r -> position);
    }

    @PostMapping(value = "/robot/commands")
    public void command() {
    }
}
