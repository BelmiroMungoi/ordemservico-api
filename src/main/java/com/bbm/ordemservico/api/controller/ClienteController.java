package com.bbm.ordemservico.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bbm.ordemservico.domain.model.Cliente;
import com.bbm.ordemservico.domain.repository.ClienteRepository;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@GetMapping("/")
	public ResponseEntity<List<Cliente>> findAllClients(){
		
		List<Cliente> clientes = clienteRepository.findAll();
		
		return new ResponseEntity<List<Cliente>>(clientes, HttpStatus.OK);
	}
}
