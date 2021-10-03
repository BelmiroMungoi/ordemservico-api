package com.bbm.ordemservico.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbm.ordemservico.domain.exceptionhandler.NegocioException;
import com.bbm.ordemservico.domain.model.Cliente;
import com.bbm.ordemservico.domain.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	public Cliente saveClient(Cliente cliente) {
		Cliente clienteExists = clienteRepository.findByEmail(cliente.getEmail());

		if (clienteExists != null && !clienteExists.equals(cliente)) {
			throw new NegocioException("Já existe um cliente com esse email");
		}
		return clienteRepository.save(cliente);
	}

	public void deleteClient(Long id) {
		clienteRepository.deleteById(id);
	}
}
