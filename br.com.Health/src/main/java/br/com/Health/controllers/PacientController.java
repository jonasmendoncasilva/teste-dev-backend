package br.com.Health.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.Health.entitys.Pacient;
import br.com.Health.services.PacientService;

@RestController
@RequestMapping(value="/pacients")
public class PacientController {

	@Autowired
	private PacientService service;
	
	@GetMapping
	public ResponseEntity<List<Pacient>> findAll(){
		List<Pacient> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
}
