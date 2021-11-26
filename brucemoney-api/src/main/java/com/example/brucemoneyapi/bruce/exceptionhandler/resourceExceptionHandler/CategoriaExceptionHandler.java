package com.example.brucemoneyapi.bruce.exceptionhandler.resourceExceptionHandler;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.brucemoneyapi.bruce.exceptionhandler.BrucemoneyExceptionHandler.Erro;
import com.example.brucemoneyapi.service.exception.CategoriaInexistenteException;
import com.example.brucemoneyapi.service.exception.PessoaInexistenteOuInativaException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@ControllerAdvice
public class CategoriaExceptionHandler{

	private MessageSource messageSource;
	
	@ExceptionHandler({CategoriaInexistenteException.class})
	public ResponseEntity<Object> handleCategoriaInexistenteException(PessoaInexistenteOuInativaException ex){
		String mensagemUsuario = messageSource.getMessage("categoria.inexistente", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}
}
