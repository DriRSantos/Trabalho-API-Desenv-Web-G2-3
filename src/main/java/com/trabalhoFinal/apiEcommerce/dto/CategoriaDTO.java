package com.trabalhoFinal.apiEcommerce.dto;

import java.util.List;

public class CategoriaDTO {

	private Integer id_categoria;
	private String nome;
	private String descricao;
	private List<ProdutoCatDTO> produtos;
	
	public Integer getId_categoria() {
		return id_categoria;
	}
	public void setId_categoria(Integer id_categoria) {
		this.id_categoria = id_categoria;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public List<ProdutoCatDTO> getProdutos() {
		return produtos;
	}
	public void setProdutos(List<ProdutoCatDTO> produtos) {
		this.produtos = produtos;
	}
}
