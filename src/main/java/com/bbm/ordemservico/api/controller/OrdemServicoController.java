package com.bbm.ordemservico.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bbm.ordemservico.domain.model.OrdemServico;
import com.bbm.ordemservico.domain.repository.OrdemServicoRepository;
import com.bbm.ordemservico.domain.service.GestaoOrdemServicoService;

@RestController
@RequestMapping("/ordem-servico")
public class OrdemServicoController {

	@Autowired
	private GestaoOrdemServicoService gestaoOrdemServico;

	@Autowired
	private OrdemServicoRepository ordemServicoRepository;

	@PostMapping("/")
	public ResponseEntity<OrdemServico> create(@Valid @RequestBody OrdemServico ordemServico) {

		OrdemServico order = gestaoOrdemServico.createOrder(ordemServico);

		return new ResponseEntity<OrdemServico>(order, HttpStatus.CREATED);
	}

	@GetMapping("/")
	public ResponseEntity<List<OrdemServico>> findAllOrder() {

		List<OrdemServico> ordens = ordemServicoRepository.findAll();

		return new ResponseEntity<List<OrdemServico>>(ordens, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findOrderById(@PathVariable("id") Long id) {

		Optional<OrdemServico> ordemServico = ordemServicoRepository.findById(id);

		if (ordemServico.isPresent()) {
			return new ResponseEntity<OrdemServico>(ordemServico.get(), HttpStatus.OK);
		}

		return new ResponseEntity<String>("Ordem de Servico não encontrada", HttpStatus.NOT_FOUND);
	}
	
}
