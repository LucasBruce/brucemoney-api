package com.example.brucemoneyapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.brucemoneyapi.model.Categoria;
import com.example.brucemoneyapi.repository.CategoriaRepository;
import com.example.brucemoneyapi.service.exception.CategoriaInexistenteException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoriaService {

	private CategoriaRepository categoriaRepository;
	
	public List<Categoria> listarCategorias(){
		return this.categoriaRepository.findAll();
	}
	
	public Categoria salvarCategoria(Categoria categoria) {
		Categoria categoriaSalva = this.categoriaRepository.save(categoria);
		return categoriaSalva;
	}
	
	public Categoria buscarCategoriaCodigo(Long id) {
		Optional<Categoria> categoriaSalva = this.categoriaRepository.findById(id);
		if(!categoriaSalva.isPresent()) {
			throw new CategoriaInexistenteException("Categoria não foi encontrada!");
		}else {
			return categoriaSalva.get();
		}
		
	}
}
