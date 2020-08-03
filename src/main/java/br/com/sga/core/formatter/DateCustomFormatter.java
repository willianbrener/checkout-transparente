package br.com.sga.core.formatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.format.Formatter;

public class DateCustomFormatter implements Formatter<Date> {
	
    private Formato formato = Formato.DIA_MES_ANO;

    public void setFormato (Formato formato) {
        this.formato = formato;
    }
	
    @Override
	public Date parse(String text, Locale locale) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(this.formato.getValue());
        return sdf.parse(text);
    }

    @Override
	public String print(Date object, Locale locale) {
		DateFormat df = new SimpleDateFormat(this.formato.getValue());
		return df.format(object);
    }
    
    public enum Formato {
        DIA_MES_ANO("dd/MM/yyyy"),
        DIA_MES_ANO_HORA("dd/MM/yyyy HH:mm:ss"),
        MES_ANO("MM/yyyy"),
        ANO_MES_DIA_HORA("yyyy-MM-dd HH:mm:ss"),
        ANO_MES_DIA("yyyy-MM-dd");
    	
    	String value;

		private Formato(String value){
    		this.value = value;
    	}
    	
    	public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
    }
}
