package br.com.Health.services;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.Health.entitys.HealthInssue;
import br.com.Health.entitys.Pacient;
import br.com.Health.entitys.PacientScore;
import br.com.Health.entitys.DTO.PacientEndangered;
import br.com.Health.repository.PacientRepository;

@Service
public class PacientService {

	@Autowired
	private PacientRepository repo;
	
	public List<Pacient> findAll() throws Exception{
		List<Pacient> pacients = repo.findAll();
		if(pacients.isEmpty()) throw new Exception("There isn't pacients saved");
		return pacients;
	}

	public Pacient findById(String id) throws Exception {
		Optional<Pacient> pacient = repo.findById(id);
		return pacient.orElseThrow(()-> new Exception("Pacient not Found!!"));
	}

	public Optional<Pacient> update(Pacient pacient) throws Exception {
		Optional<Pacient> pacientFromData = repo.findById(pacient.getId());
		
		if(pacientFromData.isPresent()) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
			
			pacientFromData.get().setName(pacient.getName());
			pacientFromData.get().setBirthDate(pacient.getBirthDate());
			pacientFromData.get().setCreationDate(pacient.getCreationDate());
			pacientFromData.get().setUpdateDate(Date.from(Instant.now()));
			pacientFromData.get().setGender(pacient.getGender());
			
			for(int i=0; i<pacient.getHealthInssue().size();i++) {
				pacientFromData.get().getHealthInssue().get(i).setName(pacient.getHealthInssue().get(i).getName());
				pacientFromData.get().getHealthInssue().get(i).setRate(pacient.getHealthInssue().get(i).getRate());
			}	
		}
		else {
			throw new Exception("Id not found!!");
		}
		return pacientFromData;
	}

	public Pacient savePacient(Pacient pacient) {
		Pacient client = repo.save(pacient);
		return client;
	}

	public List<PacientEndangered> checkRisk() throws Exception {
		List<Pacient> pacients = repo.findAll();
		List<PacientScore> pacientScore;
		List<PacientScore> topPacientScore; 
		
		pacientScore = pacients.stream().map(pacient -> new PacientScore(pacient.getId(), calculateScore(pacient))).collect(Collectors.toList());
		topPacientScore = pacientScore.stream().sorted(Comparator.comparingDouble(PacientScore::getScore).reversed()).limit(10).collect(Collectors.toList());
		
		List<Pacient> riskGroup = createRiskGroup(pacients, topPacientScore);
		
		List<PacientEndangered> pacientsEndangered; 
		pacientsEndangered= riskGroup.stream().map(pacient -> new PacientEndangered(pacient)).collect(Collectors.toList());
		return pacientsEndangered;
	}

	public Double calculateScore(Pacient pacient) {
		Double sd = 0D;
		for (HealthInssue inssues : pacient.getHealthInssue()) {
			sd += inssues.getRate();
		}
		return ( 1 / ( 1 + Math.pow(Math.E, -(-2.8 + sd)))) * 100;
	}

	public List<Pacient> createRiskGroup(List<Pacient> pacientData, List<PacientScore> topScore){
		List<Pacient> riskGroup = new ArrayList<>(10);
		for(Pacient pacient : pacientData) {
			for (PacientScore topPacientScore : topScore) {
				if(pacient.getId().equals(topPacientScore.getId())) {
					riskGroup.add(pacient);
				}
			}
		}
		return riskGroup;
	}
}
