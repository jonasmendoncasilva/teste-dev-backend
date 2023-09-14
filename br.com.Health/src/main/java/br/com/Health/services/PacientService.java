package br.com.Health.services;

import java.util.List;

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
}
