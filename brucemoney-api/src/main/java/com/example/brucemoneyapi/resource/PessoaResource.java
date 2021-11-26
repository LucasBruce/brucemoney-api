package com.example.brucemoneyapi.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.context.ApplicationEventPublisher;
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

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

	private PessoaRepository pessoaRepository;
	private ApplicationEventPublisher publisher;
	private PessoaService pessoaService;

	@GetMapping
	public List<Pessoa> listarPessoas() {
		return this.pessoaService.listarPessoas();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response,
			BindingResult result) {
		Pessoa pessoaSalvar = this.pessoaService.salvarPessoa(pessoa);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalvar.getCodigo()));
		return ResponseEntity.ok(pessoaSalvar);
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<Pessoa> buscarPessoa(@PathVariable Long codigo) {
		Pessoa pessoaAchada = this.pessoaService.buscarPessoaPeloCodigo(codigo);
		return ResponseEntity.ok(pessoaAchada);		
	}

	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		this.pessoaService.removerPessoa(codigo);
	}

	@PutMapping("/{codigo}")
	public ResponseEntity<Pessoa> atualizar(@PathVariable Long codigo, @Valid @RequestBody Pessoa pessoa) {
		Pessoa pessoaSalva = this.pessoaService.atualizar(codigo, pessoa);
		return pessoaSalva != null ? ResponseEntity.ok(pessoaSalva) : ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{codigo}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarPropriedadeAtivo(@PathVariable Long codigo,@RequestBody boolean ativo) {
		this.pessoaService.atualzarPropriedadeAtivo(codigo, ativo);
	}

}
