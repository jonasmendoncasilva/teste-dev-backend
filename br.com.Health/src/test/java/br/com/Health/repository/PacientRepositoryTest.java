package br.com.Health.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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
	
	Pacient p1;
	HealthInssue h1;
	HealthInssue h2;
	SimpleDateFormat sdf;
	
	@BeforeEach
	void setup() throws ParseException {
		//GIVEN
		sdf = new SimpleDateFormat("dd-MM-yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		h1 = new HealthInssue("diabetes", 1);
		h2 = new HealthInssue("diabetes", 2);
		p1 = new Pacient(null, "Maria", sdf.parse("30-08-2001"), 'F', sdf.parse("30-08-2001"),sdf.parse("30-08-2001"), Arrays.asList(h1,h2));
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
}
