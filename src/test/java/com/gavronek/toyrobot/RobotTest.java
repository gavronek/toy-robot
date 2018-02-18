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

    @Test(expected = IllegalStateException.class)
    public void no_starting_position() throws Exception {
        robot.getPosition();
    }
}
