package br.com.Health.exceptions;

public class ListSizeException extends Exception {
	private static final long serialVersionUID = 1L;

	public ListSizeException (String message) {
		super(message);
	}
}
