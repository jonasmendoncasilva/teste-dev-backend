package br.com.Health.entitys;

public class PacientScore {

	private String id;
	private Double score;
	
	public PacientScore() {}

	public PacientScore(String id, Double score) {
		this.id = id;
		this.score = score;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}
}
