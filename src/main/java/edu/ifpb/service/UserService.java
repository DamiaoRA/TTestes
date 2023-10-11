package edu.ifpb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import edu.ifpb.model.User;
import edu.ifpb.repository.UserRepository;
import edu.ifpb.util.AppUtils;
import edu.ifpb.util.NotificacaoEmailBean;
import edu.ifpb.util.NotificacaoSMS;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private AppUtils appUtils;
	
	@Autowired
	private NotificacaoEmailBean notificacao;
	
//	@Autowired
	private NotificacaoSMS notificacaoSMS;

	public User save(User u) {
		u.setName(appUtils.toUpperCase(u.getName()));
		return repository.save(u);
	}

	public User changeName(Long id, String name){
		//Lógica
		name = appUtils.toUpperCase(name);

		//acesso externo
		User u = repository.getReferenceById(id);
		u.setName(name);
		return repository.save(u);
	}

	public void enviarNotificacao() {
		System.out.println(notificacao.getServidor() + " " + notificacao.getSenha());
		notificacaoSMS.enviarNotificacao();
	}

	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	public User findById(Long id) {
		Optional<User> opUser = repository.findById(id);
		if(!opUser.isEmpty())
			return opUser.get();
		else 
			return null;
	}

	public UserDetails loadUserByEmail(String email) {
		return repository.findByEmail(email);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return loadUserByEmail(email);
	}

	public List<User> findAll() {
		return repository.findAll();
	}
}