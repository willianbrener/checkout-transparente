package br.com.sga.core.util;

import org.springframework.data.jpa.domain.Specification;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class SpecificationUtil {

	public static Specification addClausulaOr(Specification where, Specification novaClausula){
		if(where == null){
			return Specification.where(novaClausula);
		} else {
			return Specification.where(where).or(novaClausula);
		}
	}

	public static Specification addClausulaAnd(Specification where, Specification novaClausula){
		if(where == null){
			return Specification.where(novaClausula);
		} else {
			return Specification.where(where).and(novaClausula);
		}
	}
	
	
}
