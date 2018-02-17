package com.gavronek.toyrobot;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    @Autowired
    private ObjectMapper mapper;

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
        place(new Position(3, 2, Position.Direction.WEST));

        // TODO define test
    }

    @Test
    public void place_and_report_should_return_position() throws Exception {
        final Position position = new Position(3, 2, Position.Direction.WEST);

        place(position);

        report(position);
    }

    @Test
    public void place_move_and_report() throws Exception {
        final Position startingPosition = new Position(3, 2, Position.Direction.WEST);

        place(startingPosition);

        // TODO make some moves

        report(startingPosition);
    }

    private void place(Position positionToPlace) throws Exception {
        mockMvc.perform(put("/robot")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(positionToPlace)))
                .andExpect(status().is2xxSuccessful());
    }

    private void report(Position expectedPosition) throws Exception{
        mockMvc.perform(get("/robot"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedPosition)));
    }
}
