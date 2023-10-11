package edu.ifpb.integration;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import edu.ifpb.controller.UserController;
import edu.ifpb.model.User;
import edu.ifpb.service.UserService;

@WebMvcTest(UserController.class)
public class WebMvcTestIntegration {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService userService;
	
	@Test
	public void someTest() throws Exception {
		User u = new User();
		u.setId(1L);
		u.setName("Maria");
		u.setEmail("maria@ifpb.edu.br");

		Mockito.when(userService.findById(1L)).thenReturn(u);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/user/get/1"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Maria"));
	}
}

