package com.example.brucemoneyapi.resource;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.brucemoneyapi.bruce.exceptionhandler.BrucemoneyExceptionHandler.Erro;
import com.example.brucemoneyapi.event.RecursoCriadoEvent;
import com.example.brucemoneyapi.model.Lancamento;
import com.example.brucemoneyapi.repository.LancamentoRepository;
import com.example.brucemoneyapi.service.LancamentoService;
import com.example.brucemoneyapi.service.exception.PessoaInexistenteOuInativaException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

	private LancamentoRepository lancamentoRepository;
	private LancamentoService lancamentoService;
	private ApplicationEventPublisher publisher;
    private MessageSource messageSource;

	@GetMapping
	public List<Lancamento> listar() {
		return this.lancamentoRepository.findAll();
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<Lancamento> buscarLancamento(@PathVariable Long codigo) {
		Lancamento lancamento = this.lancamentoService.buscarLancamento(codigo);
		if (lancamento == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(lancamento);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Lancamento> criarLancamento(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response){
		Lancamento lancament = this.lancamentoService.salvar(lancamento);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, lancament.getCodigo()));
		return ResponseEntity.ok(lancament);
	}
	
	@ExceptionHandler({PessoaInexistenteOuInativaException.class})
	public ResponseEntity<Object> handlePessoaInexistenteOuInativaException(PessoaInexistenteOuInativaException ex){
		String mensagemUsuario = messageSource.getMessage("pessoa.inexistente-ou-inativa", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}
}
