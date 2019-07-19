package br.com.majority.caixa.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.majority.caixa.modelo.Produto;

public interface ProdutoRepository extends CrudRepository<Produto, Integer>{

}
