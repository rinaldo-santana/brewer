package com.algaworks.brewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.algaworks.brewer.model.Cerveja;

@Controller
public class CervejasController {
	
	@RequestMapping("/cervejas/novo")
	public String novo() {
		System.out.println("--->>> novo() ok...");
		return "/cerveja/CadastroCerveja";
	}
	
	@RequestMapping(value = "/cervejas/novo", method = RequestMethod.POST)
	public String cadastar(Cerveja cerveja){
		System.out.println(">>>>> cadastrar() ok...");
		System.out.println(">>>>> sky: " + cerveja.getSku());
		return "redirect:/cervejas/novo";
	}

	
}

