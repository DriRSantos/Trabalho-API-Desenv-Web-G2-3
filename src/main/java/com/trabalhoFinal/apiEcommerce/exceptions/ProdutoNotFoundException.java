package com.trabalhoFinal.apiEcommerce.exceptions;

public class ProdutoNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	

	public ProdutoNotFoundException(Integer id) {
		super("Não foi encontrado Produto com o id = " + id);
	}
}
