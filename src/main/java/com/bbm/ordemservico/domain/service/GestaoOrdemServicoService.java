package com.bbm.ordemservico.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbm.ordemservico.domain.exception.EntityNotFoundException;
import com.bbm.ordemservico.domain.exception.NegocioException;
import com.bbm.ordemservico.domain.model.Cliente;
import com.bbm.ordemservico.domain.model.Comentario;
import com.bbm.ordemservico.domain.model.OrdemServico;
import com.bbm.ordemservico.domain.model.enums.StatusOrdemServico;
import com.bbm.ordemservico.domain.repository.ClienteRepository;
import com.bbm.ordemservico.domain.repository.ComentarioRepository;
import com.bbm.ordemservico.domain.repository.OrdemServicoRepository;

@Service
public class GestaoOrdemServicoService {

	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ComentarioRepository comentarioRepository;
	
	public OrdemServico createOrder(OrdemServico ordemServico) {
		
		Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId())
				.orElseThrow(() -> new NegocioException("Cliente não encontrado"));
		
		ordemServico.setCliente(cliente);
		ordemServico.setStatus(StatusOrdemServico.ABERTA);
		ordemServico.setDataAbertura(OffsetDateTime.now());
		
		return ordemServicoRepository.save(ordemServico);
	}
	
	public void finalizarOrdemServico(Long id) {
		
		OrdemServico ordemServico = buscarOrdemServico(id);		
		ordemServico.finalizar();
		
		ordemServicoRepository.save(ordemServico);
	}
	
	public Comentario addComentario(Long id, String descricao) {
		
		OrdemServico ordemServico = buscarOrdemServico(id);
	
		Comentario comentario = new Comentario();
		comentario.setDataEnvio(OffsetDateTime.now());
		comentario.setDescricao(descricao);
		comentario.setOrdemServico(ordemServico);
		
		return comentarioRepository.save(comentario);
	}

	private OrdemServico buscarOrdemServico(Long id) {
		return ordemServicoRepository.findById(id)
				.orElseThrow(()  -> new EntityNotFoundException("Ordem de Servico não encontrada"));
	}
}
