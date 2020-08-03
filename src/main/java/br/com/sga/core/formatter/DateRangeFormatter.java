package br.com.sga.core.formatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.format.Formatter;

import br.com.sga.core.util.DateUtil;

public class DateRangeFormatter implements Formatter<Date[]> {
	
    private Formato formato = Formato.DIA_MES_ANO;

    public void setFormato (Formato formato) {
        this.formato = formato;
    }
	
    @Override
	public Date[] parse(String text, Locale locale) throws ParseException {
    	
    	Date[] retorno = new Date[2];
    	
    	if(formato == Formato.DIA_MES_ANO){

    		DateFormat df = new SimpleDateFormat(this.formato.getValue());
    		
    		String inputSplitted[] = text.split(" - ");
    		
    		if(inputSplitted != null && inputSplitted.length == 2){
    			
    			retorno[0] = DateUtil.obtemInicioDoDia(df.parse(inputSplitted[0]));
    			retorno[1] = DateUtil.obtemFimDoDia(df.parse(inputSplitted[1]));
    		}
    	}
    	
        return retorno;
    }

    @Override
	public String print(Date[] object, Locale locale) {
    	
    	if(formato == Formato.DIA_MES_ANO){
    		if(object != null && object.length == 2 && object[0] != null && object[1] != null){
        		SimpleDateFormat sdf = new SimpleDateFormat(this.formato.getValue());
        		
        		StringBuilder sb = new StringBuilder();
        		sb.append(sdf.format(object[0]));
        		sb.append(" - ");
        		sb.append(sdf.format(object[1]));
        		
                return sb.toString();
    		}
    	}
		
		return "";
    }
    
    public enum Formato {
        DIA_MES_ANO("dd/MM/yyyy");
    	
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
