package com.algaworks.brewer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;


import com.algaworks.brewer.service.CadastroCervejaService;
import com.algaworks.brewer.storage.FotoStorage;
import com.algaworks.brewer.storage.local.FotoStorageLocal;

@Component
@ComponentScan(basePackageClasses = CadastroCervejaService.class)
public class ServiceConfig 
{

	@Bean
	public FotoStorage fotoStorage() {
		return new FotoStorageLocal();
		
	}

}
