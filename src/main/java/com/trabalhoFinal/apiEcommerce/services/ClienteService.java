package com.trabalhoFinal.apiEcommerce.services;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.trabalhoFinal.apiEcommerce.dto.ClienteDTO;
import com.trabalhoFinal.apiEcommerce.dto.EnderecoClienteDTO;
import com.trabalhoFinal.apiEcommerce.dto.MessageDTO;
import com.trabalhoFinal.apiEcommerce.dto.MessageResponseDTO;
import com.trabalhoFinal.apiEcommerce.dto.PedidoClienteDTO;
import com.trabalhoFinal.apiEcommerce.entities.Cliente;
import com.trabalhoFinal.apiEcommerce.entities.Pedido;
import com.trabalhoFinal.apiEcommerce.exceptions.ClienteNotFoundException;
import com.trabalhoFinal.apiEcommerce.repositories.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	EmailService emailService;

	public List<Cliente> getAllClientes() {
		return clienteRepository.findAll();
	}

	public List<ClienteDTO> getAllClientesDTO() {
		ModelMapper modelMapper = new ModelMapper();

		List<ClienteDTO> clientesDto = new ArrayList<>();

		// percorre cada cliente no banco
		for (Cliente cliente : clienteRepository.findAll()) {
			ClienteDTO novoClienteDto = modelMapper.map(cliente, ClienteDTO.class);

			// pega o endereço do cliente e converte pra dto
			novoClienteDto.setEndereco(modelMapper.map(cliente.getEndereco(), EnderecoClienteDTO.class));

			// percore a lista de pedidos do cliente e converte pra dto
			List<PedidoClienteDTO> pedidosDTO = new ArrayList<>();
			for (Pedido pedido : cliente.getPedidos()) {
				PedidoClienteDTO novoPedidoDto = modelMapper.map(pedido, PedidoClienteDTO.class);
				pedidosDTO.add(novoPedidoDto);
			}

			novoClienteDto.setPedidos(pedidosDTO);
			clientesDto.add(novoClienteDto);
		}
		return clientesDto;
	}
	
	public MessageResponseDTO sentEmail(String nome, String  mailFrom, String mensagem) {
	    emailService.enviarEmailPawn(nome,  mailFrom, mensagem);
	    return new MessageResponseDTO("E-mail enviado com sucesso!");
	}

	public Cliente getClienteById(Integer id) {
		return clienteRepository.findById(id).orElseThrow(() -> new ClienteNotFoundException(id));

	}

	public Cliente saveCliente(Cliente cliente) {
		try {
			return clienteRepository.save(cliente);
		}
		catch (DataAccessException e) {
			throw new ClienteNotFoundException("");
		}
		
	}

	public Cliente updateCliente(Cliente cliente, Integer id) {
		return clienteRepository.save(cliente);
	}

	public MessageDTO delCliente(Integer id) {
		clienteRepository.findById(id).orElseThrow(() -> new ClienteNotFoundException(id));

		clienteRepository.deleteById(id);
		return new MessageDTO("Cliente excluido com sucesso");

	}
}
