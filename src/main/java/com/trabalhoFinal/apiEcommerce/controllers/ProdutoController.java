package com.trabalhoFinal.apiEcommerce.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trabalhoFinal.apiEcommerce.dto.MessageDTO;
import com.trabalhoFinal.apiEcommerce.dto.MessageResponseDTO;
import com.trabalhoFinal.apiEcommerce.dto.ProdutoDTO;
import com.trabalhoFinal.apiEcommerce.entities.Produto;
import com.trabalhoFinal.apiEcommerce.repositories.ProdutoRepository;
import com.trabalhoFinal.apiEcommerce.services.ProdutoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

	@Autowired
	ProdutoService produtoService;

	@Autowired
	ProdutoRepository produtoRepository;

	@GetMapping
	public ResponseEntity<List<Produto>> getAllProdutos() {
		return new ResponseEntity<>(produtoService.getAllProdutos(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Produto> getProdutoById(@PathVariable Integer id) {
		return new ResponseEntity<>(produtoService.getProdutoById(id), HttpStatus.OK);
	}

	// DTO

	@GetMapping("/dto")
	public ResponseEntity<List<ProdutoDTO>> getAllProdutosDTO() {
		return new ResponseEntity<>(produtoService.getAllProdutosDTO(), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> saveProduto(@RequestBody @Valid Produto produto) {

		if (produtoRepository.existsByDescricao(produto.getDescricao()))
			return ResponseEntity.badRequest().body(new MessageResponseDTO("Erro: Descrição já utilizada!"));

		return new ResponseEntity<>(produtoService.saveProduto(produto), HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<Produto> updateProduto(@RequestBody @Valid Produto produto, Integer id) {
		return new ResponseEntity<>(produtoService.updateProduto(produto), HttpStatus.OK);
	}
	
	@PutMapping("/{id}/quantidade") 
	public ResponseEntity<Produto> updateProdutoQuantity(@PathVariable Integer id, @RequestBody Integer qtd_estoque) {
	    Produto updatedProduto = produtoService.updateProdutoQuantity(id, qtd_estoque);
	    return new ResponseEntity<>(updatedProduto, HttpStatus.OK);
	}	

	@DeleteMapping("/{id}")
	public ResponseEntity<MessageDTO> delProduto(@PathVariable Integer id) {
		return new ResponseEntity<>(produtoService.delProduto(id), HttpStatus.OK);

	}
}
