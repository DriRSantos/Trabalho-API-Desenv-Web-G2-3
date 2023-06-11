package com.trabalhoFinal.apiEcommerce.dto;

import java.time.LocalDate;

public class ProdutoDTO {

	private Integer id_produto;
	private String nome;
	private String descricao;
	private Integer qtd_estoque;
	private LocalDate data_cadastro;
	private Double valor_unitario;
	private Integer id_imagem;
	private String nome_imagem;
	private CategoriaProdDTO categoriaProdDto;
	private String url_imagem;

	public Integer getId_produto() {
		return id_produto;
	}

	public void setId_produto(Integer id_produto) {
		this.id_produto = id_produto;
	}

	public String getNome_imagem() {
		return nome_imagem;
	}

	public void setNome_imagem(String nome_imagem) {
		this.nome_imagem = nome_imagem;
	}

	public Integer getId_imagem() {
		return id_imagem;
	}

	public void setId_imagem(Integer id_imagem) {
		this.id_imagem = id_imagem;
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

	public Integer getQtd_estoque() {
		return qtd_estoque;
	}

	public void setQtd_estoque(Integer qtd_estoque) {
		this.qtd_estoque = qtd_estoque;
	}

	public LocalDate getData_cadastro() {
		return data_cadastro;
	}

	public void setData_cadastro(LocalDate data_cadastro) {
		this.data_cadastro = data_cadastro;
	}

	public Double getValor_unitario() {
		return valor_unitario;
	}

	public void setValor_unitario(Double valor_unitario) {
		this.valor_unitario = valor_unitario;
	}

	public CategoriaProdDTO getCategoriaProdDto() {
		return categoriaProdDto;
	}

	public void setCategoriaProdDto(CategoriaProdDTO categoriaProdDto) {
		this.categoriaProdDto = categoriaProdDto;
	}
	
	public String getUrl_imagem() {
		return url_imagem;
	}

	public void setUrl_imagem(String url_imagem) {
		this.url_imagem = url_imagem;
	}

}
