package edu.ifpb.model;

import lombok.Data;

@Data
public class UserDto {

	private Long id;

	private String name;

	private String email;
	
	private String password;

	public User mapper() {
		User u = new User();
		u.setEmail(getEmail());
		u.setId(getId());
		u.setName(getName());
		u.setPassword(getPassword());
		return u;
	}
}
