package com.gavronek.toyrobot.domain.robot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RobotControllerTest {
    private final static String positionJson = "{\"x\" : 2, \"y\" : 3, \"facing\" : \"EAST\"}";


    @Autowired
    private MockMvc mockMvc;

    @Test
    public void report_should_fail_if_no_robot() throws Exception {
        // TODO expect proper error descriptions
        mockMvc.perform(get("/robot"))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void any_move_should_fail_if_no_robot() throws Exception {
        // TODO define proper data sent as a command
        // TODO expect proper error descriptions
        mockMvc.perform(post("/robot/commands").content("TODO"))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void place_and_fail_if_invalid_move() throws Exception {
        placeRobotOnStarting();

        // TODO define test
    }

    @Test
    public void place_and_report_should_return_position() throws Exception {
        placeRobotOnStarting();

        mockMvc.perform(get("/robot"))
                .andExpect(status().isOk())
                .andExpect(content().json(positionJson));
    }

    @Test
    public void place_move_and_report() throws Exception {
        placeRobotOnStarting();

        // TODO make some moves
        // TODO call report
    }

    private void placeRobotOnStarting() throws Exception {
        mockMvc.perform(put("/robot")
                .contentType(MediaType.APPLICATION_JSON)
                .content(positionJson))
                .andExpect(status().is2xxSuccessful());
    }
}
