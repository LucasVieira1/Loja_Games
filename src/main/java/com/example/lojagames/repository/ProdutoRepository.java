package com.example.lojagames.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.lojagames.model.Produtos;

@Repository
public interface ProdutoRepository extends JpaRepository<Produtos, Long> {
	public List<Produtos> findAllByNomeContainingIgnoreCase(String nome);

	public List<Produtos> findByPrecoGreaterThanOrderByPreco(double preco);

	public List<Produtos> findByPrecoLessThanOrderByPrecoDesc(double preco);
	
}

