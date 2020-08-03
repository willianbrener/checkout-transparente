package br.com.sga.core.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;

public class HourStringFormatter implements Formatter<String> {
	
    private Formato formato = Formato.HORA_MINUTO;

    public void setFormato (Formato formato) {
        this.formato = formato;
    }

    @Override
	public String parse(String text, Locale locale) throws ParseException {
    	
    	if(text != null && text.trim().length() == 5) {
    		if(formato == Formato.HORA_MINUTO && text.trim().length() == 5) {
        		return text.replaceAll(":", "");
    		}
    	}
    	
        return null;
    }

    @Override
	public String print(String object, Locale locale) {
    	
    	if(formato == Formato.HORA_MINUTO){
    		
    		if(object != null){
        		String str = String.valueOf(object);
        		if(str != null && str.length() == 4) {
        			return str.substring(0, 2) +":"+str.substring(2, 4);
        		}
    		}
    	}
		
		return null;
    }
    
    public enum Formato {
        HORA_MINUTO,
        HORA_MINUTO_SEGUNDO
    }
}
