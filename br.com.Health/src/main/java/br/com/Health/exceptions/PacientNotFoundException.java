package br.com.Health.exceptions;

public class PacientNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public PacientNotFoundException (String message) {
		super(message);
	}
}
