package com.example.brucemoneyapi.service.exception;

public class CategoriaInexistenteException extends PessoaInexistenteOuInativaException{

	private static final long serialVersionUID = 1L;
	
	public CategoriaInexistenteException(String message) {
		super(message);
	}

	

}
