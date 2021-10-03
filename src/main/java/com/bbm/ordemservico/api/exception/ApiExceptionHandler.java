package com.bbm.ordemservico.api.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bbm.ordemservico.api.exception.ErrorObject.Campo;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		List<Campo> campos = new ArrayList<ErrorObject.Campo>();
		
		for (ObjectError objectError : ex.getBindingResult().getAllErrors()) {
			
			//pega o nome do campo com o problema
			String nome = ((FieldError) objectError).getField(); 
			
			//manda a mensagem especificado o problema
			String msg = objectError.getDefaultMessage();
			campos.add(new ErrorObject.Campo(nome, msg));
		}
		
		ErrorObject error = new ErrorObject();
		error.setStatus(status.value() + " ==> " + status.getReasonPhrase());
		error.setTitle("Um ou mais campos estão inválidos. Faca o devido preenchimento e tente novamente");
		error.setTime(LocalDateTime.now());
		error.setCampos(campos);
		
		return super.handleExceptionInternal(ex, error, headers, status, request);
	}
}
