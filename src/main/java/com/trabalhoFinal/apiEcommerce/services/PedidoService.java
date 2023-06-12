package com.trabalhoFinal.apiEcommerce.services;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.trabalhoFinal.apiEcommerce.dto.MessageDTO;
import com.trabalhoFinal.apiEcommerce.dto.PedidoDTO;
import com.trabalhoFinal.apiEcommerce.dto.ProdutoEmailDTO;
import com.trabalhoFinal.apiEcommerce.dto.ProdutoPedidoDTO;
import com.trabalhoFinal.apiEcommerce.dto.RelatorioPedidoDTO;
import com.trabalhoFinal.apiEcommerce.entities.Cliente;
import com.trabalhoFinal.apiEcommerce.entities.ItemPedido;
import com.trabalhoFinal.apiEcommerce.entities.Pedido;
import com.trabalhoFinal.apiEcommerce.exceptions.ClienteNotFoundException;
import com.trabalhoFinal.apiEcommerce.exceptions.PedidoNotFoundException;
import com.trabalhoFinal.apiEcommerce.repositories.ClienteRepository;
import com.trabalhoFinal.apiEcommerce.repositories.PedidoRepository;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	EmailService emailService;

	public List<Pedido> getAllPedidos() {
		return pedidoRepository.findAll();
	}

	public List<PedidoDTO> getAllPedidosDTO() {
		ModelMapper modelMapper = new ModelMapper();

		List<PedidoDTO> pedidosDto = new ArrayList<>();

		for (Pedido pedido : pedidoRepository.findAll()) {
			PedidoDTO novoPedidoDto = modelMapper.map(pedido, PedidoDTO.class);
			novoPedidoDto.setId_cliente(pedido.getCliente().getId_cliente());

			List<ProdutoPedidoDTO> prodPedDto = new ArrayList<>();

			for (ItemPedido itemPedido : pedido.getItemPedidos()) {
				ProdutoPedidoDTO novoProdDto = modelMapper.map(itemPedido.getProduto(), ProdutoPedidoDTO.class);
				novoProdDto.setQuantidade(itemPedido.getQuantidade());
				novoProdDto.setValor(itemPedido.getValor_liquido());
				prodPedDto.add(novoProdDto);
			}

			novoPedidoDto.setProdutos(prodPedDto);
			pedidosDto.add(novoPedidoDto);
		}

		return pedidosDto;
	}

	public Pedido getPedidoById(Integer id) {
		return pedidoRepository.findById(id).orElseThrow(() -> new PedidoNotFoundException(id));
	}
	
	public PedidoDTO getPedidoUserById(Integer id, String email) {
		pedidoRepository.findById(id).orElseThrow(() -> new PedidoNotFoundException(id));
		
		String clienteEmail = pedidoRepository.findById(id).get().getCliente().getEmail();
		
		if(clienteEmail.equals(email)) {
			ModelMapper modelMapper = new ModelMapper();
			PedidoDTO pedidoDTO = modelMapper.map(pedidoRepository.findById(id), PedidoDTO.class);
			pedidoDTO.setId_cliente(pedidoRepository.findById(id).get().getCliente().getId_cliente());
			
			List<ProdutoPedidoDTO> listaProdutos = new ArrayList<>();
			
			for(ItemPedido itemPedido: pedidoRepository.findById(id).get().getItemPedidos()) {
				
				ProdutoPedidoDTO prodPedDto = modelMapper.map(itemPedido.getProduto(), ProdutoPedidoDTO.class);
				prodPedDto.setQuantidade(itemPedido.getQuantidade());
				listaProdutos.add(prodPedDto);
			}
			
			pedidoDTO.setProdutos(listaProdutos);
			
			return pedidoDTO;
		} else {
			return null;
		}
	}
	
	public List<PedidoDTO> getAllPedidosClienteById(Integer id) {
		
		Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new ClienteNotFoundException(id));
		List<Pedido> pedidos = cliente.getPedidos();
		
		if(pedidos.size() != 0) {
			ModelMapper modelMapper = new ModelMapper();
			List<PedidoDTO> pedidosDTO = new ArrayList<>();
			
			for(Pedido pedido: pedidos) {
				PedidoDTO pedidoDto = modelMapper.map(pedido, PedidoDTO.class);
				pedidoDto.setId_cliente(pedido.getCliente().getId_cliente());
				
				List<ProdutoPedidoDTO> produtosPedido = new ArrayList<>();
				
				for(ItemPedido itemPedido: pedido.getItemPedidos()) {
					ProdutoPedidoDTO produtoDto = modelMapper.map(itemPedido.getProduto(), ProdutoPedidoDTO.class);
					produtoDto.setQuantidade(itemPedido.getQuantidade());
					produtosPedido.add(produtoDto);
				}
				
				pedidoDto.setProdutos(produtosPedido);
				pedidosDTO.add(pedidoDto);
			}
			
			return pedidosDTO;
			
		} else {
			throw new PedidoNotFoundException("Não há pedidos cadastrados para esse cliente!");
		}
	}
	
	// Gera Relatório de Pedido e envia por email
	public MessageDTO requestRelatorio(Integer id) {
		ModelMapper modelMapper = new ModelMapper();

		Optional<Pedido> pedido = pedidoRepository.findById(id);

		if (pedido != null) {
			RelatorioPedidoDTO pedidoEmail = modelMapper.map(pedidoRepository.findById(id), RelatorioPedidoDTO.class);

			List<ProdutoEmailDTO> prodPedDto = new ArrayList<>();

			for (ItemPedido itemPedido : pedidoRepository.findById(id).get().getItemPedidos()) {
				ProdutoEmailDTO novoProdDto = modelMapper.map(itemPedido.getProduto(), ProdutoEmailDTO.class);
				novoProdDto.setQuantidade(itemPedido.getQuantidade());
				novoProdDto.setValor(itemPedido.getValor_liquido());
				novoProdDto.setValor_bruto(itemPedido.getValor_bruto());
				novoProdDto.setPercentual_desconto(itemPedido.getPercentual_desconto());
				novoProdDto.setValor_liquido(itemPedido.getValor_liquido());
				novoProdDto.setUrl_imagem(itemPedido.getProduto().getArquivo().getUrl_imagem());
				novoProdDto.setPreco_venda(itemPedido.getPreco_venda());
				prodPedDto.add(novoProdDto);
			}

			pedidoEmail.setProdutos(prodPedDto);
			pedidoEmail.setNome_cliente(pedidoRepository.findById(id).get().getCliente().getNome_completo());;

//			emailService.enviarEmail("teste@hotmail.com", "Relatório de Pedido " + pedidoEmail.getId_pedido(), pedidoEmail);
			return new MessageDTO("Relatório enviado com sucesso!");
		} else {
			return new MessageDTO("Relatório indisponível");
		}

	}

	public Pedido savePedido(Pedido pedido) {
	try {
		return pedidoRepository.save(pedido);
	} catch(DataAccessException e) {
		throw new NoSuchElementException("");
	}
	
	}

	public Pedido updatePedido(Pedido pedido) {
		return pedidoRepository.save(pedido);
	}

	public MessageDTO delPedido(Integer id) {
		pedidoRepository.findById(id).orElseThrow(() -> new PedidoNotFoundException(id));

		pedidoRepository.deleteById(id);
		return new MessageDTO("Pedido excluido com sucesso");

	}
}
