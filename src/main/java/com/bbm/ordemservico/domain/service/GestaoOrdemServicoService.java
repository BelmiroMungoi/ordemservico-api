package com.bbm.ordemservico.domain.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbm.ordemservico.domain.exceptionhandler.NegocioException;
import com.bbm.ordemservico.domain.model.Cliente;
import com.bbm.ordemservico.domain.model.OrdemServico;
import com.bbm.ordemservico.domain.model.StatusOrdemServico;
import com.bbm.ordemservico.domain.repository.ClienteRepository;
import com.bbm.ordemservico.domain.repository.OrdemServicoRepository;

@Service
public class GestaoOrdemServicoService {

	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public OrdemServico createOrder(OrdemServico ordemServico) {
		
		Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId())
				.orElseThrow(() -> new NegocioException("Cliente não encontrado"));
		
		ordemServico.setCliente(cliente);
		ordemServico.setStatus(StatusOrdemServico.ABERTA);
		ordemServico.setDataAbertura(LocalDateTime.now());
		
		return ordemServicoRepository.save(ordemServico);
	}
}
