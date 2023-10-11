package edu.ifpb.model.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import edu.ifpb.security.jwt.AuthTokenFilter;
import edu.ifpb.service.UserService;
import edu.ifpb.util.NotificacaoEmailBean;

@Configuration
public class Config {

	@Bean
	@Profile("teste")
	public NotificacaoEmailBean forTest() {
		return new NotificacaoEmailBean("test.smtp.com", "teste123");
	}

	@Bean
	@Profile("!teste")
	public NotificacaoEmailBean forProduction() {
		return new NotificacaoEmailBean("empresa.smtp.com", "senhasecreta");
	}

//	@Bean
//	public NotificacaoSMS notificacaoSmsBean() {
//		return new NotificacaoSMS();
//	}
	
//	@Bean
//	@Profile("teste")
//	public DataSource dataSource() {
//		DriverManagerDataSource ds = new DriverManagerDataSource();
//		ds.setDriverClassName("org.h2.Driver");
//		ds.setUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
//		ds.setUsername("in_test");
//		ds.setPassword("sa");
//
//		return ds;
//	}
}

