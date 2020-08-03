package br.com.sga.web.thymeleaf;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.model.IStandaloneElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

public class MessageElementTagProcessor extends AbstractElementTagProcessor {

	private static final String TAG_NAME = "messages";
    private static final int PRECEDENCE = 10;
	
	public MessageElementTagProcessor(String dialectPrefix) {
		 super(TemplateMode.HTML, dialectPrefix, TAG_NAME, true, null, false, PRECEDENCE);
	}

	@Override
	protected void doProcess(ITemplateContext context, IProcessableElementTag tag, IElementTagStructureHandler structureHandler) {
		
		IModelFactory modelFactory = context.getModelFactory();
		
		IModel iModel = modelFactory.createModel();
		
		String attributes = ElementTagProcessorSupport.extractAttributesAsString(tag);
		
		IStandaloneElementTag createStandaloneElementTag = modelFactory.createStandaloneElementTag("th:block", "th:replace", String.format("core-layout/dialects/messages :: messages ('%s')", attributes));
		
		iModel.add(createStandaloneElementTag);
		
		structureHandler.replaceWith(iModel,true);
	}
}
