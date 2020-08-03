package br.com.sga.core.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class DateUtil {
	
	public static Date obtemInicioDoDia(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static Date obtemFimDoDia(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, cal.getMaximum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, cal.getMaximum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getMaximum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getMaximum(Calendar.MILLISECOND));
		return cal.getTime();
	}
	
	public static Date obtemPrimeiroDiaMes(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	public static Date obtemUltimoDiaMes(Date date) {
		LocalDate localDate = obtemLocalDatePorDate(date);

		localDate = localDate.withDayOfMonth(localDate.getMonth().length(localDate.isLeapYear()));
		
		date = obtemDatePorLocalDate(localDate);
    	
		date = obtemFimDoDia(date);
		
		return date;
	}
	
	public static String obtemToDateOracle(Date date){
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		return df.format(date);
	}
	
	public static String obtemToDateMesAnoOracle(Date date){
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		return df.format(date);
	}
	
	public static String obtemDataDiaMes(Date date){
		DateFormat df = new SimpleDateFormat("dd/MM");
		return df.format(date);
	}
	
	public static LocalDate obtemLocalDatePorDate(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}
	
	public static Date obtemDatePorLocalDate(LocalDate localDate){
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
	
	public static LocalDate obtemLocalDatePorPadrao(String data, String padrao){
		return LocalDate.parse(data, DateTimeFormatter.ofPattern(padrao));
	}
	
	public static String obtemStrMesAnoLocalDate(LocalDate localDate){
		String mesAno = "";
		if(localDate.getMonthValue() < 10){
			mesAno += "0";
		}
		mesAno += localDate.getMonthValue();
		mesAno += "/";
		mesAno += String.valueOf(localDate.getYear()).substring(2,4);
		return mesAno;
	}
	
	public static Date generateRandomDate() {
		Instant hundredYearsAgo = Instant.now().minus(Duration.ofDays(2 * 365));
		Instant tenDaysAgo = Instant.now().minus(Duration.ofDays(1));
		Instant random = dateBetween(hundredYearsAgo, tenDaysAgo);
		return Date.from(random);
	}
	
	public static Instant dateBetween(Instant startInclusive, Instant endExclusive) {
	    long startSeconds = startInclusive.getEpochSecond();
	    long endSeconds = endExclusive.getEpochSecond();
	    long random = ThreadLocalRandom
	      .current()
	      .nextLong(startSeconds, endSeconds);
	 
	    return Instant.ofEpochSecond(random);
	}
}
