package edu.ifpb.util;

import lombok.Data;

@Data
public class NotificacaoEmailBean {
	
	private String servidor;
	private String senha;
	
	public NotificacaoEmailBean(String servidor, String senha) {
		this.servidor = servidor;
		this.senha = senha;
	}
}
