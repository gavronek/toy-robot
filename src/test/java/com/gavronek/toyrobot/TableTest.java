package com.gavronek.toyrobot;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TableTest {
    private Table table;

    @Before
    public void setUp() throws Exception {
        table = new Table(10, 10);
    }

    @Test
    public void initializes_robot_with_empty_position() throws Exception {
        assertThat(table.getRobot()).hasFieldOrPropertyWithValue("position", Optional.empty());
    }

    @Test
    public void test_apply() throws Exception {
        RobotCommand mockCommand = mock(RobotCommand.class);
        Robot robot = mock(Robot.class);
        Table table = new Table(robot, 10, 10);

        table.apply(mockCommand);

        verify(robot).apply(mockCommand, table);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validate_x_lower_limit() throws Exception {
        triggerValidation(-1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validate_x_upper_limit() throws Exception {
        triggerValidation(10, 0);
    }


    @Test(expected = IllegalArgumentException.class)
    public void validate_y_lower_limit() throws Exception {
        triggerValidation(0, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validate_y_upper_limit() throws Exception {
        triggerValidation(0, 10);
    }

    private void triggerValidation(int x, int y) {
        table.validate(new Position(x, y, Position.Direction.NORTH));
    }
}
