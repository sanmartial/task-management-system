package org.globaroman.taskmanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.globaroman.taskmanagementsystem.dto.label.CreateLabelRequireDto;
import org.globaroman.taskmanagementsystem.dto.label.LabelResponseDto;
import org.globaroman.taskmanagementsystem.model.Color;
import org.globaroman.taskmanagementsystem.model.Label;
import org.globaroman.taskmanagementsystem.repository.LabelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class LabelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LabelRepository labelRepository;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Save a new label -> should return ResponseDto and response as Created")
    @Transactional
    @Rollback
    void save_SaveLabel_ShouldReturnLabelResponseDtoAndReturnResponseAsCreated() throws Exception {
        CreateLabelRequireDto requireDto = createRequireDto();
        String jsonRequireDto = objectMapper.writeValueAsString(requireDto);
        LabelResponseDto responseDto = createResponseDto();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/labels")
                        .content(jsonRequireDto)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(responseDto.getName()));
            }

    @Test
    @DisplayName("Get all labels -> Should return List<LabelResponseDto> and response Ok")
    @Transactional
    @Rollback
    void getAll_GetAllSavedLabels_ShouldReturnResponseAsOk() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/labels")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Update exist label -> Should return updated ResponseDto and response as Ok")
    @Transactional
    @Rollback
    void update_UpdateExistLabel_ShouldReturnLabelResponseDtoAndResponseAsOk() throws Exception {
        Label existLabel = createLabelForTest();
        CreateLabelRequireDto requireDtoUpdate = createRequireDto();
        requireDtoUpdate.setColor(Color.RED);

        Label updateLabel = createLabelForTest();
        updateLabel.setColor(Color.RED);

        LabelResponseDto responseDto = createResponseDto();
        responseDto.setColor(Color.RED);

        String jsonRequest = objectMapper.writeValueAsString(requireDtoUpdate);

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/labels/{id}", existLabel.getId())
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Delete exist label -> Should return response as Ok")
    @Transactional
    @Rollback
    void deleteById_DeleteExistLabelByID_ShouldReturnResponseAsOk() throws Exception {
        Label label = createLabelForTest();

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/labels/{id}", label.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").doesNotExist());

    }

    private Label createLabelForTest() {
        Label label = new Label();
        //label.setId(1L);
        label.setName("Label 1");
        label.setColor(Color.BLUE);
        return labelRepository.save(label);
    }

    private CreateLabelRequireDto createRequireDto() {
        CreateLabelRequireDto requireDto = new CreateLabelRequireDto();
        requireDto.setName("Label 1");
        requireDto.setColor(Color.BLUE);
        return requireDto;
    }

    private LabelResponseDto createResponseDto() {
        LabelResponseDto responseDto = new LabelResponseDto();
        responseDto.setName("Label 1");
        responseDto.setColor(Color.BLUE);
        responseDto.setId(1L);
        return responseDto;
    }
}