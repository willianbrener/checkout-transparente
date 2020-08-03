package br.com.sga.core.validator.business;

import org.springframework.stereotype.Component;

import br.com.sga.core.exception.BusinessValidationException;
import br.com.sga.core.model.ExampleMask;

@Component
public class ExampleMaskBusinessValidator {
	
	public void validaSePermiteExcluirChildren(ExampleMask exampleMask) throws BusinessValidationException {
		
		if(exampleMask.getListExampleMaskChildren().size() == 1){
			
			throw new BusinessValidationException("exampleMaskChildrenInputDTO.inputExampleMaskChildren.id" , "business.exampleMask.exclusao.nao.permitida");
		}
		
	}
}
