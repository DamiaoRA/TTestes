package edu.ifpb.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Login {

	@NotBlank
	private String email;

	@NotBlank
	private String password;
}