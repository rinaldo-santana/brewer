package com.algaworks.brewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cidades")
public class CidadesController {
	
	@RequestMapping("/novo")
	public String novo(){
		return "/cidade/CadastroCidade";
	}

}
