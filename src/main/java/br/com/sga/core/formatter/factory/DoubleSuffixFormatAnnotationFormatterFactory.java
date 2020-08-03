package br.com.sga.core.formatter.factory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import br.com.sga.core.formatter.DoubleSuffixFormatter;
import br.com.sga.core.formatter.annotations.DoubleSuffixFormat;

public class DoubleSuffixFormatAnnotationFormatterFactory implements AnnotationFormatterFactory<DoubleSuffixFormat> {
	@Override
	public Set<Class<?>> getFieldTypes() {
		return new HashSet<>(Arrays.asList(Double.class));
	}

	@Override
	public Printer<?> getPrinter(DoubleSuffixFormat annotation, Class<?> fieldType) {
		return getDoubleSuffixFormatter(annotation, fieldType);
	}

	@Override
	public Parser<?> getParser(DoubleSuffixFormat annotation, Class<?> fieldType) {
		return getDoubleSuffixFormatter(annotation, fieldType);
	}

	private DoubleSuffixFormatter getDoubleSuffixFormatter(DoubleSuffixFormat annotation, Class<?> fieldType) {
		DoubleSuffixFormatter formatter = new DoubleSuffixFormatter();
		formatter.setSuffix(annotation.suffix());
		return formatter;
	}
}