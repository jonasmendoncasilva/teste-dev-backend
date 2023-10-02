package br.com.Health.entitys;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Document
public class Pacient implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	
	private String name;
	private Date birthDate;
	private char gender;
	private Date creationDate;
	private Date updateDate;
	
	private List<HealthInssue> healthInssue;

	public Pacient (Optional<Pacient> pacient) {
	
		this.name = pacient.get().getName();
		this.birthDate = pacient.get().getBirthDate();
		this.gender = pacient.get().getGender();
		this.creationDate = pacient.get().getCreationDate();
		this.updateDate = pacient.get().getUpdateDate();
	}
}
