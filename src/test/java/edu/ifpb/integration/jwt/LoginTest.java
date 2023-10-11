package edu.ifpb.integration.jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import edu.ifpb.model.Login;
import edu.ifpb.model.User;
import edu.ifpb.security.jwt.JwtResponse;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class LoginTest {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	void testLoginValido() throws URISyntaxException {
		//Login
		ResponseEntity<?> response = restTemplate.postForEntity(
				new URI("/api/auth/signin"), 
				new Login("admin@ifpb.edu.br", "pass"), 
				JwtResponse.class);

		//Verifica se o status é 200
		assertEquals(response.getStatusCode(), HttpStatus.OK);

		JwtResponse jresp = (JwtResponse)response.getBody();

		//Verifica o token
		assertThat(jresp.getToken()).isNotNull(); //verifica se não é null
		assertThat(jresp.getToken()).isNotEmpty();//verifica se não é vazia

		//Verifica o login e o perfil
		assertEquals(jresp.getEmail(), "admin@ifpb.edu.br");
		assertEquals(jresp.getRoles().size(), 1);
		assertEquals(jresp.getRoles().get(0), "ADMIN");
	}
	
	@Test
	void testLoginInvalido() throws URISyntaxException {
		//Login
		ResponseEntity<?> response = restTemplate.postForEntity(
				new URI("/api/auth/signin"), 
				new Login("sith@ifpb.edu.br", "pass"), 
				JwtResponse.class);

		//Verifica se o status é 403
		assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);

		//Verifica se a resposta é null
		assertThat(response.getBody()).isNull(); //verifica se é null
	}
	
	@Test
	void testGetAllUserByAdmin() throws URISyntaxException {
		//Login
		ResponseEntity<?> response = restTemplate.postForEntity(
				new URI("/api/auth/signin"), 
				new Login("admin@ifpb.edu.br", "pass"), 
				JwtResponse.class);

		//Obtendo o token
		JwtResponse jresp = (JwtResponse)response.getBody();
		String token = jresp.getToken();

		//Criando requisição para obter todos a lista de usuários
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);

		HttpEntity<String> jwtEntity = new HttpEntity<String>(headers);
		ResponseEntity<List> responseUsers = restTemplate.exchange("/user/getAll", HttpMethod.GET, jwtEntity,
				List.class);

		//Verificando se a resposta é 200
		assertTrue( responseUsers.getStatusCode().equals(HttpStatus.OK));

		//Verificando se os dois usuários foram retornados
		List<User> list = (List<User>)responseUsers.getBody();
		assertEquals(list.size(), 2);
	}
	
	@Test
	void testGetAllUserByUserNoAuthorized() throws URISyntaxException {
		//Login
		ResponseEntity<?> response = restTemplate.postForEntity(
				new URI("/api/auth/signin"), 
				new Login("user@ifpb.edu.br", "pass"), 
				JwtResponse.class);

		//Obtendo o token
		JwtResponse jresp = (JwtResponse)response.getBody();
		String token = jresp.getToken();

		//Criando requisição para obter todos a lista de usuários
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);

		HttpEntity<String> jwtEntity = new HttpEntity<String>(headers);
		ResponseEntity<List> responseUsers = restTemplate.exchange("/user/getAll", HttpMethod.GET, jwtEntity,
				List.class);

		//Verifica se o status é 403
		assertEquals(responseUsers.getStatusCode(), HttpStatus.FORBIDDEN);

		//Verifica se a resposta é null
		assertThat(responseUsers.getBody()).isNull(); //verifica se é null
	}
}