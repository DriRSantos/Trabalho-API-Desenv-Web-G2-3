package com.trabalhoFinal.apiEcommerce.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trabalhoFinal.apiEcommerce.dto.CategoriaProdDTO;
import com.trabalhoFinal.apiEcommerce.dto.MessageDTO;
import com.trabalhoFinal.apiEcommerce.dto.ProdutoDTO;
import com.trabalhoFinal.apiEcommerce.entities.Produto;
import com.trabalhoFinal.apiEcommerce.exceptions.ProdutoNotFoundException;
import com.trabalhoFinal.apiEcommerce.repositories.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	public List<Produto> getAllProdutos() {
		return produtoRepository.findAll();
	}

	public List<ProdutoDTO> getAllProdutosDTO() {
		ModelMapper modelMapper = new ModelMapper();

		List<ProdutoDTO> produtosDTO = new ArrayList<>();

		for (Produto produto : produtoRepository.findAll()) {
			ProdutoDTO novoProdutoDto = modelMapper.map(produto, ProdutoDTO.class);
			novoProdutoDto.setCategoriaProdDto(modelMapper.map(produto.getCategoria(), CategoriaProdDTO.class));
			novoProdutoDto.setId_produto(produto.getId_produto());
			novoProdutoDto.setId_imagem(produto.getArquivo().getId_imagem());
			novoProdutoDto.setNome_imagem(produto.getArquivo().getNome());
			novoProdutoDto.setUrl_imagem(produto.getArquivo().getUrl_imagem());
			produtosDTO.add(novoProdutoDto);
		}
		return produtosDTO;
	}

	public Produto getProdutoById(Integer id) {
		return produtoRepository.findById(id).orElseThrow(() -> new ProdutoNotFoundException(id));

	}

	public Produto saveProduto(Produto produto) {

		LocalDate localDate = LocalDate.now();
		produto.setData_cadastro(localDate);
		return produtoRepository.save(produto);
	}

	public Produto updateProduto(Produto produto) {
		return produtoRepository.save(produto);
	}
	
	public Produto updateProdutoQuantity(Integer id, Integer qtd_estoque) {
	    Produto produto = produtoRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Produto not found"));

	    produto.setQtd_estoque(qtd_estoque);

	    return produtoRepository.save(produto);
	}

	public MessageDTO delProduto(Integer id) {
		produtoRepository.findById(id).orElseThrow(() -> new ProdutoNotFoundException(id));

		produtoRepository.deleteById(id);
		return new MessageDTO("Produto deletado com sucesso!");
	}
}
