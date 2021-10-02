package com.bbm.ordemservico.api.controller;

import java.util.List;
import java.util.Optional;

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

import com.bbm.ordemservico.domain.model.Cliente;
import com.bbm.ordemservico.domain.repository.ClienteRepository;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

	@Autowired
	private ClienteRepository clienteRepository;

	@PostMapping("/")
	public ResponseEntity<Cliente> saveClient(@RequestBody Cliente cliente) {

		Cliente client = clienteRepository.save(cliente);

		return new ResponseEntity<Cliente>(client, HttpStatus.CREATED);
	}

	@GetMapping("/")
	public ResponseEntity<List<Cliente>> findAllClients() {

		List<Cliente> clientes = clienteRepository.findAll();

		return new ResponseEntity<List<Cliente>>(clientes, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Cliente> findClientById(@PathVariable("id") Long id) {

		Optional<Cliente> cliente = clienteRepository.findById(id);

		// Caso exista o cliente
		if (cliente.isPresent()) {
			return new ResponseEntity<Cliente>(cliente.get(), HttpStatus.OK);
		}

		// caso nao encontre um cliente
		return ResponseEntity.notFound().build();
	}

	@PutMapping("/")
	public ResponseEntity<?> updateClient(@RequestBody Cliente cliente) {

		if (cliente.getId() == null) {
			return new ResponseEntity<String>("Cliente não encontrado", HttpStatus.NOT_FOUND);
		}

		Cliente client = clienteRepository.saveAndFlush(cliente);

		return new ResponseEntity<Cliente>(client, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteClient(@PathVariable("id") Long id){
		
		clienteRepository.deleteById(id);
		
		return new ResponseEntity<String>("Cliente Deletado com Sucesso", HttpStatus.OK);
	}
}
