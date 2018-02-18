package com.gavronek.toyrobot;


import java.util.Optional;

public class Robot {
    private Optional<Position> position = Optional.empty();

    public Position getPosition() {
        return position.orElseThrow(() -> new IllegalStateException("Robot is not on table."));
    }

    public Position move() {
        return getPosition().forward();
    }

    public Position turnLeft() {
        return getPosition().turnLeft();
    }

    public Position turnRight() {
        return getPosition().turnRight();
    }

    public void apply(final RobotCommand command, final Table table) {
        final Position newPosition = command.apply(this);
        table.validate(newPosition);
        position = Optional.of(newPosition);
    }
}
