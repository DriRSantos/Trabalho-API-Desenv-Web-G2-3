package com.trabalhoFinal.apiEcommerce.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.trabalhoFinal.apiEcommerce.dto.MessageDTO;
import com.trabalhoFinal.apiEcommerce.entities.Endereco;
import com.trabalhoFinal.apiEcommerce.exceptions.EnderecoNotFoundException;
import com.trabalhoFinal.apiEcommerce.exceptions.HttpClientErrorExceptionhandler;
import com.trabalhoFinal.apiEcommerce.repositories.EnderecoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository enderecoRepository;

	public List<Endereco> getAllEnderecos() {
		return enderecoRepository.findAll();
	}

	public Endereco getEnderecoById(Integer id) {
		return enderecoRepository.findById(id).orElseThrow(() -> new EnderecoNotFoundException(id));
	}

	public Endereco saveEndereco(Endereco endereco) {

		Endereco enderecoApi = consultaApiEnderecoWs(endereco.getCep());
		enderecoApi.setNumero(endereco.getNumero());
		enderecoApi.setComplemento(endereco.getComplemento());
		enderecoRepository.save(enderecoApi);
		return enderecoApi;

	}

	public Endereco updateEndereco(Endereco endereco, Integer id) {
		
		try {
		Endereco updateEndereco = enderecoRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Entidade não foi encontrada!"));
		
		updateData(updateEndereco, endereco);
		return enderecoRepository.save(updateEndereco);
	}
		catch (DataAccessException e) {
			throw new EnderecoNotFoundException(id);
		}
	}	

	private void updateData(Endereco updateEndereco, Endereco endereco) {
		
		Endereco enderecoApi = consultaApiEnderecoWs(endereco.getCep());
		
		updateEndereco.setCep(endereco.getCep());
		updateEndereco.setLogradouro(enderecoApi.getLogradouro());
		updateEndereco.setBairro(enderecoApi.getBairro());
		updateEndereco.setLocalidade(enderecoApi.getLocalidade());
		updateEndereco.setNumero(endereco.getNumero());
		updateEndereco.setComplemento(endereco.getComplemento());
		updateEndereco.setUf(enderecoApi.getUf());
	}
	
	

	public MessageDTO delEndereco(Integer id) {
		enderecoRepository.findById(id).orElseThrow(() -> new EnderecoNotFoundException(id));

		enderecoRepository.deleteById(id);
		return new MessageDTO("Endereço excluído com sucesso!");

	}

	// API
	private Endereco consultaApiEnderecoWs(String cep) {

		RestTemplate restTemplate = new RestTemplate();
		String uri = "https://viacep.com.br/ws/{cep}/json/";

		Map<String, String> params = new HashMap<String, String>();
		params.put("cep", cep);

		try {
			Endereco endereco = restTemplate.getForObject(uri, Endereco.class, params);
			return endereco;
			
		} catch (HttpClientErrorException e) {
			throw new HttpClientErrorExceptionhandler(cep);
		}

	}
}
