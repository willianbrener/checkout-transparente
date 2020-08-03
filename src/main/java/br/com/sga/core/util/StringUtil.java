package br.com.sga.core.util;

import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;

import javax.swing.text.MaskFormatter;

public class StringUtil {
	
	public static String gerarSenha() {
		int generated = generateRandomIntIntRange(0, 999999);
		return String.format("%06d", generated);
	}
	
	public static int generateRandomIntIntRange(int min, int max) {
	    Random r = new Random();
	    return r.nextInt((max - min) + 1) + min;
	}

	public static String StrZeroEsquerda(String value, int n) {
		String s = value.trim();
		StringBuffer resp = new StringBuffer();
		int fim = n - s.length();
		for (int x = 0; x < fim; x++)
			resp.append('0');
		return resp + s;
	}
	
	public static  String formataCpf(String string) {
		String semCaracteresEspeciais = removerMascaras(string);

		if(semCaracteresEspeciais.length() < 11){
			semCaracteresEspeciais = completarAEsquerda(semCaracteresEspeciais,  '0', 11);
		}

		return semCaracteresEspeciais.substring(0,3)+"."+semCaracteresEspeciais.substring(3,6)+"."+semCaracteresEspeciais.substring(6,9)+"-"+semCaracteresEspeciais.substring(9,11);
	}

	public static  String formataCnpj(String string) {
		String semCaracteresEspeciais = removerMascaras(string);

		if(semCaracteresEspeciais.length() < 14){
			semCaracteresEspeciais = completarAEsquerda(semCaracteresEspeciais,  '0', 14);
		}

		return semCaracteresEspeciais.substring(0,2)+"."+semCaracteresEspeciais.substring(2,5)+"."+semCaracteresEspeciais.substring(5,8)+"/"+semCaracteresEspeciais.substring(8,12) +"-"+semCaracteresEspeciais.substring(12,14);
	}
	
	public static  String formataInscricaoEstadual(String string) {
		try {
			String semCaracteresEspeciais = removerMascaras(string);
			
			return semCaracteresEspeciais.substring(0,2)+"."+semCaracteresEspeciais.substring(2,5)+"."+semCaracteresEspeciais.substring(5,8)+"-"+semCaracteresEspeciais.substring(8,9);
		} catch (Exception e) {}
		
		return string;
	}
	
	public static String completarAEsquerda(String value, char c, int size) {
		String result = value;

		while (result.length() < size) {
			result = c + result;
		}

		return result;
	}
	
	public static String removerMascaras(final String s) {
		return s.replaceAll("[.]", "").replaceAll("[-]", "").replaceAll("[/]", "").replaceAll("[(]", "").replaceAll("[ ]","").replaceAll("[:]", "").replaceAll("[)]", "");
	}

	public static String formataTamanhoArquivo(Long size) {
		if (size <= 0)
			return "0";
		final String[] units = new String[] { "B", "kB", "MB", "GB", "TB" };
		int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
		return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
	}

	public static String apenasNumeros(String str){
		return str.replaceAll("\\D+","");
	}

	public static String padraoBanco(String str){
		str = str.toUpperCase();
		str = removerAcentos(str);

		return str;
	}

	public static String removerAcentos(String str) {
		return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}

	public static String formatarString(String mascara, String texto) {
		MaskFormatter mf;
		try {
			mf = new MaskFormatter(mascara);
			mf.setValueContainsLiteralCharacters(false);

			return mf.valueToString(texto);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return mascara;
	}

	public static String formataFloatEmMoeda(Float valor, Boolean removeRSifrao) {
		try {
			String retorno = NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(valor);

			if(retorno != null && removeRSifrao) {
				retorno = retorno.replace("R$ ", "");
			}

			return retorno;
		}catch (Exception e) {
			return valor.toString();
		}
	}
	
	public static String formataDateToString(Date date) {
		return formataDateToString(date, null);
	}
	
	public static String formataDateToString(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern == null ? "dd/MM/yyyy" : pattern);
		
		if(date != null) {
			try {

				return sdf.format(date);
			} catch (Exception e) {}
		}

		return "";
	}
	
	public static String formataGregorianCalendarToString(GregorianCalendar date, String pattern) {
		StringBuffer formattedDate = new StringBuffer();
		try {
			if(pattern.equalsIgnoreCase("DD/MM/YYYY")) {
				formattedDate.append(StrZeroEsquerda(String.valueOf(date.get(GregorianCalendar.DAY_OF_MONTH)), 2 )).append("/");
				formattedDate.append(StrZeroEsquerda(String.valueOf(date.get(GregorianCalendar.MONTH) + 1), 2)).append("/");
				formattedDate.append(StrZeroEsquerda(String.valueOf(date.get(GregorianCalendar.YEAR)), 2));
			} else if(pattern.equalsIgnoreCase("YYYY-MM-DD")) {
				formattedDate.append(StrZeroEsquerda(String.valueOf(date.get(GregorianCalendar.YEAR)), 2)).append("-");
				formattedDate.append(StrZeroEsquerda(String.valueOf(date.get(GregorianCalendar.MONTH) + 1), 2)).append("-");
				formattedDate.append(StrZeroEsquerda(String.valueOf(date.get(GregorianCalendar.DAY_OF_MONTH)), 2 ));
			}
		} catch (Exception e) {}
		
		return formattedDate.toString();
	}
}
