package com.example.brucemoneyapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.brucemoneyapi.model.Lancamento;
import com.example.brucemoneyapi.model.Pessoa;
import com.example.brucemoneyapi.repository.LancamentoRepository;
import com.example.brucemoneyapi.repository.filter.LancamentoFilter;
import com.example.brucemoneyapi.service.exception.LancamentoNaoEncontradoException;
import com.example.brucemoneyapi.service.exception.PessoaInexistenteOuInativaException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class LancamentoService {

	private LancamentoRepository lancamentoRepository;
	private PessoaService pessoaService;
	private CategoriaService categoriaService;

	public Lancamento salvar(Lancamento lancamento) {
		Pessoa pessoa = this.pessoaService.buscarPessoaPeloCodigo(lancamento.getPessoa().getCodigo());
		this.categoriaService.buscarCategoriaCodigo(lancamento.getCategoria().getCodigo());

		if (pessoa == null || pessoa.isInativo()) {
			throw new PessoaInexistenteOuInativaException("Pessoa Não foi encontrada!");
		}
		return this.lancamentoRepository.save(lancamento);
	}

	public Lancamento buscarLancamento(Long codigo) {
		Optional<Lancamento> lancamento = this.lancamentoRepository.findById(codigo);
		if (!lancamento.isPresent()) {
			throw new LancamentoNaoEncontradoException("Lançamento não encontrado ou não existe!");
		} else {
			return lancamento.get();
		}

	}
	
	public void removerLancamento(Long codigo) {
		this.lancamentoRepository.deleteById(codigo);
	}
	
	public Page<Lancamento> pesquisar(LancamentoFilter lancamentoFilter, Pageable pageable){
		return this.lancamentoRepository.filtrar(lancamentoFilter, pageable);
	}
}
