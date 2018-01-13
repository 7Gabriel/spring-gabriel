package br.com.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.cursomc.domain.Categoria;
import br.com.cursomc.dto.CategoriaDTO;
import br.com.cursomc.repositories.CategoriaRepository;
import br.com.cursomc.services.exceptions.DataIntegrityException;
import br.com.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;
	
	public Categoria find(Integer id){
		Categoria obj = repo.findOne(id);
		if(obj == null){
			throw new ObjectNotFoundException("Objeto não encontrado! id: " + id
					+ ", Tipo: " + Categoria.class.getName());
		}
		return obj;
	}
	
	public Categoria insert(Categoria categoria){
		categoria.setId(null);
		return repo.save(categoria);
	}
	
	public Categoria update(Categoria categoria){
		Categoria categ = find(categoria.getId());
		updataData(categ, categoria);
		return repo.save(categoria);
	}
	
	private void updataData(Categoria categ, Categoria categoria) {
		categ.setNome(categoria.getNome());
	}

	public void delete(Integer id){
		find(id);
		try{
			repo.delete(id);
		}catch(DataIntegrityViolationException e){
			throw new DataIntegrityException("Não é possivel escluir categorias com produtos");
		}
	}
	
	public List<Categoria> findAll(){
		return repo.findAll();
	}
	
	public Page<Categoria> findPage(int page, int linePage, String orderBy, String direction){
		PageRequest pageRequest = new PageRequest(page, linePage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Categoria fromDTO(CategoriaDTO dto){
		return new Categoria(dto.getId(), dto.getNome());
	}
}
