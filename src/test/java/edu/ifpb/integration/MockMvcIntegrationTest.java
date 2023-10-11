package edu.ifpb.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class MockMvcIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void sayHello() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/hello")) //requisição
		.andExpect(MockMvcResultMatchers.status().isOk())     //status 200
		.andExpect(MockMvcResultMatchers.content().string("Hello"));
	}
}



//@WebMvcTest(HelloController.class)
