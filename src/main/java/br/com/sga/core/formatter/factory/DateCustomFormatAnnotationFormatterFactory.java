package br.com.sga.core.formatter.factory;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import br.com.sga.core.formatter.DateCustomFormatter;
import br.com.sga.core.formatter.annotations.DateCustomFormat;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class DateCustomFormatAnnotationFormatterFactory implements AnnotationFormatterFactory<DateCustomFormat> {
	@Override
	public Set<Class<?>> getFieldTypes() {
		return new HashSet<>(Arrays.asList(Date.class));
	}

	@Override
	public Printer<?> getPrinter(DateCustomFormat annotation, Class<?> fieldType) {
		return getDateCustomFormatter(annotation, fieldType);
	}

	@Override
	public Parser<?> getParser(DateCustomFormat annotation, Class<?> fieldType) {
		return getDateCustomFormatter(annotation, fieldType);
	}

	private DateCustomFormatter getDateCustomFormatter(DateCustomFormat annotation, Class<?> fieldType) {
		DateCustomFormatter formatter = new DateCustomFormatter();
		formatter.setFormato(annotation.formato());
		return formatter;
	}
}