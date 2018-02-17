package com.gavronek.toyrobot;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class RobotTest {

    private Robot robot;

    @Before
    public void setUp() throws Exception {
        this.robot = new Robot();
    }

    @Test
    public void no_starting_position() throws Exception {
        assertThat(robot.getPosition()).isEqualTo(Optional.empty());
    }

    @Test
    public void test_set_and_get() throws Exception {
        final Position position = new Position(3, 4, Position.Direction.NORTH);

        robot.setPosition(position);
        assertThat(robot.getPosition()).isEqualTo(Optional.of(position));
    }
}
