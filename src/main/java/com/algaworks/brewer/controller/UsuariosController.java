package com.algaworks.brewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {
	
	
	@RequestMapping("/novo")
	public ModelAndView novo(){
		return new ModelAndView("/usuario/CadastroUsuario");
	}

}
