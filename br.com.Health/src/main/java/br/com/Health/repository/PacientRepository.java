package br.com.Health.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.Health.entitys.Pacient;

@Repository
public interface PacientRepository extends MongoRepository<Pacient, String> {}
