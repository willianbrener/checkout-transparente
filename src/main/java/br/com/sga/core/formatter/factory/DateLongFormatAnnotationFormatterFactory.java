package br.com.sga.core.formatter.factory;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import br.com.sga.core.formatter.DateLongFormatter;
import br.com.sga.core.formatter.annotations.DateLongFormat;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DateLongFormatAnnotationFormatterFactory implements AnnotationFormatterFactory<DateLongFormat> {
	@Override
	public Set<Class<?>> getFieldTypes() {
		return new HashSet<>(Arrays.asList(Long.class));
	}

	@Override
	public Printer<?> getPrinter(DateLongFormat annotation, Class<?> fieldType) {
		return getDateLongFormatter(annotation, fieldType);
	}

	@Override
	public Parser<?> getParser(DateLongFormat annotation, Class<?> fieldType) {
		return getDateLongFormatter(annotation, fieldType);
	}

	private DateLongFormatter getDateLongFormatter(DateLongFormat annotation, Class<?> fieldType) {
		DateLongFormatter formatter = new DateLongFormatter();
		formatter.setFormato(annotation.formato());
		return formatter;
	}
}