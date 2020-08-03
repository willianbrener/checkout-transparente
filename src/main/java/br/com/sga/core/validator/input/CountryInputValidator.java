package br.com.sga.core.validator.input;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.sga.core.exception.BusinessValidationException;
import br.com.sga.core.model.Country;
import br.com.sga.core.validator.business.CountryBusinessValidator;

@Component
public class CountryInputValidator implements Validator {

	@Autowired
	private CountryBusinessValidator countryBusinessValidator;

	@Override
	public boolean supports(Class<?> clazz) {
		return CountryInputValidator.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "input.country.name.notEmpty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "region.id", "input.country.region.notEmpty");
		
	}

	public void validateSave(Country country, Errors errors) {
		this.validate(country, errors);
	
		if(!errors.hasErrors()) {
			try {
				
				this.countryBusinessValidator.validaNomePermitidos(country);
	
			} catch(BusinessValidationException e) {
				errors.rejectValue(e.getFieldId(), e.getMessageKey());
			}
		}
	}

}
