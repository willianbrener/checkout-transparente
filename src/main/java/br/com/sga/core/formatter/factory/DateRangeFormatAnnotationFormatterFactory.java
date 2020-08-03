package br.com.sga.core.formatter.factory;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import br.com.sga.core.formatter.DateRangeFormatter;
import br.com.sga.core.formatter.annotations.DateRangeFormat;

public class DateRangeFormatAnnotationFormatterFactory implements AnnotationFormatterFactory<DateRangeFormat> {
	@Override
	public Set<Class<?>> getFieldTypes() {
		return new HashSet<>(Arrays.asList(Date[].class));
	}

	@Override
	public Printer<?> getPrinter(DateRangeFormat annotation, Class<?> fieldType) {
		return getDateRangeFormatter(annotation, fieldType);
	}

	@Override
	public Parser<?> getParser(DateRangeFormat annotation, Class<?> fieldType) {
		return getDateRangeFormatter(annotation, fieldType);
	}

	private DateRangeFormatter getDateRangeFormatter(DateRangeFormat annotation, Class<?> fieldType) {
		DateRangeFormatter formatter = new DateRangeFormatter();
		formatter.setFormato(annotation.formato());
		return formatter;
	}
}