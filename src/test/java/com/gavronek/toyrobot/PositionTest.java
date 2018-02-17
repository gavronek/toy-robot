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
        example = new Position(5, 6, Position.Direction.EAST);
    }

    @Test
    public void test_getters() throws Exception {
        assertThat(example.getX()).isEqualTo(5);
        assertThat(example.getY()).isEqualTo(6);
        assertThat(example.getDirection()).isEqualTo(Position.Direction.EAST);
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
}
