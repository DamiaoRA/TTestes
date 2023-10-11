package edu.ifpb.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.longThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import edu.ifpb.model.User;
import edu.ifpb.repository.UserRepository;
import edu.ifpb.util.AppUtils;

public class TesteUserServiceMockito {

	@Mock
	private UserRepository repository;

	@InjectMocks
	private UserService service;

	@Captor
    private ArgumentCaptor<User> userCaptor;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		// Injete manualmente um objeto AppUtils no Service
		AppUtils utils = new AppUtils();
        ReflectionTestUtils.setField(service, "appUtils", utils);
	}

	@Test
	void testSaveUser() {
		User u = new User();
		u.setId(1L);
		u.setName("Maria");
		u.setEmail("maria@ifpb.edu.br");

        //Definindo comportamento do mock
		when(repository.save(u)).thenReturn(u);
		
		//Testando a lógica do negócio
		User newUser = service.save(u);
		
		assertEquals(newUser.getName(), "MARIA");
	}
	
	@Test
	void testChangeName() {
		User u = new User();
		u.setName("Maria");
		u.setEmail("maria@ifpb.edu.br");

		//Comportamento
		when(repository.getReferenceById(1L)).thenReturn(u);
		when(repository.save(u)).thenReturn(u);
		
		//asserts
		String newName = "Mariana";
		User newUser = service.changeName(1L, newName);

		assertEquals(newUser.getName(), newName.toUpperCase());
	}
	
	@Test
	void testUserMock() {
		User u = mock(User.class);
		when(u.getId()).thenReturn(1L);
		when(u.getEmail()).thenReturn("m@ifpb.edu.br");
		when(u.getName()).thenReturn("Maria");

		User u2 = new User();
		u2.setName("Maria");
		u2.setEmail("m@ifpb.edu.br");
		u2.setId(1L);

//		assertEquals(u2, u);
		assertTrue(u2.equals(u));
	}

	@Test
	void testThrowException() {
		//Comportamento
		when(repository.getReferenceById(-1L))
			.thenThrow(new ObjectNotFoundException("Erro: objeto não encontrado", -1L));

		assertThrows(ObjectNotFoundException.class, () -> repository.getReferenceById(-1L));


		when(repository.getReferenceById(longThat(argument -> argument <= 0)))
		.thenThrow(new ObjectNotFoundException("Erro: objeto não encontrado", -1L));

		assertThrows(ObjectNotFoundException.class, () -> repository.getReferenceById(-50L));
	}

	@Test
	void testCaptor() {
		User u = new User();
		u.setId(1L);
		u.setName("Maria");
		u.setEmail("maria@ifpb.edu.br");

        //Definindo comportamento do mock
		when(repository.save(u)).thenReturn(u);

		//Testando a lógica do negócio
		User newUser = service.save(u);

		// Capture o argumento passado para o método mockado
		verify(repository).save(userCaptor.capture());

		// Faça asserções nos argumentos capturados
        assertTrue(u.equals(userCaptor.getValue()));

		assertEquals(newUser.getName(), "MARIA");
	}
}