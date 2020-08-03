package br.com.sga.core.formatter;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.format.Formatter;

public class MoedaFormatter implements Formatter<Double> {
	
    private Moeda moeda = Moeda.REAL;

    public void setMoeda (Moeda moeda) {
        this.moeda = moeda;
    }
	
    @Override
	public Double parse(String text, Locale locale) throws ParseException {
		
		if(StringUtils.isEmpty(text)){
			return 0D;
		}
    	
    	if(moeda == Moeda.REAL){
    		text = text.replace("R$", "");
    	}else if(moeda == Moeda.DOLAR){
    		text = text.replace("$", "");
    	}else if(moeda == Moeda.EURO){
    		text = text.replace("€", "");
    	}
    	
		text = text.replace(".", "");
		text = text.replace(",", ".");
		text = text.trim();
		
		return new Double(text);
    }

    @Override
	public String print(Double object, Locale locale) {

    	if(moeda == Moeda.REAL){
    		Locale BRAZIL = new Locale("pt","BR");
    		DecimalFormatSymbols REAL = new DecimalFormatSymbols(BRAZIL);
    		DecimalFormat DINHEIRO_REAL = new DecimalFormat("R$ ###,###,##0.00",REAL);
    		
    		return DINHEIRO_REAL.format(object);
    		
    	}else if(moeda == Moeda.DOLAR){
    	    DecimalFormatSymbols DOLAR = new DecimalFormatSymbols(Locale.US);
    	    DecimalFormat DINHEIRO_DOLAR = new DecimalFormat("$ ###,###,##0.00",DOLAR);
    	    
    		return DINHEIRO_DOLAR.format(object);
    		
    	}else if(moeda == Moeda.EURO){
    		DecimalFormatSymbols EURO = new DecimalFormatSymbols(Locale.GERMANY);
    	    DecimalFormat DINHEIRO_EURO = new DecimalFormat("€ ###,###,##0.00",EURO);
    	    
    		return DINHEIRO_EURO.format(object);
    	}
		
		return String.valueOf(object);
    }
    
    public enum Moeda {
        REAL,
        DOLAR,
        EURO
    }
}
