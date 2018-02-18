package com.gavronek.toyrobot;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

public final class RobotCommandDTO {
    private final RobotMovement command;

    @JsonCreator
    public RobotCommandDTO(@JsonProperty("command") final RobotMovement command) {
        this.command = command;
    }

    public RobotMovement getCommand() {
        return command;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final RobotCommandDTO that = (RobotCommandDTO) o;
        return command == that.command;
    }

    @Override
    public int hashCode() {
        return Objects.hash(command);
    }
}
