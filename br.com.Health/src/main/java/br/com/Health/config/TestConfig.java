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
		
		
		Pacient p1 = new Pacient(null, "Maria", sdf.parse("30-08-2001"), 'F', sdf.parse("30-08-2001"),sdf.parse("30-08-2001"), Arrays.asList(h1,h2));
		Pacient p2 = new Pacient(null, "José", sdf.parse("30-08-2001"), 'M', sdf.parse("30-08-2001"),sdf.parse("30-08-2001"), Arrays.asList(h3,h4));
		
		repo.saveAll(Arrays.asList(p1,p2));
	}

	
}
