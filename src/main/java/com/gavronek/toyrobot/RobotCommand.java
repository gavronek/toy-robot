package com.gavronek.toyrobot;

@FunctionalInterface
public interface RobotCommand {
    Position apply(Robot robot);
}
