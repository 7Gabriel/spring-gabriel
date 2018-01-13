package br.com.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.cursomc.controllers.exceptions.FieldMessage;
import br.com.cursomc.domain.Cliente;
import br.com.cursomc.domain.enums.TipoCliente;
import br.com.cursomc.dto.ClienteNewDTO;
import br.com.cursomc.repositories.ClienteRepository;
import br.com.cursomc.services.validation.utils.CpfCnpjValidator;

public class ClientInsertValidator implements ConstraintValidator<ClientInsert, ClienteNewDTO>  {
	
	@Autowired
	private ClienteRepository repository;
	
	@Override
	public void initialize(ClientInsert ann) {
	}
	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
	List<FieldMessage> list = new ArrayList<>();
	
	if(objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !CpfCnpjValidator.isValidCPF(objDto.getCpfOuCnpj())){
		list.add(new FieldMessage("cpfOuCnpj", "CPF Inválido"));
	}
	if(objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !CpfCnpjValidator.isValidCNPJ(objDto.getCpfOuCnpj())){
		list.add(new FieldMessage("cpfOuCnpj", "CPF Inválido"));
	}	
	
	Cliente aux = repository.findByEmail(objDto.getEmail());
	if(aux != null){
		list.add(new FieldMessage("email", "Email já cadastrado"));
	}
	
	for (FieldMessage e : list) {
		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate(e.getMessage())
			.addPropertyNode(e.getFieldName()).addConstraintViolation();
	}
	return list.isEmpty();
	}
}
