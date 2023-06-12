package com.trabalhoFinal.apiEcommerce.controllers;

import java.util.List;

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

import com.trabalhoFinal.apiEcommerce.dto.MessageDTO;
import com.trabalhoFinal.apiEcommerce.entities.Endereco;
import com.trabalhoFinal.apiEcommerce.repositories.EnderecoRepository;
import com.trabalhoFinal.apiEcommerce.services.EnderecoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

	@Autowired
	EnderecoService enderecoService;

	@Autowired
	EnderecoRepository enderecoRepository;

	@GetMapping
	public ResponseEntity<List<Endereco>> getAllEnderecos() {
		return new ResponseEntity<>(enderecoService.getAllEnderecos(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Endereco> getEnderecoById(@PathVariable Integer id) {
		return new ResponseEntity<>(enderecoService.getEnderecoById(id), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Endereco> saveEndereco(@RequestBody @Valid Endereco endereco) {

		return new ResponseEntity<>(enderecoService.saveEndereco(endereco), HttpStatus.CREATED);

	}

	@PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
	@PutMapping
	public ResponseEntity<Endereco> updateEndereco(@RequestBody @Valid Endereco endereco, Integer id) {

		return new ResponseEntity<>(enderecoService.updateEndereco(endereco, id), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<MessageDTO> delEndereco(@PathVariable Integer id) {
		return new ResponseEntity<>(enderecoService.delEndereco(id), HttpStatus.OK);

	}
}
