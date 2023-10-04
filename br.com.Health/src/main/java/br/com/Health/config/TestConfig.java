package br.com.Health.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import br.com.Health.entitys.HealthInssue;
import br.com.Health.entitys.Pacient;
import br.com.Health.repository.PacientRepository;

@Configuration
public class TestConfig implements CommandLineRunner {

	@Autowired
	private PacientRepository repo;

	@Override
	public void run(String... args) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		repo.deleteAll();
		
		HealthInssue h1 = new HealthInssue("diabetes", 1);
		HealthInssue h2 = new HealthInssue("diabetes", 2);
		
		HealthInssue h3 = new HealthInssue("hepatite", 1);
		HealthInssue h4 = new HealthInssue("hepatite", 2);
		
		
		Pacient p1 = new Pacient(null, "Maria", sdf.parse("30-08-2001"), 'F', sdf.parse("30-08-2001"),sdf.parse("30-08-2001"), Arrays.asList(h1,h2,h3,h4));
		Pacient p2 = new Pacient(null, "José", sdf.parse("30-08-2001"), 'M', sdf.parse("30-08-2001"),sdf.parse("30-08-2001"), Arrays.asList(h3,h4));
		Pacient p3 = new Pacient(null, "Roberto", sdf.parse("30-08-2001"), 'M', sdf.parse("30-08-2001"),sdf.parse("30-08-2001"), Arrays.asList(h1,h4));
		Pacient p4 = new Pacient(null, "Alfonso", sdf.parse("30-08-2001"), 'M', sdf.parse("30-08-2001"),sdf.parse("30-08-2001"), Arrays.asList(h2,h4));
		Pacient p5 = new Pacient(null, "Miguel", sdf.parse("30-08-2001"), 'M', sdf.parse("30-08-2001"),sdf.parse("30-08-2001"), Arrays.asList(h3,h1));
		Pacient p6 = new Pacient(null, "Alberto", sdf.parse("30-08-2001"), 'M', sdf.parse("30-08-2001"),sdf.parse("30-08-2001"), Arrays.asList(h3,h2));
		Pacient p7 = new Pacient(null, "Rose", sdf.parse("30-08-2001"), 'M', sdf.parse("30-08-2001"),sdf.parse("30-08-2001"), Arrays.asList(h1,h2));
		Pacient p8 = new Pacient(null, "Sara", sdf.parse("30-08-2001"), 'M', sdf.parse("30-08-2001"),sdf.parse("30-08-2001"), Arrays.asList(h3,h4));
		Pacient p9 = new Pacient(null, "Humberto", sdf.parse("30-08-2001"), 'M', sdf.parse("30-08-2001"),sdf.parse("30-08-2001"), Arrays.asList(h3,h4));
		Pacient p10 = new Pacient(null, "Beatriz", sdf.parse("30-08-2001"), 'M', sdf.parse("30-08-2001"),sdf.parse("30-08-2001"), Arrays.asList(h2,h1));
		Pacient p11 = new Pacient(null, "Juliana", sdf.parse("30-08-2001"), 'M', sdf.parse("30-08-2001"),sdf.parse("30-08-2001"), Arrays.asList(h2,h3,h4));
		
		
		repo.saveAll(Arrays.asList(p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11));
	}

	
}
