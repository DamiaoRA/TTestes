package edu.ifpb.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import edu.ifpb.model.User;
import edu.ifpb.repository.UserRepository;

@SpringBootTest
@Import(TestConfig.class)
@TestMethodOrder(value = OrderAnnotation.class)
public class RepositoryIntegrationTest {

	@Autowired
	private UserRepository repository;
	
	@BeforeEach
	public void setUp() {
//		repository.deleteAll();
	}
	
	@Test
	@Order(1)
	public void testBD() {
		List<User> lista = repository.findAll();
		assertEquals(2, lista.size());
	}

	@Test
	@Order(10)
	@Sql(statements = "delete from tb_user", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
	public void test2BD() {
		List<User> lista = repository.findAll();
		assertNotEquals(2, lista.size());
		assertEquals(0, lista.size());
	}

	@Test
	@Order(20)
	@Sql(statements = "delete from tb_user", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
	@Sql({"/insert1.sql", "/insert2.sql"})
	public void test3BD() {
		List<User> lista = repository.findAll();
		assertEquals(2, lista.size());
	}
}
