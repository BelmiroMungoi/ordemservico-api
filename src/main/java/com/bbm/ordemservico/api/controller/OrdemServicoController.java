package com.bbm.ordemservico.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bbm.ordemservico.domain.model.OrdemServico;
import com.bbm.ordemservico.domain.service.GestaoOrdemServicoService;

@RestController
@RequestMapping("/ordem-servico")
public class OrdemServicoController {

	@Autowired
	private GestaoOrdemServicoService gestaoOrdemServico;
	
	@PostMapping("/")
	public ResponseEntity<OrdemServico> create(@RequestBody OrdemServico ordemServico) {
		
		OrdemServico order = gestaoOrdemServico.createOrder(ordemServico);
		
		return new ResponseEntity<OrdemServico>(order, HttpStatus.CREATED);
	}
}
