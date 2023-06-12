package com.trabalhoFinal.apiEcommerce.exceptions;

public class ClienteNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public ClienteNotFoundException(String message) {
        super(message);
    }

	public ClienteNotFoundException(Integer id) {
		super("Não foi encontrado Cliente com o id = " + id);
	}
}
