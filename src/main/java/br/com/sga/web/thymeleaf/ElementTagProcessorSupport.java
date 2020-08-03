package br.com.sga.web.thymeleaf;

import java.util.Map;

import org.thymeleaf.model.IProcessableElementTag;

public class ElementTagProcessorSupport {

	private ElementTagProcessorSupport() {
	}
	
	public static String extractAttributesAsString(IProcessableElementTag tag) {
		String attributes = null;
		Map<String, String> attributeMap = tag.getAttributeMap();

		if (attributeMap != null && !attributeMap.isEmpty()) {
			attributes = attributeMap.toString().replaceAll("^\\{|\\}$", "");
		}
		return attributes;
	}
}
