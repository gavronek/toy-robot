package com.gavronek.toyrobot;

import org.junit.Before;
import org.junit.Test;

import static com.gavronek.toyrobot.Position.Direction.NORTH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class RobotTest {
    private static final Position startingPosition = new Position(1, 1, NORTH);
    private Table table;
    private Robot robot;

    @Before
    public void setUp() throws Exception {
        this.robot = new Robot();
        this.table = mock(Table.class);
    }

    @Test(expected = IllegalStateException.class)
    public void no_starting_position() throws Exception {
        robot.getPosition();
    }

    @Test
    public void test_proxy_calls() throws Exception {
        final Position positionMock = mock(Position.class);

        robot.apply(r -> positionMock, table);

        robot.move();
        robot.turnLeft();
        robot.turnRight();

        verify(positionMock).forward();
        verify(positionMock).turnLeft();
        verify(positionMock).turnRight();
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_apply_validation_fails() throws Exception {
        doThrow(new IllegalArgumentException()).when(table).validate(any());
        robot.apply(r -> startingPosition, table);
    }

    @Test
    public void test_apply_validation_ok() throws Exception {
        robot.apply(r -> startingPosition, table);
        assertThat(robot.getPosition()).isEqualTo(startingPosition);
    }
}
