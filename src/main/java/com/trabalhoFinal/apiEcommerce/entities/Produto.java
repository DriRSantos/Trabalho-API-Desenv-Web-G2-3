package com.trabalhoFinal.apiEcommerce.entities;

import java.time.LocalDate;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class,
		property = "id_produto",
		scope =  Produto.class
		) 
@Entity
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_produto")
	private Integer id_produto;

	@NotBlank(message = "O nome deve ser preenchido!" )
	@Column(name = "nome")
	private String nome;

	@NotBlank(message = "A descrição deve ser preenchida!" )
	@Column(name = "descricao")
	private String descricao;

	
	@Min(value = 0, message = "A quantidade no estoque não deve ser negativa!" )
	@Column(name = "qtd_estoque")
	private Integer qtd_estoque;


	@Column(name = "data_cadastro")
	private LocalDate data_cadastro;
	
	@NotNull(message = "O valor unitario deve ser preenchido!" )
	@Column(name = "valor_unitario")
	private Double valor_unitario;

	@ManyToOne
	@JoinColumn(name = "id_categoria", referencedColumnName = "id_categoria")
	private Categoria categoria;

	@OneToMany(mappedBy = "produto")
	private Set<ItemPedido> itemPedidos;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_imagem", referencedColumnName = "id_imagem")
	private UploadArquivo arquivo;

	
	public UploadArquivo getArquivo() {
		return arquivo;
	}

	public void setArquivo(UploadArquivo arquivo) {
		this.arquivo = arquivo;
	}

	public Integer getId_produto() {
		return id_produto;
	}

	public void setId_produto(Integer id_produto) {
		this.id_produto = id_produto;
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
	
	public void atualizaEstoque(Integer metodo, Integer quantidade) {
		
		switch(metodo) {
			case 0:
				this.qtd_estoque -= quantidade;
				break;
			case 1:
				this.qtd_estoque += quantidade;
				break;
		}
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

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Set<ItemPedido> getItemPedidos() {
		return itemPedidos;
	}

	public void setItemPedidos(Set<ItemPedido> itemPedidos) {
		this.itemPedidos = itemPedidos;
	}

}
