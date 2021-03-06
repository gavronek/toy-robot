package com.gavronek.toyrobot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Table {
    private final Robot robot;
    private final int dimensionX;
    private final int dimensionY;

    Table(final Robot robot, final int dimensionX, final int dimensionY) {
        this.robot = robot;
        this.dimensionX = dimensionX;
        this.dimensionY = dimensionY;
    }

    @Autowired
    public Table(@Value("${table.dimensions.x}") final int dimensionX, @Value("${table.dimensions.y}") final int dimensionY) {
        this(new Robot(), dimensionX, dimensionY);
    }

    public void apply(final RobotCommand command) {
        robot.apply(command, this);
    }

    public void validate(final Position newPosition) {
        if (!isBetween(newPosition.getX(), 0, dimensionX))
            throw new IllegalArgumentException(String.format("Position X is not in range [0, %s)", dimensionX));

        if (!isBetween(newPosition.getY(), 0, dimensionY))
            throw new IllegalArgumentException(String.format("Position Y is not in range [0, %s)", dimensionY));
    }

    public Robot getRobot() {
        return robot;
    }

    private boolean isBetween(final int x, final int fromIncl, final int toExcl) {
        return fromIncl <= x  && x < toExcl;
    }
}
