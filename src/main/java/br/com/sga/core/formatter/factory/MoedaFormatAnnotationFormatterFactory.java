package br.com.sga.core.formatter.factory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import br.com.sga.core.formatter.MoedaFormatter;
import br.com.sga.core.formatter.annotations.MoedaFormat;

public class MoedaFormatAnnotationFormatterFactory implements AnnotationFormatterFactory<MoedaFormat> {
	@Override
	public Set<Class<?>> getFieldTypes() {
		return new HashSet<>(Arrays.asList(Double.class));
	}

	@Override
	public Printer<?> getPrinter(MoedaFormat annotation, Class<?> fieldType) {
		return getMoedaFormatter(annotation, fieldType);
	}

	@Override
	public Parser<?> getParser(MoedaFormat annotation, Class<?> fieldType) {
		return getMoedaFormatter(annotation, fieldType);
	}

	private MoedaFormatter getMoedaFormatter(MoedaFormat annotation, Class<?> fieldType) {
		MoedaFormatter formatter = new MoedaFormatter();
		formatter.setMoeda(annotation.moeda());
		return formatter;
	}
}