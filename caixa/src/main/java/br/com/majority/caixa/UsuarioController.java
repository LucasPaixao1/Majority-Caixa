package br.com.majority.caixa;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.majority.caixa.modelo.Usuario;
import br.com.majority.caixa.repository.UsuarioRepository;

@Controller
public class UsuarioController {

	@Autowired
	private UsuarioRepository repository;

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping("cadastroDeUsuario")
	public String cadastroDeUsuario() {
		return "cadastroDeUsuario";
	}

	@RequestMapping("menu")
	public String menu() {
		return "menu";
	}
	
	@RequestMapping(value = "cadastrar", method = RequestMethod.POST)
	public String cadastrarUsuario(@RequestParam("login") String login, @RequestParam("senha") String senha, @RequestParam("email") String email,
			@RequestParam("confirmarSenha") String confirmarSenha, Model model) {

		Usuario usuario = new Usuario();
		usuario.setLogin(login);
		usuario.setSenha(senha);
		usuario.setEmail(email);

		if (usuario.getSenha().equals(confirmarSenha)) {

			repository.save(usuario);

			return "redirect:/";
		} else {

			List<String> erro = new ArrayList<String>();
			erro.add("Cadastro não realizado. Motivo: As senhas não batem");

			model.addAttribute("erros", erro);
			return "cadastroDeUsuario";
		}

	}

	@RequestMapping(value = "entrar", method = RequestMethod.POST)
	public String entrar(@RequestParam("login") String login, @RequestParam("senha") String senha, Model model) {

		boolean logou = false;

		String pagina = "";
		Usuario usuario = new Usuario();
		usuario.setLogin(login);
		usuario.setSenha(senha);

		Iterable<Usuario> usuarios = repository.findAll();

		for (Usuario usuario2 : usuarios) {
			if (usuario.getLogin().equals(usuario2.getLogin()) && usuario.getSenha().equals(usuario2.getSenha())) {
				logou = true;
			}
		}
		
		if (logou) {

			pagina = "redirect:menu";
		}else {
			List<String> erro = new ArrayList<String>();
			erro.add("Login ou senha incorreto.");
			model.addAttribute("erros", erro);
			pagina = "index";
		}
		return pagina;
		
	}
}
