package br.com.sga.core.validator.input;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.sga.core.exception.BusinessValidationException;
import br.com.sga.core.model.ExampleMask;
import br.com.sga.core.validator.business.ExampleMaskBusinessValidator;

@Component
public class ExampleMaskInputValidator implements Validator {
	
	@Autowired
	private ExampleMaskBusinessValidator exampleMaskBusinessValidator;

	@Override
	public boolean supports(Class<?> clazz) {
		return ExampleMaskInputValidator.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cpf", "input.exampleMask.cpf.notEmpty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cnpj", "input.exampleMask.cnpj.notEmpty");
		
	}

	public void validateSave(ExampleMask exampleMask, Errors errors) {
		this.validate(exampleMask, errors);
	}

	public void validateAddEListMask(ExampleMask exampleMask, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "exampleMaskChildrenInputDTO.inputExampleMaskChildren.descricao", "input.listChildrens.emasklist.descricao.notEmpty");
	}

	public void validateRemoveEListMask(ExampleMask exampleMask, Errors errors) {
		try {
			
			this.exampleMaskBusinessValidator.validaSePermiteExcluirChildren(exampleMask);

		} catch(BusinessValidationException e) {
			errors.rejectValue(e.getFieldId(), e.getMessageKey());
		}
	}
}
