package br.com.sga.core.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.format.Formatter;

public class DoubleSuffixFormatter implements Formatter<Double> {
	
    private Suffix suffix = Suffix.PERCENT;

    public void setSuffix (Suffix suffix) {
        this.suffix = suffix;
    }
	
    @Override
	public Double parse(String text, Locale locale) throws ParseException {
    	
		if(StringUtils.isEmpty(text)){
			return 0D;
		}

		if (suffix == Suffix.PERCENT) {
			text = text.replace("%", "");
		}
		
		text = text.replace(",", ".");
		text = text.trim();
		
		return new Double(text);
    }

    @Override
	public String print(Double object, Locale locale) {
		
		String str = "";
		
		if(object.doubleValue() % 1 == 0){
			int objectInt = ((int) object.doubleValue());
			str = String.valueOf(objectInt);
		}else{
			str = String.valueOf(object.doubleValue());
			str = str.replace(".", ",");
		}
		
		if(suffix == Suffix.PERCENT){
			str += " %";
		}
		
		return str;
    }
    
    public enum Suffix {
        PERCENT
    }
}
