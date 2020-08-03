package br.com.sga.core.validator.input;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.sga.core.exception.BusinessValidationException;
import br.com.sga.core.model.Location;
import br.com.sga.core.validator.business.LocationBusinessValidator;

@Component
public class LocationInputValidator implements Validator {

	@Autowired
	private LocationBusinessValidator locationBusinessValidator;

	@Override
	public boolean supports(Class<?> clazz) {
		return LocationInputValidator.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "streetAddress", "input.location.streetAddress.notEmpty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "postalCode", "input.location.postalCode.notEmpty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "input.location.city.notEmpty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "stateProvince", "input.location.stateProvince.notEmpty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "country.id", "input.location.country.id.notEmpty");
		
	}

	public void validateSave(Location location, Errors errors) {
		this.validate(location, errors);
	
		if(!errors.hasErrors()) {
//			try {
//				
//				this.locationBusinessValidator.validaNomePermitidos(location);
//	
//			} catch(BusinessValidationException e) {
//				errors.rejectValue(e.getFieldId(), e.getMessageKey());
//			}
		}
	}

}
