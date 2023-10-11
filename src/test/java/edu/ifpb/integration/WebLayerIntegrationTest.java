package edu.ifpb.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Import(TestConfig.class)
public class WebLayerIntegrationTest {

	@LocalServerPort
	private int port;

	//Cliente síncrono para fazer requisição HTTP
	private RestTemplate restTemplate;
	
	@Autowired
	private TestRestTemplate testRestTemplate;

	@BeforeEach
	public void setup() {
		restTemplate = new RestTemplate();
	}

	@Test
	void testPort() {
		System.out.println(port);
	}
	
	@Test
	void testTestRestTemplate() throws URISyntaxException {
		ResponseEntity<String> resposta = testRestTemplate.getForEntity(new URI("/hello"), String.class);
		assertEquals(resposta.getBody(), "Hello");
	}
	
	@Test
	void testHello() {
		String resposta = restTemplate.getForObject("http://localhost:" + port + "/hello", String.class);
		assertEquals(resposta, "Hello");
	}
}

