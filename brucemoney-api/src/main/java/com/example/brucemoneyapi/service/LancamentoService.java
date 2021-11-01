package com.example.brucemoneyapi.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.brucemoneyapi.model.Lancamento;
import com.example.brucemoneyapi.model.Pessoa;
import com.example.brucemoneyapi.repository.LancamentoRepository;
import com.example.brucemoneyapi.repository.PessoaRepository;
import com.example.brucemoneyapi.service.exception.LancamentoNaoEncontradoException;
import com.example.brucemoneyapi.service.exception.PessoaInexistenteOuInativaException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class LancamentoService {

private LancamentoRepository lancamentoRepository;
private PessoaService pessoaService;
private PessoaRepository pessoaRepository;

    public Lancamento salvar(Lancamento lancamento) {
    	Pessoa pessoa = this.pessoaService.buscarPessoaPeloCodigo(lancamento.getPessoa().getCodigo());
    	if(pessoa == null || pessoa.isInativo()) {
    		throw new PessoaInexistenteOuInativaException("");
    	}
    	return this.lancamentoRepository.save(lancamento);
    }

	public Lancamento buscarLancamento(Long codigo) {
		return this.lancamentoRepository.findById(codigo).orElseThrow(() -> new LancamentoNaoEncontradoException("Lançamento não encontrado"));
		
	}
}
