package com.example.brucemoneyapi.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.brucemoneyapi.event.RecursoCriadoEvent;
import com.example.brucemoneyapi.model.Pessoa;
import com.example.brucemoneyapi.repository.PessoaRepository;
import com.example.brucemoneyapi.service.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private PessoaService pessoaService;
	
	@GetMapping
	public List<Pessoa> lista() {
		List<Pessoa> pessoas = this.pessoaRepository.findAll();
		return pessoas;
	}

	@PostMapping
	public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response,
			BindingResult result) {
		Pessoa pessoaSalvar = this.pessoaRepository.save(pessoa);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalvar.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalvar);
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<Pessoa> buscarPessoa(@PathVariable Long codigo) {
		Optional<Pessoa> pessoa = this.pessoaRepository.findById(codigo);
		return pessoa.isPresent() ? ResponseEntity.ok(pessoa.get()) : ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		this.pessoaRepository.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Pessoa> atualizar(@PathVariable Long codigo, @Valid @RequestBody Pessoa pessoa){
		if(!(this.pessoaRepository.existsById(codigo))) {
			return ResponseEntity.notFound().build();
		}
		pessoa.setCodigo(codigo);
		pessoa = this.pessoaRepository.save(pessoa);
		return ResponseEntity.ok(pessoa);
	}

}
