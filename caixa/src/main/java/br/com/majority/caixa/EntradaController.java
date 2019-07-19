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

import br.com.majority.caixa.modelo.Entrada;
import br.com.majority.caixa.modelo.Produto;
import br.com.majority.caixa.repository.EntradaRepository;
import br.com.majority.caixa.repository.ProdutoRepository;

@Controller
public class EntradaController {

	@Autowired
	private EntradaRepository repository;
	@Autowired
	private ProdutoRepository repositoryProduto;
	
	@RequestMapping(value = "cadastrarEntrada", method=RequestMethod.POST)
	public String cadastraEntrada(@RequestParam("idProduto") Integer idProduto, @RequestParam("quantidade") BigDecimal quantidade, Model model) {

		Produto produto = repositoryProduto.findOne(idProduto);

		Entrada entrada = new Entrada();
		entrada.setProduto(produto);
		entrada.setQuantidade(quantidade);

		produto.setQuantidade(quantidade.add(produto.getQuantidade()));

		repositoryProduto.save(produto);
		repository.save(entrada);

		Iterable<Entrada> entradas = repository.findAll();

		List<String> alertas = new ArrayList<String>();
		alertas.add("Cadastro Realizado com Sucesso!!");

		model.addAttribute("entradas", entradas);
		model.addAttribute("alertas", alertas);
		return "registrarEntrada";
	}

	@RequestMapping(value = "excluirEntrada/{id}")
	public String excluirProduto(@PathVariable(value = "id") Integer id, Model model) {
		
		Produto produto = new Produto();

		Entrada entrada = repository.findOne(id);
		produto = repositoryProduto.findOne(entrada.getProduto().getId());

		produto.setQuantidade(produto.getQuantidade().subtract(entrada.getQuantidade()));

		repositoryProduto.save(produto);
		repository.delete(entrada);

		Iterable<Entrada> entradas = repository.findAll();
		List<String> alertas = new ArrayList<String>();
		alertas.add("Produto Excluido com Sucesso!!");

		model.addAttribute("entradas", entradas);
		model.addAttribute("alertas", alertas);

		return "redirect:/registrarEntrada";
	}

	@RequestMapping("atualizarTabela")
	public String atualizarTabela(Model model) {
		
		Iterable<Entrada> entradas = repository.findAll();
		model.addAttribute("entradas", entradas);

		return "registrarEntrada";
	}

}
