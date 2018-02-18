package com.gavronek.toyrobot;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

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
    public void calls_command_with_robot_and_sets_position() throws Exception {
        final Position position = new Position(3, 7, Position.Direction.SOUTH);

        table.apply(r -> {
            assertThat(r).isEqualTo(table.getRobot());
            return position;
        });

        assertThat(table.getRobot().getPosition()).isEqualTo(position);
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

    private void triggerValidation(int x, int y) throws Exception {
        table.apply(r -> new Position(x, y, Position.Direction.SOUTH));

    }
}
