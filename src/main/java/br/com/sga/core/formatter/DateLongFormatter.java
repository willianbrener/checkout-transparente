package br.com.sga.core.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;

public class DateLongFormatter implements Formatter<Long> {
	
    private Formato formato = Formato.MES_ANO;

    public void setFormato (Formato formato) {
        this.formato = formato;
    }

    @Override
	public Long parse(String text, Locale locale) throws ParseException {
    	
    	if(formato == Formato.MES_ANO){
    		String inputSplitted[] = text.split("/");
    		
    		if(inputSplitted != null && inputSplitted.length == 2){
    			String mesStr = inputSplitted[0];
    			String anoStr = inputSplitted[1];
    			String anoMesStr = anoStr+mesStr;
    			return new Long(anoMesStr);
    		}
    	}
    	
        return new Long(text);
    }

    @Override
	public String print(Long object, Locale locale) {
    	
    	if(formato == Formato.MES_ANO){
    		String str = String.valueOf(object);
    		
    		if(str != null && str.length() == 6){
    			return str.substring(4, 6) +"/"+str.substring(0, 4);
    		}
    	}
		
		return String.valueOf(object);
    }
    
    public enum Formato {
        MES_ANO
    }
}
