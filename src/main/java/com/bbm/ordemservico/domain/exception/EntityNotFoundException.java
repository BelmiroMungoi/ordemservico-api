package com.bbm.ordemservico.domain.exception;

public class EntityNotFoundException extends NegocioException{
	
	private static final long serialVersionUID = 1L;

	public EntityNotFoundException(String msg) {
		super(msg);
	}


}
