package com.gavronek.toyrobot;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.gavronek.toyrobot.Position.Direction.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
        mockMvc.perform(get("/robot"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.debugMessage", is("Robot is not on table.")));
    }

    @Test
    public void any_move_should_fail_if_no_robot() throws Exception {
        for (RobotMovement movement : RobotMovement.values()) {
            moveCall(movement)
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.debugMessage", is("Robot is not on table.")));
        }
    }

    @Test
    public void place_should_fail_if_out_of_table() throws Exception {
        placeCall(new Position(10, 0, NORTH))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message", is("Validation failed. Move ignored.")));

        placeCall(new Position(0, 10, NORTH))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message", is("Validation failed. Move ignored.")));
    }

    @Test
    @DirtiesContext
    public void place_and_fail_if_invalid_move() throws Exception {
        place(new Position(4, 3, EAST));

        moveCall(RobotMovement.MOVE)
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message", is("Validation failed. Move ignored.")));
    }

    @Test
    @DirtiesContext
    public void place_and_report_should_return_position() throws Exception {
        final Position position = new Position(3, 2, WEST);

        place(position);

        report(position);
    }

    @Test
    @DirtiesContext
    public void place_move_and_report() throws Exception {
        final Position startingPosition = new Position(3, 2, EAST);

        place(startingPosition);

        move(RobotMovement.MOVE);
        move(RobotMovement.LEFT);
        move(RobotMovement.MOVE);
        move(RobotMovement.MOVE);
        move(RobotMovement.RIGHT);

        report(new Position(4, 4, EAST));
    }

    @Test
    public void invalid_move() throws Exception {
        mockMvc.perform(post("/robot/commands")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void invalid_place() throws Exception {
        mockMvc.perform(put("/robot")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
    }

    private void place(Position positionToPlace) throws Exception {
        placeCall(positionToPlace).andExpect(status().isNoContent());
    }

    private ResultActions placeCall(Position positionToPlace) throws Exception {
        return mockMvc.perform(put("/robot")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(positionToPlace)));
    }

    private void report(Position expectedPosition) throws Exception{
        mockMvc.perform(get("/robot"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedPosition)));
    }

    private void move(RobotMovement movement) throws Exception {
        moveCall(movement).andExpect(status().isNoContent());
    }

    private ResultActions moveCall(RobotMovement movement) throws Exception {
        return mockMvc.perform(post("/robot/commands")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new RobotCommandDTO(movement))));
    }

}
