package com.algaworks.brewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/clientes")
public class ClientesController 
{
	
	@RequestMapping("/novo")
	public String novo()
	{
		return "/cliente/CadastroCliente";
	}

}
