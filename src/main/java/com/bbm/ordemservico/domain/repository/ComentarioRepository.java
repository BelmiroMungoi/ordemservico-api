package com.bbm.ordemservico.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bbm.ordemservico.domain.model.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario, Long>{

}
