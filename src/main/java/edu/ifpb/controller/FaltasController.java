package edu.ifpb.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ifpb.model.User;

@RestController
@RequestMapping("/faltas")
public class FaltasController {

//	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/")
	public ResponseEntity<?> faltas() {
		User u = new User();
		u.setName("ad");
		u.setEmail("adsf");
		List<User> l = new ArrayList<User>();
		l.add(u);
		
		return ResponseEntity.ok(l);
	}
}
