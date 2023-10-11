package edu.ifpb.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ifpb.model.User;
import edu.ifpb.repository.UserRepository;

//@SpringBootTest
//@Import(TestConfig.class)

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserRepositoryIntegrationTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	public void testFindUserIgnoreCase() {
		User u = userRepository.findByNameIgnoreCase("MARIA");
		assertEquals(u.getName(), "Maria");
	}
	
	@Test
	public void testSaveUser() {
		User u = new User();
		u.setId(20L);
		u.setName("Rita");
		u.setEmail("rita@ifpb.com");

		userRepository.save(u);

		User u2 = userRepository.findById(20L).get();
		assertEquals(u, u2);
	}

	@Test
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void testSaveUserWithEmptyName() {
		User u = new User();
		u.setId(10L);
		u.setName("");
		u.setEmail("fulano@ifpb.com");

		Assertions.assertThatThrownBy(() -> {
			userRepository.save(u);
		}).isInstanceOf(TransactionSystemException.class);
	}
	
	@Test
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void testSaveUserWithEmptyEmail() {
		User u = new User();
		u.setId(10L);
		u.setName("Fulano");
		u.setEmail("");

		Assertions.assertThatThrownBy(() -> {
			userRepository.save(u);
		}).isInstanceOf(TransactionSystemException.class);
	}
	
	@Test
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void testSaveUserWithWrongEmail() {
		User u = new User();
		u.setId(10L);
		u.setName("Fulano");
		u.setEmail("fulano@ifpb.c");

		Assertions.assertThatThrownBy(() -> {
			userRepository.save(u);
		}).isInstanceOf(TransactionSystemException.class);
		
		u.setEmail("fulano@ifpb.com");
		Assertions.assertThatNoException().isThrownBy(
				() -> userRepository.save(u)
		);
	}
}