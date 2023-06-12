package com.trabalhoFinal.apiEcommerce.dto;

public class MessageResponseDTO {
	
	private String nome;
	private String email;
	private String mensagem;
	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public MessageResponseDTO(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getMessage() {
		return mensagem;
	}

	public void setMessage(String mensagem) {
		this.mensagem = mensagem;
	}
}