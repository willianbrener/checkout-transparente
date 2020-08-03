package br.com.sga.core.validator.business;

import org.springframework.stereotype.Component;

import br.com.sga.core.exception.BusinessValidationException;
import br.com.sga.core.model.Region;

@Component
public class RegionBusinessValidator {
	
	public void validaNomePermitidos(Region region) throws BusinessValidationException {
		
		if(!region.getName().toLowerCase().startsWith("region")){
			
			throw new BusinessValidationException("name" , "business.region.name.nao.permitido");
		}
		
	}
}
