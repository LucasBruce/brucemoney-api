package com.example.brucemoneyapi.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.brucemoneyapi.model.Pessoa;
import com.example.brucemoneyapi.repository.PessoaRepository;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

	@Autowired
	private PessoaRepository pessoaRepository;

	@GetMapping
	public List<Pessoa> lista() {
		List<Pessoa> pessoas = this.pessoaRepository.findAll();
		return pessoas;
	}

	@PostMapping
	public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response,
			BindingResult result) {
		Pessoa pessoaSalvar = this.pessoaRepository.save(pessoa);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/codigo")
				.buildAndExpand(pessoaSalvar.getCodigo()).toUri();
		response.setHeader("Location", uri.toASCIIString());
		return ResponseEntity.created(uri).body(pessoaSalvar);
	}

	@GetMapping("/{codigo}")
	public Optional<Pessoa> buscarPessoa(@PathVariable Long codigo) {
		Optional<Pessoa> pessoa = this.pessoaRepository.findById(codigo);
		return pessoa;
	}

}