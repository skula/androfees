package com.skula.androfees.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CalendarUtils {
	private final static SimpleDateFormat frenchFormat = new SimpleDateFormat("dd/MM/yyyy");
	private final static SimpleDateFormat sqliteFormat = new SimpleDateFormat("yyyy-MM-dd");

	public static void main(String args[]) {
		Calendar cal2 = CalendarUtils.string2Calendar("1988-04-02");
		System.out.println("1988-04-02  =?  " + CalendarUtils.calendar2String(cal2));

		Calendar cal = CalendarUtils.getCalendarFrom(3, Calendar.DAY_OF_MONTH);
		System.out.println(CalendarUtils.calendar2String(cal));

		cal = CalendarUtils.getCalendarFrom(3, Calendar.MONTH);
		System.out.println(CalendarUtils.calendar2String(cal));

		cal = CalendarUtils.getCalendarFrom(3, Calendar.YEAR);
		System.out.println(CalendarUtils.calendar2String(cal));
	}

	public static String frenchToSqlite(String date) {
		try {
			return sqliteFormat.format(frenchFormat.parse(date));
		} catch (ParseException e) {
			return "";
		}

	}

	public static String calendar2String(Calendar cal) {
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);

		return year + "-" + (day < 10 ? "0" + day : day + "") + "-" + (day < 10 ? "0" + day : day + "");
	}

	public static Calendar string2Calendar(String date) {
		String slices[] = date.split("-");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.valueOf(slices[0]));
		cal.set(Calendar.MONTH, Integer.valueOf(slices[1])-1);
		cal.set(Calendar.DAY_OF_MONTH, Integer.valueOf(slices[2]));
		return cal;
	}

	public static Calendar getCalendarFrom(int count, int typePeriod) {
		Calendar cal = Calendar.getInstance();
		if (typePeriod != Calendar.DAY_OF_MONTH) {
			cal.set(Calendar.DAY_OF_MONTH, 1);
		}
		cal.add(typePeriod, -1 * count);
		return cal;
	}

	public static String getStringFrom(int count, int typePeriod) {
		return calendar2String(getCalendarFrom(count, typePeriod));
	}

	public static String sqliteToFrench(String date) {
		try {
			return frenchFormat.format(sqliteFormat.parse(date));
		} catch (ParseException e) {
			return "";
		}
	}

	public static Calendar getNextMonth(int month, int year) {
		Calendar cal = Calendar.getInstance(Locale.FRANCE);
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.add(Calendar.MONTH, 1);
		return cal;
	}

	public static Calendar getPreviousMonth(int month, int year) {
		Calendar cal = Calendar.getInstance(Locale.FRANCE);
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.add(Calendar.MONTH, -1);
		return cal;
	}

	public static String getMonthLabel(int month) {
		switch (month) {
		case 1:
			return "JANVIER";
		case 2:
			return "FEVRIER";
		case 3:
			return "MARS";
		case 4:
			return "AVRIL";
		case 5:
			return "MAI";
		case 6:
			return "JUIN";
		case 7:
			return "JUILLET";
		case 8:
			return "AOUT";
		case 9:
			return "SEPTEMBRE";
		case 10:
			return "OCTOBRE";
		case 11:
			return "NOVEMBRE";
		case 12:
			return "DECEMBRE";
		default:
			return "";
		}
	}

	public static String getFirstDay(int month, int year) {
		Calendar cal = Calendar.getInstance(Locale.FRANCE);
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, 0);
		return sqliteFormat.format(cal.getTime());
	}

	public static String getLastDay(int month, int year) {
		Calendar cal = Calendar.getInstance(Locale.FRANCE);
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.DAY_OF_MONTH, 0);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return sqliteFormat.format(cal.getTime());
	}
}
