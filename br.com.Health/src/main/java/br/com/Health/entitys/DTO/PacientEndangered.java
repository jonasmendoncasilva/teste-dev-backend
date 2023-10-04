package br.com.Health.entitys.DTO;

import br.com.Health.entitys.Pacient;

public record PacientEndangered(
		String id,
		String name) {
	
	public PacientEndangered(Pacient pacient) {
		this(pacient.getId(),pacient.getName());
	}
}
