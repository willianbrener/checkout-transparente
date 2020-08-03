package br.com.sga.core.formatter.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.com.sga.core.formatter.DateCustomFormatter;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateCustomFormat {
	
	DateCustomFormatter.Formato formato () default DateCustomFormatter.Formato.DIA_MES_ANO;

}
