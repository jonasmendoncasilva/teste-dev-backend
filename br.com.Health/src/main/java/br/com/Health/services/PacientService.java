package br.com.Health.services;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.Health.entitys.Pacient;
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

	public List<Pacient> checkRisk() throws Exception {
		List<Pacient> pacients = findAll();
		List<Pacient> riskGroup = new ArrayList<>(10);
		List<Double> scores = null;
		List<String> idS = null;
		Double score = 0D;
		
		int sd = 0;
		
		for (int k=0; k < pacients.size(); k++) {
			for (int i=0; i < pacients.get(k).getHealthInssue().size(); i++) {
				sd = pacients.get(k).getHealthInssue().get(i).getRate();				
			}
			score = ( 1 / ( 1 + Math.pow(Math.E, -(-2.8 + sd)))) * 100;
			scores.add(score);
			idS.add(pacients.get(k).getId());
		}
		
		score = scores.get(0);
		for(int k=0; k < scores.size(); k++) {
			for (int i=0; i < pacients.size(); i++) {
				if(scores.get(k) >= score && pacients.get(i).getId().equals(idS.get(k))) {
					riskGroup.add(pacients.get(i));
				}
			}
		}
			
		return null;
	}
}
