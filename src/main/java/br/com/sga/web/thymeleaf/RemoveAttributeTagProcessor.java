package br.com.sga.web.thymeleaf;

import org.springframework.util.StringUtils;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;
import org.thymeleaf.templatemode.TemplateMode;

public class RemoveAttributeTagProcessor extends AbstractAttributeTagProcessor {

	private static final String ATTRIBUTE = "removeAttribute";
    private static final int PRECEDENCE = 10;
    
	public RemoveAttributeTagProcessor(String dialectPrefix) {
		 super(TemplateMode.HTML, dialectPrefix, null, false, ATTRIBUTE, true, PRECEDENCE, true);
	}

	@Override
	protected void doProcess(ITemplateContext context, IProcessableElementTag tag, AttributeName attributeName, String attributeValues, IElementTagStructureHandler structureHandler) {

		if (StringUtils.isEmpty(attributeValues)) {
			return;
		}
		
		for (String attributeValue : attributeValues.split(";")) {
			
			attributeValue = attributeValue.trim();
			
			//check if has any expression
			if (attributeValue.contains("$") || attributeValue.contains("*")) {
				
				IStandardExpressionParser parser = StandardExpressions.getExpressionParser(context.getConfiguration());
				IStandardExpression expression = parser.parseExpression(context, attributeValue);
				
				removeAttribute(structureHandler, expression.execute(context));
				
			}else{
				
				removeAttribute(structureHandler, attributeValue);
			}
		}
	}
	
	private void removeAttribute(IElementTagStructureHandler structureHandler, Object attributes){
		
		if (attributes == null) {
			return;
		}
		
		for (Object attribute : ((String) attributes).split(",")) {
			
			if (StringUtils.isEmpty(attribute)) {
				break;
			}
			
			structureHandler.removeAttribute(attribute.toString());
		}
	}
}
