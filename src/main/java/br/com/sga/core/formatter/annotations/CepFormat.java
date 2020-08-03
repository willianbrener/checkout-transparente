package br.com.sga.core.formatter.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.com.sga.core.formatter.CepFormatter;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CepFormat {

	CepFormatter.Formato formato () default CepFormatter.Formato.CEP;

}
