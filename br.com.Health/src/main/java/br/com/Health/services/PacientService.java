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
import br.com.Health.exceptions.InssueRateException;
import br.com.Health.exceptions.ListSizeException;
import br.com.Health.exceptions.PacientNotFoundException;
import br.com.Health.exceptions.PacientsNotFoundException;
import br.com.Health.repository.PacientRepository;

@Service
public class PacientService {

	@Autowired
	private PacientRepository repo;
	
	public List<Pacient> findAll(){
		List<Pacient> pacients = repo.findAll();
		if(pacients.isEmpty()) throw new PacientsNotFoundException("There isn't pacients saved");
		return pacients;
	}

	public Pacient findById(String id){
		Optional<Pacient> pacient = repo.findById(id);
		return pacient.orElseThrow(()-> new PacientNotFoundException("Pacient not Found!!"));
	}

	public Pacient update(Pacient pacient){
		Optional<Pacient> pacientFromData = repo.findById(pacient.getId());
		
		if(pacientFromData.isPresent()) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
			
			pacientFromData.get().setName(pacient.getName());
			pacientFromData.get().setBirthDate(pacient.getBirthDate());
			pacientFromData.get().setCreationDate(pacient.getCreationDate());
			pacientFromData.get().setUpdateDate(Date.from(Instant.now()));
			pacientFromData.get().setGender(pacient.getGender());
			
			for(int i=0; i < pacient.getHealthInssue().size() - 1;i++) {
				pacientFromData.get().getHealthInssue().get(i).setName(pacient.getHealthInssue().get(i).getName());
				pacientFromData.get().getHealthInssue().get(i).setRate(pacient.getHealthInssue().get(i).getRate());
			}	
		}
		else {
			throw new PacientNotFoundException("Pacient not Found!!");
		}
		Pacient p = new Pacient(pacientFromData);
		return repo.save(p);
	}

	public Pacient savePacient(Pacient pacient) throws InssueRateException {		
			if(checkListInssues(pacient)==false) throw new InssueRateException("The Rate must be between 1 and 2");
			return repo.save(pacient);
	}

	private boolean checkListInssues(Pacient pacient){
		for (HealthInssue inssue : pacient.getHealthInssue()) {
			if(inssue.getRate()<1 || inssue.getRate()>2 ) {
				return false;
			} 
		}
		return true;
	}

	public List<PacientEndangered> checkRisk() throws ListSizeException{
			List<Pacient> pacients = repo.findAll();
		
			if(pacients.size()<10) throw new ListSizeException("The size of list is less than 10!!!");
		
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
