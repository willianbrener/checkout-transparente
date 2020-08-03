package br.com.sga.core.formatter.factory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import br.com.sga.core.formatter.CepFormatter;
import br.com.sga.core.formatter.annotations.CepFormat;

public class CepFormatAnnotationFormatterFactory implements AnnotationFormatterFactory<CepFormat> {
	@Override
	public Set<Class<?>> getFieldTypes() {
		return new HashSet<>(Arrays.asList(String.class));
	}

	@Override
	public Printer<?> getPrinter(CepFormat annotation, Class<?> fieldType) {
		return getCepFormatter(annotation, fieldType);
	}

	@Override
	public Parser<?> getParser(CepFormat annotation, Class<?> fieldType) {
		return getCepFormatter(annotation, fieldType);
	}

	private CepFormatter getCepFormatter(CepFormat annotation, Class<?> fieldType) {
		CepFormatter formatter = new CepFormatter();
		formatter.setFormato(annotation.formato());
		return formatter;
	}
}