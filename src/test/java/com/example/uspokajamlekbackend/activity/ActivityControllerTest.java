package com.example.uspokajamlekbackend.activity;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ActivityControllerTest {
    private MockMvc mockMvc;

    @MockBean
    private ActivityService activityService;

    @InjectMocks
    private ActivityController activityController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(activityController).build();

    }

    @Test
    void shouldGetUserActivities() throws Exception {
        Long userId = 1L;
        List<ActivityResponse> activityResponses = List.of(new ActivityResponse(), new ActivityResponse());

        when(activityService.getUserActivities(anyLong())).thenReturn(activityResponses);

        mockMvc.perform(MockMvcRequestBuilders.get("/activities?id=" + userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        verify(activityService, times(1)).getUserActivities(userId);
    }

    @Test
    void shouldAddActivity() throws Exception {
        ActivityRequest activityRequest = new ActivityRequest();

        mockMvc.perform(MockMvcRequestBuilders.post("/activities/add")
                        .content(asJsonString(activityRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(activityService, times(1)).addActivity(activityRequest);
    }

    // Utility method to convert object to JSON string
    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
