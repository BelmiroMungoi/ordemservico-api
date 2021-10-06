package com.bbm.ordemservico.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bbm.ordemservico.api.model.ComentarioInput;
import com.bbm.ordemservico.api.model.OrdemServicoInput;
import com.bbm.ordemservico.api.model.dtos.ComentarioDto;
import com.bbm.ordemservico.api.model.dtos.OrdemServicoDto;
import com.bbm.ordemservico.domain.exception.EntityNotFoundException;
import com.bbm.ordemservico.domain.model.Comentario;
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

	@Autowired
	private ModelMapper modelMapper;

	@PostMapping("/")
	public ResponseEntity<OrdemServicoDto> create(@Valid @RequestBody OrdemServicoInput ordemServicoInput) {

		OrdemServico ordemServico = toEntity(ordemServicoInput);

		OrdemServico order = gestaoOrdemServico.createOrder(ordemServico);

		return new ResponseEntity<OrdemServicoDto>(toModel(order), HttpStatus.CREATED);
	}

	@GetMapping("/")
	public ResponseEntity<List<OrdemServicoDto>> findAllOrder() {

		List<OrdemServico> ordens = ordemServicoRepository.findAll();

		return new ResponseEntity<List<OrdemServicoDto>>(toCollectionModel(ordens), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findOrderById(@PathVariable("id") Long id) {

		Optional<OrdemServico> ordemServico = ordemServicoRepository.findById(id);

		if (ordemServico.isPresent()) {
			return new ResponseEntity<OrdemServicoDto>(toModel(ordemServico.get()), HttpStatus.OK);
		}

		return new ResponseEntity<String>("Ordem de Servico não encontrada", HttpStatus.NOT_FOUND);
	}

	@PostMapping("/{id}/comentario")
	public ResponseEntity<ComentarioDto> addComentario(@PathVariable Long id,
			@Valid @RequestBody ComentarioInput input) {

		Comentario comentario = gestaoOrdemServico.addComentario(id, input.getDescricao());
		
		return new ResponseEntity<ComentarioDto>(toComent(comentario), HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}/comentario")
	public ResponseEntity<List<ComentarioDto>> listarComentario(@PathVariable("id") Long id) {
		
		OrdemServico ordemServico = ordemServicoRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Ordem de Servico não encontrada"));

		return new ResponseEntity<List<ComentarioDto>>(toCollectionComents(
				ordemServico.getComentarios()), HttpStatus.OK);
	}
	
	@PutMapping("/{id}/finalizacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void finalizar(@PathVariable("id") Long id) {
		gestaoOrdemServico.finalizarOrdemServico(id);
	}

	// transforma a entidade num representation model/dto
	private OrdemServicoDto toModel(OrdemServico ordemServico) {
		return modelMapper.map(ordemServico, OrdemServicoDto.class);
	}

	private List<OrdemServicoDto> toCollectionModel(List<OrdemServico> ordensServico) {
		return ordensServico.stream().map(ordemServico -> toModel(ordemServico)).collect(Collectors.toList());
	}

	private OrdemServico toEntity(OrdemServicoInput ordemServicoInput) {
		return modelMapper.map(ordemServicoInput, OrdemServico.class);
	}

	private ComentarioDto toComent(Comentario comentario) {
		return modelMapper.map(comentario, ComentarioDto.class);
	}
	
	private List<ComentarioDto> toCollectionComents(List<Comentario> comentarios) {
		return comentarios.stream().map(comentario -> toComent(comentario)).collect(Collectors.toList());
	}
}
