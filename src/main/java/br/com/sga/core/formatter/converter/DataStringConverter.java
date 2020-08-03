package br.com.sga.core.formatter.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class DataStringConverter implements Converter<String, Date>{
	
	private Logger LOGGER = LoggerFactory.getLogger(DataStringConverter.class);

	
	@Override
	public Date convert(String source) {
		if (!StringUtils.isEmpty(source)) {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			try {
				return format.parse(source);
			} catch (ParseException e) {
				LOGGER.error(e.getMessage());
				return null;
			}
		}
		return null;
	}

}
