package me.readyplayer.reportingservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ReportingServiceApplicationTests {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void itShouldReturnReport() throws Exception {
		ReportRequest request = new ReportRequest("Bike", 2, 500, "$", "E-bike for cities");

		MvcResult result = mockMvc.perform(post("/api/v1/generate-report")
						.contentType(MediaType.APPLICATION_JSON)
						.content(Objects.requireNonNull(ObjectToJson(request))))
				.andExpect(status().isOk())
				.andReturn();

		assertNotNull(result.getResponse().getContentAsString());
	}

	@Test
	void itShouldGiveBadRequestIfQuantityIsLessThanOne() throws Exception {
		ReportRequest request = new ReportRequest("Bike", 0, 500, "$", "E-bike for cities");

		mockMvc.perform(post("/api/v1/generate-report")
						.contentType(MediaType.APPLICATION_JSON)
						.content(Objects.requireNonNull(ObjectToJson(request))))
				.andExpect(status().isBadRequest());
	}

	private String ObjectToJson(Object object) throws JsonProcessingException {
		return objectMapper.writeValueAsString(object);
	}
}
