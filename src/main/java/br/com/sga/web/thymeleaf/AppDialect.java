package br.com.sga.web.thymeleaf;
import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;

public class AppDialect extends AbstractProcessorDialect {

	public AppDialect() {
		super("App Dialect", "dialect", 10);
	}

	@Override
	public Set<IProcessor> getProcessors(String dialectPrefix) {

		Set<IProcessor> processors = new HashSet<IProcessor>();
		
		processors.add(new MessageElementTagProcessor(dialectPrefix));
		processors.add(new RemoveAttributeTagProcessor(dialectPrefix));
		processors.add(new PaginationElementTagProcessor(dialectPrefix));
		processors.add(new CheckErrorAttributeTagProcessor(dialectPrefix));
		
		return processors;
	}
}
