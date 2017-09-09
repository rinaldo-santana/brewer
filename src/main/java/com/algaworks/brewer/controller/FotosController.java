package com.algaworks.brewer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.brewer.dto.FotoDTO;
import com.algaworks.brewer.storage.FotoStorage;
import com.algaworks.brewer.storage.FotoStorageRunnable;

@RequestMapping("/fotos")
@RestController
public class FotosController {
	
	@Autowired
	FotoStorage fotoStorage;
	
	@RequestMapping(method = RequestMethod.POST)
	public DeferredResult<FotoDTO> upload(@RequestParam("files[]") MultipartFile[] files) {
		
		DeferredResult<FotoDTO> resultado = new DeferredResult<>();
		
		System.out.println("files size: " + files[0].getSize());
		
		Thread thread = new Thread(new FotoStorageRunnable(resultado, files, fotoStorage));
		thread.start();
	
		return resultado;
	}
	
	@GetMapping("/temp/{nome:.*}")
	public byte[] recuperarFotosTemporaria(@PathVariable String nome ){
		return fotoStorage.recuperarFotoTemporaria(nome);
	}

	@GetMapping("{nome:.*}")
	public byte[] recuperarFotos(@PathVariable String nome ){
		return fotoStorage.recuperarFoto(nome);
	}
}
