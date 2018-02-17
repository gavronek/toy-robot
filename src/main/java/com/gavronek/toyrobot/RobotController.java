package com.gavronek.toyrobot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RobotController {

    @GetMapping(value = "/robot")
    public String report() {
        return "{}";
    }

    @PutMapping(value = "/robot")
    public void place() {
    }

    @PostMapping(value = "/robot/commands")
    public void command() {
    }
}
