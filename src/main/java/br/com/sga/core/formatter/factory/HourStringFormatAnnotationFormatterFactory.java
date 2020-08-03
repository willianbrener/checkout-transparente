package br.com.sga.core.formatter.factory;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import br.com.sga.core.formatter.HourStringFormatter;
import br.com.sga.core.formatter.annotations.HourStringFormat;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class HourStringFormatAnnotationFormatterFactory implements AnnotationFormatterFactory<HourStringFormat> {
	@Override
	public Set<Class<?>> getFieldTypes() {
		return new HashSet<>(Arrays.asList(Long.class));
	}

	@Override
	public Printer<?> getPrinter(HourStringFormat annotation, Class<?> fieldType) {
		return getHourIntegerFormatter(annotation, fieldType);
	}

	@Override
	public Parser<?> getParser(HourStringFormat annotation, Class<?> fieldType) {
		return getHourIntegerFormatter(annotation, fieldType);
	}

	private HourStringFormatter getHourIntegerFormatter(HourStringFormat annotation, Class<?> fieldType) {
		HourStringFormatter formatter = new HourStringFormatter();
		formatter.setFormato(annotation.formato());
		return formatter;
	}
}