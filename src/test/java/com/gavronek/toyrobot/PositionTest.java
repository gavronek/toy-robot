package com.gavronek.toyrobot;

import javafx.geometry.Pos;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

import static com.gavronek.toyrobot.Position.Direction.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@JsonTest
public class PositionTest {
    @Autowired
    private JacksonTester<Position> json;

    private Position example;
    private String exampleJson = "{\"x\" : 5, \"y\" : 6, \"direction\" : \"EAST\"}";

    @Before
    public void setUp() throws Exception {
        example = new Position(5, 6, EAST);
    }

    @Test
    public void test_getters() throws Exception {
        assertThat(example.getX()).isEqualTo(5);
        assertThat(example.getY()).isEqualTo(6);
        assertThat(example.getDirection()).isEqualTo(EAST);
    }

    @Test
    public void fulfills_equals_contract() throws Exception {
        EqualsVerifier.forClass(Position.class).verify();
    }

    @Test
    public void test_deserialize() throws Exception {
        assertThat(json.parse(exampleJson)).isEqualTo(example);
    }

    @Test
    public void test_serialization() throws Exception {
        assertThat(json.write(example)).isEqualToJson(exampleJson);
    }

    @Test
    public void go_square_north_right() throws Exception {
        Position position = position(3, 3, NORTH);

        position = position.forward();
        assertThat(position).isEqualTo(position(3,4, NORTH));

        position = position.turnRight().forward();
        assertThat(position).isEqualTo(position(4, 4, EAST));

        position = position.turnRight().forward();
        assertThat(position).isEqualTo(position(4, 3, SOUTH));

        position = position.turnRight().forward();
        assertThat(position).isEqualTo(position(3, 3, WEST));
    }

    @Test
    public void go_square_west_left() throws Exception {
        Position position = position(3, 3, WEST);

        position = position.forward();
        assertThat(position).isEqualTo(position(2,3, WEST));

        position = position.turnLeft().forward();
        assertThat(position).isEqualTo(position(2, 2, SOUTH));

        position = position.turnLeft().forward();
        assertThat(position).isEqualTo(position(3, 2, EAST));

        position = position.turnLeft().forward();
        assertThat(position).isEqualTo(position(3, 3, NORTH));
    }

    @Test
    public void test_all_direction_can_rotate() throws Exception {
        for (Position.Direction direction : Position.Direction.values()) {
            direction.next(true);
            direction.next(false);
        }
    }

    private static Position position(int x, int y, Position.Direction dir) {
        return new Position(x, y, dir);
    }
}
