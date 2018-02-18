package com.gavronek.toyrobot;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

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
        this.direction = requireNonNull(direction);
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

    public Position forward() {
        return new Position(x + direction.deltaX, y + direction.deltaY, direction);
    }

    public Position turnRight() {
        return new Position(x, y, direction.next(true));
    }

    public Position turnLeft() {
        return new Position(x, y, direction.next(false));
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
        NORTH(0, 1),
        EAST(1, 0),
        SOUTH(0, -1),
        WEST(-1, 0);

        private final int deltaX;
        private final int deltaY;

        Direction(final int deltaX, final int deltaY) {
            this.deltaX = deltaX;
            this.deltaY = deltaY;
        }

        public Direction next(final boolean clockWise) {
            final int dir = clockWise ? 1 : -1;
            return Direction.values()[Math.floorMod(this.ordinal() + dir, 4)];
        }
    }
}
