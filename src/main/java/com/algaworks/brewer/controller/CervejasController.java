package com.algaworks.brewer.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.model.Origem;
import com.algaworks.brewer.model.Sabor;
import com.algaworks.brewer.repository.Cervejas;
import com.algaworks.brewer.repository.Estilos;
import com.algaworks.brewer.repository.filter.CervejaFilter;
import com.algaworks.brewer.service.CadastroCervejaService;

@Controller
@RequestMapping("/cervejas")
public class CervejasController {

	@Autowired
	private Estilos estilos;

	@Autowired
	private CadastroCervejaService cadastroCervejaService;
	
	@Autowired
	private Cervejas cervejas;

	@GetMapping("/novo")
	public ModelAndView novo(Cerveja cerveja) {
		ModelAndView view = new ModelAndView("cerveja/CadastroCerveja");
		view.addObject("sabores", Sabor.values());
		view.addObject("estilos", estilos.findAll());
		view.addObject("origens", Origem.values());
		return view;
	}

	@PostMapping("/novo")
	public ModelAndView cadastar(@Valid Cerveja cerveja, BindingResult result, Model model,
			RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(cerveja);
		}

		cadastroCervejaService.salvar(cerveja);
		attributes.addFlashAttribute("mensagem", "Cerveja salva consucesso.");
		return new ModelAndView("redirect:/cervejas/novo");
	}

	@GetMapping
	public ModelAndView pesquisar(CervejaFilter cervejaFilter, BindingResult result, @PageableDefault(size = 2) Pageable pageable) {
		Page<Cerveja> pagina = cervejas.filtrar(cervejaFilter, pageable);
		
		return new ModelAndView("cerveja/PesquisaCervejas")
				.addObject("pagina", pagina)				
				.addObject("origens", Origem.values())
				.addObject("estilos", estilos.findAll())
				.addObject("sabores", Sabor.values());
	}

}
