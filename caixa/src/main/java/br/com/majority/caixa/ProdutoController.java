package br.com.majority.caixa;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.majority.caixa.modelo.Produto;
import br.com.majority.caixa.repository.ProdutoRepository;

@Controller
public class ProdutoController {

	@Autowired
	private ProdutoRepository repository;
	
	@RequestMapping(value = "cadastrarProduto")
	public Model cadastraProdutos(@RequestParam("nomeProduto") String nome, @RequestParam("marca") String marca,
			@RequestParam("categoria") String categoria, @RequestParam("preco") BigDecimal preco, Model model) {

		Produto produto = new Produto();

		
		BigDecimal quantidade = new BigDecimal(0);
		produto.setNome(nome);
		produto.setMarca(marca);
		produto.setCategoria(categoria);
		produto.setPreco(preco);
		produto.setQuantidade(quantidade);

		repository.save(produto);

		Iterable<Produto> produtos = repository.findAll();

		List<String> alertas = new ArrayList<String>();
		alertas.add("Cadastro Realizado com Sucesso!!");

		model.addAttribute("produtos", produtos);
		model.addAttribute("alertas", alertas);
		return model;
	}

	@RequestMapping(value = "excluirProduto/{id}")
	public String excluirProduto(@PathVariable(value = "id") Integer id, Model model) {
		
		repository.delete(id);

		Iterable<Produto> produtos = repository.findAll();

		List<String> alertas = new ArrayList<String>();
		alertas.add("Produto Excluido com Sucesso!!");

		model.addAttribute("produtos", produtos);
		model.addAttribute("alertas", alertas);
		return "redirect:/cadastroProdutos";
	}
	
	@RequestMapping(value="verOqueFazer", method=RequestMethod.POST)
	public String verOqueFazer(@RequestParam("nomeProduto") String nome, @RequestParam("marca") String marca,
		@RequestParam("categoria") String categoria, @RequestParam("preco") BigDecimal preco, @RequestParam("id") Integer id, Model model) {
				
		if(id != null) {
			alterarProduto(nome, marca, categoria, preco, id, model);
			return "cadastroProdutos";
		}else {
			model = cadastraProdutos(nome, marca, categoria, preco, model);
			return "cadastroProdutos";
		}
	}
	
	@RequestMapping(value = "alterarProduto")
	public Model alterarProduto(@RequestParam("nomeProduto") String nome, @RequestParam("marca") String marca, @RequestParam("categoria") String categoria, @RequestParam("preco") BigDecimal preco, @RequestParam("id") Integer id, Model model) {
		Produto produto = new Produto();
		
		produto.setId(id);
		produto.setNome(nome);
		produto.setMarca(marca);
		produto.setCategoria(categoria);
		produto.setPreco(preco);

		repository.save(produto);
		Iterable<Produto> produtos = repository.findAll();

		List<String> alertas = new ArrayList<String>();
		alertas.add("Produto Alterado com Sucesso!!");

		model.addAttribute("produtos", produtos);
		model.addAttribute("alertas", alertas);
		return model;
	}
	
}
