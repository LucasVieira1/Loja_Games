package com.example.lojagames.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.lojagames.model.Produtos;
import com.example.lojagames.repository.ProdutoRepository;

@Service
public class ProdutoService {
	@Autowired
	private ProdutoRepository produtoRepository;

	public Optional<Produtos> curtir(Long id) {
		if (produtoRepository.existsById(id)) {

			Produtos produtos = produtoRepository.findById(id).get();
			produtos.setCurtir(produtos.getCurtir() + 1);
			return Optional.of(produtoRepository.save(produtos));

		}
		return Optional.empty();
	}

}
