package com.example.brucemoneyapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.brucemoneyapi.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{

}
