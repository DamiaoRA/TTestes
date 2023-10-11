package edu.ifpb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	//O código de status 200 (OK) será automaticamente definido na resposta
//	@ResponseStatus(value  = HttpStatus.OK)
	@GetMapping("/hello")
	public String getHello() {
		return "Hello";
	}
}
