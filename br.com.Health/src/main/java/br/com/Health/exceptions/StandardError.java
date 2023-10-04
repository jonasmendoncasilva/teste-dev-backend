package br.com.Health.exceptions;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StandardError {

	private Instant timestramp;
	private Integer status;
	private String message;
	private String path;
}
