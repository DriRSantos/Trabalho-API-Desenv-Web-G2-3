package com.trabalhoFinal.apiEcommerce.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trabalhoFinal.apiEcommerce.dto.ClienteDTO;
import com.trabalhoFinal.apiEcommerce.dto.MessageDTO;
import com.trabalhoFinal.apiEcommerce.dto.MessageResponseDTO;
import com.trabalhoFinal.apiEcommerce.entities.Cliente;
import com.trabalhoFinal.apiEcommerce.repositories.ClienteRepository;
import com.trabalhoFinal.apiEcommerce.services.ClienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	ClienteService clienteService;
	
	@Autowired
	ClienteRepository clienteRepository;
	
	
	@GetMapping 
	public ResponseEntity<List<Cliente>> getAllClientes() {
		return new ResponseEntity<>(clienteService.getAllClientes(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> getClienteById(@PathVariable Integer id) {
		return new ResponseEntity<>(clienteService.getClienteById(id), HttpStatus.OK);
	}
	
	
	// DTO
	@GetMapping ("/dto")
	public ResponseEntity<List<ClienteDTO>> getAllClientesDTO() {
		return new ResponseEntity<>(clienteService.getAllClientesDTO(), HttpStatus.OK);
	}
	
	@PostMapping("/mensagem")
	public ResponseEntity<MessageResponseDTO> sentEmail(@RequestBody Map<String, String> request) {
		System.out.println("teste");   
		String nome = request.get("nome");
		String mailFrom = request.get("email");
		String mensagem = request.get("mensagem");
		
		return new ResponseEntity<>(clienteService.sentEmail(nome,  mailFrom, mensagem), HttpStatus.OK);
	}
	
	
	@PostMapping
	public ResponseEntity<?> saveCliente(@RequestBody @Valid Cliente cliente) {
		
		if(clienteRepository.existsByCpf(cliente.getCpf()))
			return ResponseEntity.badRequest().body(new MessageResponseDTO("Erro: Cpf cadastrado já utilizado!"));
		
		if(clienteRepository.existsByEmail(cliente.getEmail()))
			return ResponseEntity.badRequest().body(new MessageResponseDTO("Erro: Email cadastrado já utilizado!"));
		
		return new ResponseEntity<>(clienteService.saveCliente(cliente), HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<Cliente> updateCliente(@RequestBody @Valid Cliente cliente, Integer id) {
		return new ResponseEntity<>(clienteService.updateCliente(cliente, id), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<MessageDTO> delCliente(@PathVariable Integer id) {
		return new ResponseEntity<>(clienteService.delCliente(id), HttpStatus.OK);
		
	}
}
