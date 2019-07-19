package br.com.majority.caixa.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.majority.caixa.modelo.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Integer>{

}
