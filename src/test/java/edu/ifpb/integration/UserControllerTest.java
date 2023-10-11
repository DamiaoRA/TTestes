package edu.ifpb.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import edu.ifpb.model.User;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	void testPostUser() {
		User u = new User();
		u.setId(10L);
		u.setName("Marcos");
		u.setEmail("marcos@ifpb.edu.br");

		ResponseEntity<User> response = testRestTemplate.postForEntity("/user/postUser", u, User.class);
		assertThat(response.getBody().getName()).isEqualTo("MARCOS");
	}

	@Test
	void testPutAndGetUser() {
		User u = new User();
		u.setId(15L);
		u.setName("Mariana");
		u.setEmail("mariana@ifpb.edu.br");

		testRestTemplate.put("/user/putUser", u);

		ResponseEntity<User> response = testRestTemplate.getForEntity("/user/get/{id}", User.class, 15L);

		assertEquals(response.getBody().getId(), 15L);
		assertThat(response.getBody().getName()).isEqualTo("MARIANA");
	}

	@Test
	@Sql(statements = "insert into tb_user values (20, 'carol@ifpb.edu.br', 'CAROL')")
	void testDeleteUser() {
		ResponseEntity<User> response = testRestTemplate.getForEntity("/user/get/{id}", User.class, 20L);
		assertThat(response.getBody().getName()).isEqualTo("CAROL");

		testRestTemplate.delete("/user/delete/{id}", 20L);

		response = testRestTemplate.getForEntity("/user/get/{id}", User.class, 20L);
		assertNull(response.getBody());
	}
}