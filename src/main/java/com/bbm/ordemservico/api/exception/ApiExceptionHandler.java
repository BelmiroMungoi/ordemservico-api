package com.bbm.ordemservico.api.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		ErrorObject error = new ErrorObject();
		error.setStatus(status.value() + " ==> " + status.getReasonPhrase());
		error.setTitle("Um ou mais campos estão inválidos. Faca o devido preenchimento e tente novamente");
		error.setTime(LocalDateTime.now());
		
		return super.handleExceptionInternal(ex, error, headers, status, request);
	}
}
