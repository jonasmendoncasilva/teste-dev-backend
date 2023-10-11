package br.com.Health.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.Health.entitys.HealthInssue;
import br.com.Health.entitys.Pacient;
import br.com.Health.services.PacientService;

@WebMvcTest
public class PacientControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private PacientService service;
	
	Pacient p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13;
	HealthInssue h1, h2, h3, h4, h5;
	SimpleDateFormat sdf;
	
	@BeforeEach
	void setup() throws ParseException {

		// GIVEN
		sdf = new SimpleDateFormat("dd-MM-yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

		h1 = new HealthInssue("diabetes", 1);
		h2 = new HealthInssue("diabetes", 2);
		h3 = new HealthInssue("hepatite", 1);
		h4 = new HealthInssue("hepatite", 2);
		h5 = new HealthInssue("hepatite", 3);

		p1 = new Pacient(null, "Maria", sdf.parse("30-08-2001"), 'F', sdf.parse("30-08-2001"), sdf.parse("30-08-2001"),Arrays.asList(h1, h2, h3, h4));
		p2 = new Pacient(null, "José", sdf.parse("30-08-2001"), 'M', sdf.parse("30-08-2001"), sdf.parse("30-08-2001"),Arrays.asList(h3, h4));
		p3 = new Pacient(null, "Roberto", sdf.parse("30-08-2001"), 'M', sdf.parse("30-08-2001"),sdf.parse("30-08-2001"), Arrays.asList(h1, h4));
		p4 = new Pacient(null, "Alfonso", sdf.parse("30-08-2001"), 'M', sdf.parse("30-08-2001"),sdf.parse("30-08-2001"), Arrays.asList(h2, h4));
		p5 = new Pacient(null, "Miguel", sdf.parse("30-08-2001"), 'M', sdf.parse("30-08-2001"), sdf.parse("30-08-2001"),Arrays.asList(h3, h1));
		p6 = new Pacient(null, "Alberto", sdf.parse("30-08-2001"), 'M', sdf.parse("30-08-2001"),sdf.parse("30-08-2001"), Arrays.asList(h3, h2));
		p7 = new Pacient(null, "Rose", sdf.parse("30-08-2001"), 'M', sdf.parse("30-08-2001"), sdf.parse("30-08-2001"),Arrays.asList(h1, h2));
		p8 = new Pacient(null, "Sara", sdf.parse("30-08-2001"), 'M', sdf.parse("30-08-2001"), sdf.parse("30-08-2001"),Arrays.asList(h3, h4));
		p9 = new Pacient(null, "Humberto", sdf.parse("30-08-2001"), 'M', sdf.parse("30-08-2001"),sdf.parse("30-08-2001"), Arrays.asList(h3, h4));
		p10 = new Pacient(null, "Beatriz", sdf.parse("30-08-2001"), 'M', sdf.parse("30-08-2001"),sdf.parse("30-08-2001"), Arrays.asList(h2, h1));
		p11 = new Pacient(null, "Juliana", sdf.parse("30-08-2001"), 'M', sdf.parse("30-08-2001"),sdf.parse("30-08-2001"), Arrays.asList(h2, h3, h4));

		p12 = new Pacient(null, "Roberto", sdf.parse("30-08-2001"), 'M', sdf.parse("30-08-2001"),sdf.parse("30-08-2001"), Arrays.asList(h4, h5));
		p13 = new Pacient(null, "Josefina", sdf.parse("30-08-2001"), 'M', sdf.parse("30-08-2001"), sdf.parse("30-08-2001"),Arrays.asList(h3, h4));
	}

	@Test
	@DisplayName("Given Pacient Object when Create Pacient then Return Saved Pacient")
	void testGivenPacientObject_whenCreatePacient_thenReturnSavedPacient() throws Exception {
		
			//Given / Arrange
			given(service.savePacient(any(Pacient.class))).willAnswer(
					(invocation) -> invocation.getArgument(0));
			
			//When / Act
			ResultActions response = mockMvc.perform(post("/pacients")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(p1)));
			
			//Then / Assert
			response.andDo(print())
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.name", is(p1.getName())))
					.andExpect(jsonPath("$.healthInssue", Matchers.hasSize(4)))
					.andExpect(jsonPath("$.healthInssue[0].name", is(p1.getHealthInssue().get(0).getName())));
	}
	
	@Test
	@DisplayName("Given List Of Pacients when FindAll Pacients then Return Pacients List")
	void testGivenListOfPacients_whenFindAllPacients_thenReturnPacientsList() throws Exception {
		
		//Given / Arrange
		List<Pacient> pacients = new ArrayList<>();
		pacients.addAll(Arrays.asList(p1,p2,p3,p4,p5,p6,p7,p8,p9,p10));
		
		given(service.findAll()).willReturn(pacients);
				
		//When / Act
		ResultActions response = mockMvc.perform(get("/pacients"));
				
		//Then / Assert
		response
		.andExpect(status().isOk())
		.andDo(print())
		.andExpect(jsonPath("$.size()", is(pacients.size())));
	}
	
	@Test
	@DisplayName("Given Pacient Object when Create Pacient then Return Saved Pacient")
	void testGivenPacientId_whenFindById_thenReturnPacientObject() throws Exception {
		
			//Given / Arrange
			String id = "651cbd64c2d5d263cdbf8a30";
			given(service.findById(id)).willReturn(p1);
			
			//When / Act
			ResultActions response = mockMvc.perform(get("/pacients/{id}", id));
					
			//Then / Assert
			response
				.andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$.name", is(p1.getName())))
				.andExpect(jsonPath("$.healthInssue", Matchers.hasSize(4)))
				.andExpect(jsonPath("$.healthInssue[0].name", is(p1.getHealthInssue().get(0).getName())));
	}
	
	@Test
	@DisplayName("Given Pacient Object when Create Pacient then Return Saved Pacient")
	void testGivenUpdatedPacient_whenUpdate_thenReturnUpdatedPersonObject() throws Exception {
		
			//Given / Arrange
			String id = "651cbd64c2d5d263cdbf8a30";
			given(service.findById(id)).willReturn(p1);
			given(service.update(any(Pacient.class))).willAnswer((invocation) -> invocation.getArgument(0));
					
			
			//When / Act
			ResultActions response = mockMvc.perform(put("/pacients/update")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(p2)));
			
			//Then / Assert
			response.andDo(print())
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.name", is(p2.getName())))
					.andExpect(jsonPath("$.healthInssue", Matchers.hasSize(2)))
					.andExpect(jsonPath("$.healthInssue[0].name", is(p2.getHealthInssue().get(0).getName())));
	}
}
