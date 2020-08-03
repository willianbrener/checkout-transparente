package br.com.sga.core.dto;

import br.com.sga.core.model.ExampleMaskChildren;

public class ExampleMaskChildrenInputDTO {

	private ExampleMaskChildren inputExampleMaskChildren;

	private Integer indiceEdicaoExampleMaskChildren;
	
	public ExampleMaskChildren getInputExampleMaskChildren() {
		return inputExampleMaskChildren;
	}

	public void setInputExampleMaskChildren(ExampleMaskChildren inputExampleMaskChildren) {
		this.inputExampleMaskChildren = inputExampleMaskChildren;
	}

	public Integer getIndiceEdicaoExampleMaskChildren() {
		return indiceEdicaoExampleMaskChildren;
	}

	public void setIndiceEdicaoExampleMaskChildren(Integer indiceEdicaoExampleMaskChildren) {
		this.indiceEdicaoExampleMaskChildren = indiceEdicaoExampleMaskChildren;
	}

}
