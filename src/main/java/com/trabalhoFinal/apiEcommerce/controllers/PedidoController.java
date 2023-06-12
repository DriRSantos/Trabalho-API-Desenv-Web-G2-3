package com.trabalhoFinal.apiEcommerce.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trabalhoFinal.apiEcommerce.dto.MessageDTO;
import com.trabalhoFinal.apiEcommerce.dto.PedidoDTO;
import com.trabalhoFinal.apiEcommerce.entities.Pedido;
import com.trabalhoFinal.apiEcommerce.security.service.UserDetailsImpl;
import com.trabalhoFinal.apiEcommerce.services.PedidoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	PedidoService pedidoService;

	@GetMapping
	public ResponseEntity<List<Pedido>> getAllPedidos() {
		return new ResponseEntity<>(pedidoService.getAllPedidos(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Pedido> getPedidoById(@PathVariable Integer id) {
		
		return new ResponseEntity<>(pedidoService.getPedidoById(id), HttpStatus.OK);
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<?> getPedidoUserById(@PathVariable Integer id) {
		
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userEmail = userDetails.getEmail();
		PedidoDTO pedidoDTO = pedidoService.getPedidoUserById(id, userEmail);
		
		if(pedidoDTO != null)
			return new ResponseEntity<>(pedidoDTO, HttpStatus.OK);
		else
			return new ResponseEntity<>(new MessageDTO("Esse pedido não está relacionado a esse usuário"), HttpStatus.OK);
	}

	@GetMapping("/relatorio/{id}")
	public ResponseEntity<MessageDTO> getRelatorio(@PathVariable Integer id) {
		return new ResponseEntity<>(pedidoService.requestRelatorio(id), HttpStatus.OK);
	}
	
	@GetMapping("/cliente/{id}")
	public ResponseEntity<List<PedidoDTO>> getAllPedidosClienteById(@PathVariable Integer id) {
		return new ResponseEntity<>(pedidoService.getAllPedidosClienteById(id), HttpStatus.OK);
	}

	// DTO
	@GetMapping("/dto")
	public ResponseEntity<List<PedidoDTO>> getAllPedidosDTO() {
		return new ResponseEntity<>(pedidoService.getAllPedidosDTO(), HttpStatus.OK);
	}	
	

	@PostMapping
	public ResponseEntity<Pedido> savePedido(@RequestBody @Valid Pedido pedido) {
		return new ResponseEntity<>(pedidoService.savePedido(pedido), HttpStatus.CREATED);			
	}

	@PutMapping
	public ResponseEntity<Pedido> updatePedido(@RequestBody @Valid Pedido pedido, Integer id) {
		return new ResponseEntity<>(pedidoService.updatePedido(pedido), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<MessageDTO> delPedido(@PathVariable Integer id) {
		return new ResponseEntity<>(pedidoService.delPedido(id), HttpStatus.OK);

	}
}
