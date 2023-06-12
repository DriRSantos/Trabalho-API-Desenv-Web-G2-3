package com.trabalhoFinal.apiEcommerce.entities;

import java.time.LocalDate;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class,
		property = "id_pedido",
		scope =  Pedido.class
		) 
@Entity
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_pedido")
	private Integer id_pedido;

	@NotNull(message = "A data do pedido deve ser preenchida!" )
	@Column(name = "data_pedido")
	private LocalDate data_pedido;
	
	@NotNull(message = "A data de entrega deve ser preenchida!" )
	@Column(name = "data_entrega")
	private LocalDate data_entrega;

	@NotNull(message = "A data de envio deve ser preenchida!" )
	@Column(name = "data_envio")
	private LocalDate data_envio;

	@NotBlank(message = "O status da mercadoria deve ser preenchido!" )
	@Column(name = "status")
	private String status;

	@Column(name = "valor_total")
	private Double valor_total = 0.0;

	@ManyToOne
	@JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
	private Cliente cliente;

	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	private Set<ItemPedido> itemPedidos;

	
	public Integer getId_pedido() {
		return id_pedido;
	}

	public void setId_pedido(Integer id_pedido) {
		this.id_pedido = id_pedido;
	}

	public LocalDate getData_pedido() {
		return data_pedido;
	}

	public void setData_pedido(LocalDate data_pedido) {
		 LocalDate currentDate = LocalDate.now(); 

	       if (data_pedido.isAfter(currentDate)) {
	           this.data_pedido = data_pedido;
	       } else {
	           throw new IllegalArgumentException("Data de Pedido inválida: Datas retroativas não são permitidas.");
	       }
	}

	public LocalDate getData_entrega() {
		return data_entrega;
	}

	public void setData_entrega(LocalDate data_entrega) {
		this.data_entrega = data_entrega;
	}

	public LocalDate getData_envio() {
		return data_envio;
	}

	public void setData_envio(LocalDate data_envio) {
		this.data_envio = data_envio;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getValor_total() {
		return valor_total;
	}

	public void setValor_total(Double valor, Integer r) {
		// r = 0 | saveItemPedido
		// r = 1 | delItemPedido
		
		switch(r) {
		case 0:
			this.valor_total += valor;
			break;
		case 1:
			this.valor_total -= valor;
			break;
		}
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Set<ItemPedido> getItemPedidos() {
		return itemPedidos;
	}

	public void setItemPedidos(Set<ItemPedido> itemPedidos) {
		this.itemPedidos = itemPedidos;
	}
}
