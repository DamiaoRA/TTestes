package edu.ifpb.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ifpb.model.Login;
import edu.ifpb.model.User;
import edu.ifpb.security.jwt.JwtResponse;
import edu.ifpb.security.jwt.JwtUtils;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> login(@RequestBody Login login) {
		UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(
				login.getEmail(), login.getPassword());

		Authentication authentication = authenticationManager.authenticate(upat);

		String jwt = jwtUtils.generateJwtToken(authentication);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		User userDetails = (User) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(
				new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), roles));
	}
}


//		SecurityContextHolder.getContext().setAuthentication(authentication);