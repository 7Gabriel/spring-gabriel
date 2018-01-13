package br.com.cursomc.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.cursomc.controllers.utils.URL;
import br.com.cursomc.domain.Produto;
import br.com.cursomc.dto.ProdutoDTO;
import br.com.cursomc.services.ProdutoService;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Produto> find(@PathVariable Integer id){
		Produto Produto = service.find(id);
		return ResponseEntity.ok().body(Produto);
	}
	
	@GetMapping(value = "/page")
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(value ="nome", defaultValue="") String nome,
			@RequestParam(value ="categorias", defaultValue="") String categorias,
			@RequestParam(value ="page", defaultValue="0") Integer page,
		  	@RequestParam(value="linesPage", defaultValue="24") Integer linePage, 
		  	@RequestParam(value="orderBy" ,defaultValue="nome")String orderBy, 
		  	@RequestParam(value="direction", defaultValue="ASC")String direction){
		String nomeDecoded = URL.decodeParam(nome);
		List<Integer> ids = URL.decodeIntList(categorias);
		Page<Produto> clientes = service.search(nomeDecoded, ids, page, linePage, orderBy, direction);
		Page<ProdutoDTO> clientesDTO = clientes.map(cliente -> new ProdutoDTO(cliente));
		
		return ResponseEntity.ok().body(clientesDTO);
	}
}
