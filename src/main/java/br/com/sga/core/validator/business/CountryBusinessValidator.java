package br.com.sga.core.validator.business;

import org.springframework.stereotype.Component;

import br.com.sga.core.exception.BusinessValidationException;
import br.com.sga.core.model.Country;

@Component
public class CountryBusinessValidator {
	
	public void validaNomePermitidos(Country country) throws BusinessValidationException {
		
		if(!country.getName().toLowerCase().startsWith("country")){
			
			throw new BusinessValidationException("name" , "business.country.name.nao.permitido");
		}
		
	}
}
