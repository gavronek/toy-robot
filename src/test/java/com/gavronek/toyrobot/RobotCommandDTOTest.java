package com.gavronek.toyrobot;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@JsonTest
public class RobotCommandDTOTest {
    @Autowired
    private JacksonTester<RobotCommandDTO> json;

    private RobotCommandDTO example = new RobotCommandDTO(RobotMovement.RIGHT);
    private String exampleJson = "{ \"command\" : \"RIGHT\" }";

    @Test
    public void test_serialization() throws Exception {
        assertThat(json.write(example)).isEqualToJson(exampleJson);
    }

    @Test
    public void test_deserialize() throws Exception {
        assertThat(json.parse(exampleJson)).isEqualTo(example);
    }

    @Test
    public void fulfills_equals_contract() throws Exception {
        EqualsVerifier.forClass(RobotCommandDTO.class).verify();
    }
}
