package com.example.brucemoneyapi.service.exception;

public class LancamentoNaoEncontradoException extends PessoaInexistenteOuInativaException {

	
	private static final long serialVersionUID = 1L;
	
	public LancamentoNaoEncontradoException(String message) {
		super(message);
	}

}
