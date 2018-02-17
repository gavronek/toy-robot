package com.gavronek.toyrobot;


import java.util.Optional;

public class Robot {
    private Optional<Position> position = Optional.empty();

    public Optional<Position> getPosition() {
        return position;
    }

    public void setPosition(final Position position) {
        this.position = Optional.of(position);
    }
}
