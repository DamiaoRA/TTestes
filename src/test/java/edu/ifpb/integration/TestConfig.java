package edu.ifpb.integration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import edu.ifpb.util.NotificacaoSMS;

@TestConfiguration
public class TestConfig {
	@Bean
	public NotificacaoSMS notificacaoSmsBean() {
		return new NotificacaoSMS();
	}
}
