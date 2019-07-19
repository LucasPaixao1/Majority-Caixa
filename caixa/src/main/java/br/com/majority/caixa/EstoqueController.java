package br.com.majority.caixa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.majority.caixa.modelo.Entrada;
import br.com.majority.caixa.modelo.Produto;
import br.com.majority.caixa.repository.ProdutoRepository;

@Controller
public class EstoqueController {
	
	@Autowired
	private ProdutoRepository  repository;
	
	@RequestMapping("atualizarTabelaEstoque")
	public String atualizarTabela(Model model) {
		
		Iterable<Produto> produtos = repository.findAll();
		model.addAttribute("produtos", produtos);

		return "estoque";
	}

}
