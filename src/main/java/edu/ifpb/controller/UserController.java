package edu.ifpb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import edu.ifpb.model.User;
import edu.ifpb.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@ResponseStatus(value = HttpStatus.CREATED)
	@PostMapping("/postUser")
	public User postUser(@RequestBody User u) {
		return userService.save(u);
	}

//	@ResponseStatus(value = HttpStatus.CREATED)
	@PutMapping("/putUser")
	public ResponseEntity<User> putUser(@RequestBody User u) {
		return new ResponseEntity<>(userService.save(u), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable Long id) {
		userService.deleteById(id);
	}

	@GetMapping("/get/{id}")
	public User getUser(@PathVariable Long id) {
		return userService.findById(id);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/getAll")
	public List<User> getUsers() {
		return userService.findAll();
	}
}