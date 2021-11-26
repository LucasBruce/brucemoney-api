package com.example.brucemoneyapi.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.brucemoneyapi.event.RecursoCriadoEvent;
import com.example.brucemoneyapi.model.Categoria;
import com.example.brucemoneyapi.service.CategoriaService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

	/*
	 * #Status code: 
	 * #2xx -> Sucesso 
	 * #4xx -> Erro do cliente 
	 * #5xx -> Erro no serviço/servidor
	 */
	
	private ApplicationEventPublisher publisher;
	private CategoriaService categoriaService;

	@GetMapping
	public List<Categoria> findAll() {
		return this.categoriaService.listarCategorias();
	}

	@PostMapping
	public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response,
			BindingResult result) {
		Categoria categoriaSalva = categoriaService.salvarCategoria(categoria);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<Categoria> buscaPeloCodigo(@PathVariable Long codigo) {
		Categoria categoria = categoriaService.buscarCategoriaCodigo(codigo);
         return ResponseEntity.ok(categoria);
	}
}
