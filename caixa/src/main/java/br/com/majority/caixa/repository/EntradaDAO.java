package br.com.majority.caixa.repository;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.majority.caixa.modelo.Entrada;
import br.com.majority.caixa.modelo.Produto;

@Service
public class EntradaDAO {
	@Autowired
	private EntradaRepository repository;
	@Autowired
	private ProdutoRepository repositoryProduto;

	@Autowired
	public EntradaDAO(EntradaRepository repository, ProdutoRepository repositoryProduto) {
		this.repository = repository;
		this.repositoryProduto = repositoryProduto;
	}

	public Iterable<Entrada> cadastrarEntrada(Integer idProduto, BigDecimal quantidade) {
		Produto produto = repositoryProduto.findOne(idProduto);

		Entrada entrada = new Entrada();
		entrada.setProduto(produto);
		entrada.setQuantidade(quantidade);

		produto.setQuantidade(quantidade.add(produto.getQuantidade()));

		repositoryProduto.save(produto);
		repository.save(entrada);

		return atualizarTabela();
	}

	public Iterable<Entrada> excluirEntrada(Integer id) {
		Produto produto = new Produto();

		Entrada entrada = repository.findOne(id);
		produto = repositoryProduto.findOne(entrada.getProduto().getId());

		produto.setQuantidade(produto.getQuantidade().subtract(entrada.getQuantidade()));

		repositoryProduto.save(produto);
		repository.delete(entrada);

		return atualizarTabela();
	}

	public Iterable<Entrada> atualizarTabela() {
		Iterable<Entrada> entradas = repository.findAll();
		return entradas;
	}

}
