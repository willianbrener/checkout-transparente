package br.com.sga.web.thymeleaf;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.spring4.util.FieldUtils;
import org.thymeleaf.templatemode.TemplateMode;

public class CheckErrorAttributeTagProcessor extends AbstractAttributeTagProcessor {

	private static final String ATTRIBUTE = "checkError";
    private static final int PRECEDENCE = 10;
    
	public CheckErrorAttributeTagProcessor(String dialectPrefix) {
		 super(TemplateMode.HTML, dialectPrefix, null, false, ATTRIBUTE, true, PRECEDENCE, true);
	}

	@Override
	protected void doProcess(ITemplateContext context, IProcessableElementTag tag, AttributeName attributeName, String attributeValue, IElementTagStructureHandler structureHandler) {

		boolean hasError = FieldUtils.hasErrors(context, attributeValue);
		
		if (hasError) {
			structureHandler.setAttribute("class", tag.getAttributeValue("class") + " is-invalid");
		}
	}
}
