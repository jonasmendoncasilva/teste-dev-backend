
package br.com.Health.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.Health.entitys.Pacient;
import br.com.Health.entitys.DTO.PacientEndangered;
import br.com.Health.services.PacientService;

@RestController
@RequestMapping(value="/pacients")
public class PacientController {

	@Autowired
	private PacientService service;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Pacient>> findAll() throws Exception{		
		List<Pacient> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Pacient> save(@RequestBody Pacient pacient) throws Exception{		
		Pacient client = service.savePacient(pacient);
		return ResponseEntity.ok().body(client);
	}
	
	@RequestMapping(value="/{id}")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Pacient> findById(@PathVariable String id) throws Exception{
		Pacient pacient = service.findById(id);
		return ResponseEntity.ok().body(pacient);
	}
	
	@RequestMapping(value="/update")
	@PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Pacient> update(@RequestBody Pacient pacient) throws Exception{
		Pacient client = service.update(pacient);
		return ResponseEntity.ok().body(client);
	}
	
	@RequestMapping(value="/danger")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PacientEndangered>> checkRisk(@RequestBody Pacient pacient) throws Exception{
		
		List<PacientEndangered> pacients = service.checkRisk(); 
		return ResponseEntity.ok().body(pacients);
	}

	
}
