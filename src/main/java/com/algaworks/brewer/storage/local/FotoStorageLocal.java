package com.algaworks.brewer.storage.local;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.brewer.storage.FotoStorage;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

public class FotoStorageLocal implements FotoStorage {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FotoStorageLocal.class);

	private Path local;
	private Path localTemporario;
	
	
	public FotoStorageLocal() {
		this(FileSystems.getDefault().getPath(System.getenv("HOME"), ".brewerfotos"));
		
	}

	public FotoStorageLocal(Path path){
		this.local = path;
		criarPastas();
	}


	@Override
	public String salvarTemporariamente(MultipartFile[] files) {
		String novoNome = null;
		
		if (files != null && files.length > 0) {
			MultipartFile arquivo = files[0];
			novoNome = renomearArquivo(arquivo.getOriginalFilename());			

			try {
				arquivo.transferTo(new File(this.localTemporario.toAbsolutePath().toString() 
											+ FileSystems.getDefault().getSeparator() 
											+ novoNome));
			} catch (IOException e) {
				throw new RuntimeException("Erro salvando foto na pasta temporaria", e);
			}			
		}
		
		return novoNome;		
	}
	
	
	@Override
	public byte[] recuperarFotoTemporaria(String nome) {
		try {
			return Files.readAllBytes(this.localTemporario.resolve(nome));
		} catch (IOException e) {
			throw new RuntimeException("Erro ao ler a foto temporaria ", e);
		}
	}
	
	@Override
	public byte[] recuperarFoto(String nome) {
		try {
			return Files.readAllBytes(this.local.resolve(nome));
		} catch (IOException e) {
			throw new RuntimeException("Erro ao ler a foto", e);
		}
	}
	
	
	@Override
	public void salvar(String foto) {
		try {
			Files.move(this.localTemporario.resolve(foto), this.local.resolve(foto));
		} catch (IOException e) {
			throw new RuntimeException("Erro ao nover a foto para o diretorio local", e);
		}
		
		try {
			Thumbnails.of(this.local.resolve(foto).toString()).size(40, 68).toFiles(Rename.PREFIX_DOT_THUMBNAIL);
		} catch (IOException e) {
			throw new RuntimeException("Erro gerando thumbnail", e);
		}
	}
	
	
	
	private void criarPastas() {
		try {
			Files.createDirectories(this.local);
			this.localTemporario = FileSystems.getDefault().getPath(this.local.toString(), "temp");
			Files.createDirectories(this.localTemporario);
			
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Pastas criadas para salvar fotos");
				LOGGER.debug("Pasta default: " + this.local.toAbsolutePath());
				LOGGER.debug("Pasta temporaria: " + this.localTemporario.toAbsolutePath());
			}
			
		} catch (IOException e) {
			throw new RuntimeException("Erro criando pasta para salvar foto", e);
		}
	}
	
	private String renomearArquivo(String nomeOriginal){
		String novoNome = UUID.randomUUID().toString() + "_" + nomeOriginal;
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(String.format("Nome original: %s, novo nome: %s", nomeOriginal, novoNome ));
		}
		
		return novoNome;		
	}	
	
}
