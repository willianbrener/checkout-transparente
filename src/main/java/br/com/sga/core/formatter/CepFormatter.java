package br.com.sga.core.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.format.Formatter;

import br.com.sga.core.util.StringUtil;

public class CepFormatter implements Formatter<String> {

	private Formato formato = Formato.CEP;

	public void setFormato (Formato formato) {
		this.formato = formato;
	}

	@Override
	public String parse(String text, Locale locale) throws ParseException {
		if (StringUtils.isEmpty(text)) {
			return "0";
		}

		if (formato == Formato.CEP) {
			text = text.replace(".", "");
			text = text.replace("-", "");
			text = text.trim();
		}

		return text;
	}

	@Override
	public String print(String object, Locale locale) {

		if (formato == Formato.CEP && object != null) {
			String str = String.valueOf(object);
			Integer qtdZeroEsquerda = str.length() - 8;
			str = StringUtil.StrZeroEsquerda(str, qtdZeroEsquerda);

			if (str.length() == 8) {
				StringBuilder sb = new StringBuilder();

				sb.append(str.substring(0, 2));
				sb.append(".");
				sb.append(str.substring(2, 5));
				sb.append("-");
				sb.append(str.substring(5, str.length()));

				return sb.toString();
			}
		}

		return String.valueOf(object);
	}

	public enum Formato {
		CEP
	}
}
