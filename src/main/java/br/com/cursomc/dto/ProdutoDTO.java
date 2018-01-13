package br.com.cursomc.dto;

import java.io.Serializable;

import br.com.cursomc.domain.Produto;

public class ProdutoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome;
	private Double preco;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ProdutoDTO(Produto prod){
		id = prod.getId();
		nome = prod.getNome();
		preco = prod.getPreco();
	}
	
	public ProdutoDTO(){
		
	}

	public String getNome() {
		return nome;
	}

	public Double getPreco() {
		return preco;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}
	
	
}
