package edu.ifpb.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import edu.ifpb.service.UserService;

@SpringBootTest
@ActiveProfiles("teste2")
@Import(TestConfig.class)
public class IntegrationTest {
	
	@Autowired
	private UserService service;

	@Test
	void test() {
//		fail("Não implementado");
		service.enviarNotificacao();
	}
}