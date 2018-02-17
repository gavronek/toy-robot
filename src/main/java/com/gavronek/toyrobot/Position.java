package com.gavronek.toyrobot;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

public final class Position {
    private final int x;
    private final int y;
    private final Direction direction;

    @JsonCreator
    public Position(
            @JsonProperty("x") final int x,
            @JsonProperty("y") final int y,
            @JsonProperty("direction") final Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Position position = (Position) o;
        return x == position.x &&
                y == position.y &&
                direction == position.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, direction);
    }

    public enum Direction {
        NORTH,
        EAST,
        SOUTH,
        WEST
    }
}
