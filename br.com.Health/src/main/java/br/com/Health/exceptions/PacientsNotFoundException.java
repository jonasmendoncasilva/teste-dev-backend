package br.com.Health.exceptions;

public class PacientsNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public PacientsNotFoundException (String message) {
		super(message);
	}
}
