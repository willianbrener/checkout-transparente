package br.com.sga.core.formatter.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.com.sga.core.formatter.DateRangeFormatter;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateRangeFormat {
	
	DateRangeFormatter.Formato formato () default DateRangeFormatter.Formato.DIA_MES_ANO;

}
