package br.com.majority.caixa;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.majority.caixa.modelo.Produto;
import br.com.majority.caixa.modelo.Venda;
import br.com.majority.caixa.repository.ProdutoRepository;
import br.com.majority.caixa.repository.VendaRepository;

@Controller
public class VendaController {
	@Autowired
	private VendaRepository repository;
	@Autowired
	private ProdutoRepository repositoryProduto;
	
	@RequestMapping(value = "cadastrarVenda", method=RequestMethod.POST)
	public String cadastraVenda(@RequestParam("idProduto") Integer idProduto, @RequestParam("quantidade") BigDecimal quantidade, Model model) {

		List<String> erros = new ArrayList<String>();
		
		Produto produto = repositoryProduto.findOne(idProduto);

		Date data = new Date(System.currentTimeMillis());
		
		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		
		Venda venda = new Venda();
		venda.setProduto(produto);
		venda.setData(formatador.format(data));
		venda.setValor(quantidade.multiply(produto.getPreco()));
		venda.setQuantidade(quantidade);
		
		if(quantidade.compareTo(produto.getQuantidade()) == 1) {
			erros.add("Quantidade Vendida é Maior do que a Quantidade Presente no Estoque!!!");
			model.addAttribute("erros", erros);
			
			return "venda";
		}
		
		produto.setQuantidade(produto.getQuantidade().subtract(quantidade));
		
		repositoryProduto.save(produto);
		repository.save(venda);

		Iterable<Venda> vendas = repository.findAll();

		List<String> alertas = new ArrayList<String>();
		alertas.add("Cadastro Realizado com Sucesso!!");

		model.addAttribute("vendas", vendas);
		model.addAttribute("alertas", alertas);
		return "venda";
	}

	@RequestMapping(value = "excluirVenda/{id}")
	public String excluirVenda(@PathVariable(value = "id") Integer id, Model model) {
		
		Produto produto = new Produto();

		Venda venda = repository.findOne(id);
		produto = repositoryProduto.findOne(venda.getProduto().getId());

		produto.setQuantidade(produto.getQuantidade().add(venda.getQuantidade()));

		repositoryProduto.save(produto);
		repository.delete(venda);

		Iterable<Venda> vendas = repository.findAll();
		List<String> alertas = new ArrayList<String>();
		alertas.add("Venda Excluida com Sucesso!!");

		model.addAttribute("vendas", vendas);
		model.addAttribute("alertas", alertas);

		return "redirect:/venda";
	}

	@RequestMapping("atualizarTabelaVenda")
	public String atualizarTabela(Model model) {
		
		Iterable<Venda> vendas = repository.findAll();
		model.addAttribute("vendas", vendas);

		return "registrarEntrada";
	}

}
