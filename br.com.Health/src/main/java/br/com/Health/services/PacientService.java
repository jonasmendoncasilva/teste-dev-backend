package br.com.Health.services;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.Health.entitys.Pacient;
import br.com.Health.repository.PacientRepository;

@Service
public class PacientService {

	@Autowired
	private PacientRepository repo;
	
	public List<Pacient> findAll(){
		
		return repo.findAll();
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
}
