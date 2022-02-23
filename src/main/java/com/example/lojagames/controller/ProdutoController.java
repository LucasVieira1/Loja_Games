package com.example.lojagames.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.lojagames.model.Produtos;
import com.example.lojagames.repository.CategoriaRepository;
import com.example.lojagames.repository.ProdutoRepository;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoController {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired // cria a injeção de dependencia
	private CategoriaRepository categoriaRepository;

	@GetMapping
	public ResponseEntity<List<Produtos>> getAll() {
		return ResponseEntity.ok(produtoRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Produtos> getById(@PathVariable Long id) {
		return produtoRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Produtos>> getByNome(@PathVariable String nome) {
		return ResponseEntity.ok(produtoRepository.findAllByNomeContainingIgnoreCase(nome));
	}

	@PostMapping
	public ResponseEntity<Produtos> postProduto(@Valid @RequestBody Produtos produtos) {
		if (produtoRepository.existsById(produtos.getCategoria().getId())) {
			return ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(produtos));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping
	public ResponseEntity<Produtos> putPostagem(@Valid @RequestBody Produtos produtos) {
		if (categoriaRepository.existsById(produtos.getCategoria().getId())) {
			return produtoRepository.findById(produtos.getId())
					.map(resposta -> ResponseEntity.ok().body(produtoRepository.save(produtos)))
					.orElse(ResponseEntity.notFound().build());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProduto(@PathVariable Long id) {
		return produtoRepository.findById(id).map(resposta -> {
			produtoRepository.deleteById(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}).orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/maiorpreco/{preco}")
	public ResponseEntity<List<Produtos>> getPrecoMaiorQue(@PathVariable double preco) {
		return ResponseEntity.ok(produtoRepository.findByPrecoGreaderThanOrderByPreco(preco));
	}
	@GetMapping("/menorpreco/{preco}")
	public ResponseEntity<List<Produtos>> getPrecoMenorQue(@PathVariable double preco) {
		return ResponseEntity.ok(produtoRepository.findByPrecoLessThanOrderByPrecoDesc(preco));
	}
}
