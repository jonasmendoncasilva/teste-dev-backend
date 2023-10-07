package br.com.Health.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.Health.entitys.HealthInssue;
import br.com.Health.entitys.Pacient;
import br.com.Health.entitys.DTO.PacientEndangered;
import br.com.Health.exceptions.InssueRateException;
import br.com.Health.exceptions.ListSizeException;
import br.com.Health.exceptions.PacientsNotFoundException;
import br.com.Health.repository.PacientRepository;

@ExtendWith(MockitoExtension.class)
public class PacientServiceTest {

	@Mock
	private PacientRepository repository;
	
	@InjectMocks
	private PacientService service;
	
	Pacient p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12;
	HealthInssue h1,h2,h3,h4,h5;
	SimpleDateFormat sdf;
	
	@BeforeEach
	void setup() throws ParseException  {
		
		//GIVEN
		sdf = new SimpleDateFormat("dd-MM-yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		h1 = new HealthInssue("diabetes", 1);
		h2 = new HealthInssue("diabetes", 2);
		h3 = new HealthInssue("hepatite", 1);
		h4 = new HealthInssue("hepatite", 2);
		h5 = new HealthInssue("hepatite", 3);
		
		p1 = new Pacient(null, "Maria", sdf.parse("30-08-2001"), 'F', sdf.parse("30-08-2001"),sdf.parse("30-08-2001"), Arrays.asList(h1,h2,h3,h4));
		p2 = new Pacient(null, "José", sdf.parse("30-08-2001"), 'M', sdf.parse("30-08-2001"),sdf.parse("30-08-2001"), Arrays.asList(h3,h4));
		p3 = new Pacient(null, "Roberto", sdf.parse("30-08-2001"), 'M', sdf.parse("30-08-2001"),sdf.parse("30-08-2001"), Arrays.asList(h1,h4));
		p4 = new Pacient(null, "Alfonso", sdf.parse("30-08-2001"), 'M', sdf.parse("30-08-2001"),sdf.parse("30-08-2001"), Arrays.asList(h2,h4));
		p5 = new Pacient(null, "Miguel", sdf.parse("30-08-2001"), 'M', sdf.parse("30-08-2001"),sdf.parse("30-08-2001"), Arrays.asList(h3,h1));
		p6 = new Pacient(null, "Alberto", sdf.parse("30-08-2001"), 'M', sdf.parse("30-08-2001"),sdf.parse("30-08-2001"), Arrays.asList(h3,h2));
		p7 = new Pacient(null, "Rose", sdf.parse("30-08-2001"), 'M', sdf.parse("30-08-2001"),sdf.parse("30-08-2001"), Arrays.asList(h1,h2));
		p8 = new Pacient(null, "Sara", sdf.parse("30-08-2001"), 'M', sdf.parse("30-08-2001"),sdf.parse("30-08-2001"), Arrays.asList(h3,h4));
		p9 = new Pacient(null, "Humberto", sdf.parse("30-08-2001"), 'M', sdf.parse("30-08-2001"),sdf.parse("30-08-2001"), Arrays.asList(h3,h4));
		p10 = new Pacient(null, "Beatriz", sdf.parse("30-08-2001"), 'M', sdf.parse("30-08-2001"),sdf.parse("30-08-2001"), Arrays.asList(h2,h1));
		p11 = new Pacient(null, "Juliana", sdf.parse("30-08-2001"), 'M', sdf.parse("30-08-2001"),sdf.parse("30-08-2001"), Arrays.asList(h2,h3,h4));
		
		p12 = new Pacient(null, "Roberto", sdf.parse("30-08-2001"), 'M', sdf.parse("30-08-2001"),sdf.parse("30-08-2001"), Arrays.asList(h4,h5));
	}
	
	@Test
	@DisplayName("Given A Pacient Object when Save A Pacient then Return A Pacient")
	void testGivenAPacientObject_whenSaveAPacient_thenReturnAPacient() {
		
		//Given
		given(repository.save(any(Pacient.class))).willReturn(p1);
		
		//When & Then
		assertDoesNotThrow(()-> {
			Pacient savedPacient = service.savePacient(p1);
			assertEquals(p1, savedPacient);
		});

		verify(repository, times(1)).save(any(Pacient.class));
	}
	
	@Test
	@DisplayName("Given A Pacient Object when Save A Pacient then Throw Exception")
	void testGivenAPacientObject_whenSaveAPacient_thenThrowException() {
		
		//When
		InssueRateException exception = assertThrows(InssueRateException.class, () -> {
            service.savePacient(p12);
        });

		//Then
        assertEquals("The Rate must be between 1 and 2", exception.getMessage());
        verify(repository, never()).save(any(Pacient.class));
	}

	@Test
	@DisplayName("Given A Pacient List when Find All Pacients then Return A List Of Pacients")
	void testGivenAPacientList_whenFindAllPacients_thenReturnAListOfPacients() {
		
		//Given
		given(repository.findAll()).willReturn(List.of(p1,p2));
		
		//When
		List<Pacient> list = service.findAll();
	
		//Then
		assertNotNull(list);
		assertEquals("Maria", list.get(0).getName());
		assertEquals("José", list.get(1).getName());
	}
	
	@Test
	@DisplayName("Given A Pacient List when Find All Pacients then Return Throw Exception")
	void testGivenAPacientList_whenFindAllPacients_thenReturnThrowException() {
		
		//Given
		given(repository.findAll()).willReturn(Collections.emptyList());
		
		//When
		PacientsNotFoundException exception = assertThrows(PacientsNotFoundException.class, ()->{
			service.findAll();
		});
		
		//Then
		assertEquals("There isn't pacients saved", exception.getMessage());
	}
	
	@Test
	@DisplayName("Given A Pacient Id Object when Find By Id then Return A Pacient Object")
	void testGivenAPacientId_whenFindById_thenReturnAPacientObject() {
		
		//Given
		given(repository.findById(anyString())).willReturn(Optional.of(p1));
		String id = "651cbd64c2d5d263cdbf8a30";
		
		//When
		Pacient savedPacient = service.findById(id);

		//Then
		assertEquals(p1, savedPacient);
		assertNotNull(savedPacient);
		assertEquals("Maria", savedPacient.getName());
	}

	@Test
	@DisplayName("Given A Pacient List when Check Risk then Return The Most Dangered Pacients")
	void testGivenAPacientList_whenCheckRisk_thenReturnTheMostDangeredPacients() {
		
		//Given	
		p1.setId("maria");
		p2.setId("jose");
		p3.setId("roberto");
		p4.setId("alfonso");
		p5.setId("miguel");
		p6.setId("alberto");
		p7.setId("rose");
		p8.setId("sara");
		p9.setId("humberto");
		p10.setId("beatriz");
		p11.setId("juliana");
		
		List<Pacient> list = Arrays.asList(p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11);
		given(repository.findAll()).willReturn(list);
		
		//When && Then
		assertDoesNotThrow(()-> {
			List<PacientEndangered> listDTO = service.checkRisk();
			assertEquals(10, listDTO.size());
			assertEquals("Maria", listDTO.get(0).name());
			assertEquals("maria", listDTO.get(0).id());
		});
	}
		@Test
		@DisplayName("Given A Pacient List when Check Risk With A Size Less Than Ten then Throws A Exception")
		void testGivenAPacientList_whenCheckRiskWithASizeLessThanTen_thenThrowsAException() {
			
			//Given
			p1.setId("maria");
			p2.setId("jose");
			p3.setId("roberto");
			p4.setId("alfonso");
			p5.setId("miguel");
			
			List<Pacient> list = Arrays.asList(p1,p2,p3,p4,p5);
			given(repository.findAll()).willReturn(list);
			
			//When
			ListSizeException exception = assertThrows(ListSizeException.class, ()-> {
				service.checkRisk();
			});
			
			//Then
			assertEquals("The size of list is less than 10!!!", exception.getMessage());
	}
}
