package br.com.Health.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import br.com.Health.entitys.HealthInssue;
import br.com.Health.entitys.Pacient;

@DataMongoTest
public class PacientRepositoryTest {

	@Autowired
	private PacientRepository repository;
	
	Pacient p1,p2;
	HealthInssue h1,h2,h3,h4;
	SimpleDateFormat sdf;
	
	@BeforeEach
	void setup() throws ParseException {
		
		//GIVEN
		sdf = new SimpleDateFormat("dd-MM-yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		h1 = new HealthInssue("diabetes", 1);
		h2 = new HealthInssue("diabetes", 2);
		h3 = new HealthInssue("hepatite", 1);
		h4 = new HealthInssue("hepatite", 2);
		
		p1 = new Pacient(null, "Maria", sdf.parse("30-08-2001"), 'F', sdf.parse("30-08-2001"),sdf.parse("30-08-2001"), Arrays.asList(h1,h2));
		p2 = new Pacient(null, "José", sdf.parse("30-08-2001"), 'M', sdf.parse("30-08-2001"),sdf.parse("30-08-2001"), Arrays.asList(h3,h4));
	}
	
	@Test
	@DisplayName("Given Pacient when Save A Pacient Object will Return A Saved Pacient")
	void testGivenPacient_whenSaveAPacientObject_willReturnASavedObject(){
		
		//When
		Pacient savedPacient = repository.save(p1);

		//Then
		assertNotNull(savedPacient);
		assertEquals(1, savedPacient.getHealthInssue().get(0).getRate());
	}
	
	@Test
	@DisplayName("Given A List Of Pacients will Return All Pacients Saved")
	void testGivenAListOfPacients_willReturnAllPacientsSaved(){
		
		//Given
		repository.saveAll(Arrays.asList(p1,p2));
		
		//When
		List<Pacient> savedPacients = repository.findAll();
		
		//Then
		assertNotNull(savedPacients);
		assertEquals("Maria",savedPacients.get(0).getName());
		assertEquals("Maria",savedPacients.get(0).getName());
	}
	
	@Test
	@DisplayName("Given A Pacient Object when Set A Id To Search will Return A Pacient Object")
	void testGivenAPacientObject_whenSetAIdToSearch_willReturnAPacientObject(){
		
		//Given
		String id = "651afc458dfcef0b961e4b8f";
		repository.save(p1);
		p1.setId(id);
		
		//When
		Optional<Pacient> pacient = repository.findById(id);
		
		//Then
		assertNotNull(pacient);
		assertEquals(id, pacient.get().getId());
		
	}
}
