package br.com.majority.caixa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.majority.caixa.modelo.Entrada;
import br.com.majority.caixa.modelo.Produto;
import br.com.majority.caixa.modelo.Venda;
import br.com.majority.caixa.repository.EntradaRepository;
import br.com.majority.caixa.repository.ProdutoRepository;
import br.com.majority.caixa.repository.VendaRepository;

@Controller
public class MenuController {
	
	@Autowired
	private ProdutoRepository  repository;
	
	@Autowired
	private EntradaRepository  repositoryEntrada;
	
	@Autowired
	private VendaRepository  repositoryVenda;
	
	@RequestMapping("cadastroProdutos")
	public String cadastroProdutos(Model model) {
		Iterable<Produto> produtos = repository.findAll();
		model.addAttribute("produtos", produtos);
		
		return "cadastroProdutos";
	}

	@RequestMapping("registrarEntrada")
	public String registrarEntrada(Model model) {
		Iterable<Entrada> entradas = repositoryEntrada.findAll();
		model.addAttribute("entradas", entradas);
		
		return "registrarEntrada";
	}
	
	@RequestMapping("venda")
	public String venda(Model model) {
		Iterable<Venda> vendas = repositoryVenda.findAll();
		model.addAttribute("vendas", vendas);
		
		return "venda";
	}
	
	@RequestMapping("estoque")
	public String estoque(Model model) {
		Iterable<Produto> produtos = repository.findAll();
		model.addAttribute("produtos", produtos);
		
		return "estoque";
	}
}
