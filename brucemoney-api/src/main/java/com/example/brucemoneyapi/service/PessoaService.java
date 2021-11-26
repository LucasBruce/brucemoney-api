package com.example.brucemoneyapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.brucemoneyapi.model.Pessoa;
import com.example.brucemoneyapi.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;

	public List<Pessoa> listarPessoas(){
		return this.pessoaRepository.findAll();
	}
	
	public Pessoa salvarPessoa(Pessoa pessoa) {
		return this.pessoaRepository.save(pessoa);
	}
	
	public Pessoa atualizar(Long codigo, Pessoa pessoa) {
		Pessoa pessoaSalva = this.buscarPessoaPeloCodigo(codigo);
		BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");
		this.pessoaRepository.save(pessoaSalva);
		return this.buscarPessoaPeloCodigo(codigo);
		 	
	}

	public void atualzarPropriedadeAtivo(Long codigo, boolean ativo) {
		Pessoa pessoaSalva = this.buscarPessoaPeloCodigo(codigo);
		pessoaSalva.setAtivo(ativo);
		this.pessoaRepository.save(pessoaSalva);
	}

	public Pessoa buscarPessoaPeloCodigo(Long codigo) {
		Optional<Pessoa> pessoaBuscada = this.pessoaRepository.findById(codigo);
		if(!pessoaBuscada.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		return pessoaBuscada.get();
		
	}
	
	public void removerPessoa(Long codigo) {
		this.pessoaRepository.deleteById(codigo);
	}

}
