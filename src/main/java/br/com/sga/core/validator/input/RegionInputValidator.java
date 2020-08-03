package br.com.sga.core.validator.input;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.sga.core.exception.BusinessValidationException;
import br.com.sga.core.model.Region;
import br.com.sga.core.validator.business.RegionBusinessValidator;

@Component
public class RegionInputValidator implements Validator {

	@Autowired
	private RegionBusinessValidator regionBusinessValidator;

	@Override
	public boolean supports(Class<?> clazz) {
		return RegionInputValidator.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "input.region.name.notEmpty");
		
	}

	public void validateSave(Region region, Errors errors) {
		this.validate(region, errors);
	
//		if(!errors.hasErrors()) {
//			try {
//				
//				this.regionBusinessValidator.validaNomePermitidos(region);
//	
//			} catch(BusinessValidationException e) {
//				errors.rejectValue(e.getFieldId(), e.getMessageKey());
//			}
//		}
	}

}
