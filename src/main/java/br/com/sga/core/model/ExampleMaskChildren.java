package br.com.sga.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "EXAMPLE_MASK_CHILDREN")
public class ExampleMaskChildren implements Serializable {
	
	private static final long serialVersionUID = 7575404176175821468L;

	@Id
	@Column(name = "EXAMPLE_MASK_CHILDREN_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "DESCRICAO")
	private String descricao;
	
	@ManyToOne
	@JoinColumn(name = "EXAMPLE_MASK_ID")
	private ExampleMask exampleMask;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public ExampleMask getExampleMask() {
		return exampleMask;
	}

	public void setExampleMask(ExampleMask exampleMask) {
		this.exampleMask = exampleMask;
	}
}
