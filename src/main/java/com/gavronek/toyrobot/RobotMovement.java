package com.gavronek.toyrobot;

public enum RobotMovement implements RobotCommand {
    MOVE(Robot::move),
    LEFT(Robot::turnLeft),
    RIGHT(Robot::turnRight);

    private final RobotCommand command;

    RobotMovement(RobotCommand command) {
        this.command = command;
    }

    @Override
    public Position apply(final Robot robot) {
        return command.apply(robot);
    }
}
