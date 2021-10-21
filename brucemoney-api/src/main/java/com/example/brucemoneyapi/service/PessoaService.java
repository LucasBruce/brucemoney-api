package com.example.brucemoneyapi.service;

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

	public Pessoa atualizar(Long codigo, Pessoa pessoa) {
		Optional<Pessoa> pessoaSalva = this.pessoaRepository.findById(codigo);
		if (pessoaSalva.get() == null) {
			throw new EmptyResultDataAccessException(1);
		}

		BeanUtils.copyProperties(pessoa, pessoaSalva.get(), "codigo");
		return this.pessoaRepository.save(pessoaSalva.get());
	}
}
