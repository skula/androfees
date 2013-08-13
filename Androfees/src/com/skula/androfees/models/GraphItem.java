package com.skula.androfees.models;

import java.util.Calendar;

public class GraphItem {
	private int day;
	private int month;
	private int year;
	private double value;

	public static void main(String[] args) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 2);
		cal.set(Calendar.MONTH, 4);
		GraphItem g = new GraphItem(cal, 0.0);
		System.out.println(g.getDay());
		System.out.println(g.getMonth());
		System.out.println(g.getYear());

		Calendar cal2 = Calendar.getInstance();
		cal2.set(Calendar.DAY_OF_MONTH, 2);
		cal2.set(Calendar.MONTH, 4);

		System.out.println(cal == cal2);
	}

	public GraphItem() {
	}

	public GraphItem(Calendar cal, double value) {
		this.day = cal.get(Calendar.DAY_OF_MONTH);
		this.month = cal.get(Calendar.MONTH)+1;
		this.year = cal.get(Calendar.YEAR);
		this.value = value;
	}

	public GraphItem(int day, int month, int year, double value) {
		this.day = day;
		this.month = month;
		this.year = year;
		this.value = value;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return day + "/" + month + "/" + year+": "+value;
	}

	public boolean equals(GraphItem gi) {
		return day == gi.getDay() && month == gi.getMonth() && year == gi.getYear();
	}

	public boolean equals(Calendar cal) {
		return day == cal.get(Calendar.DAY_OF_MONTH) && month == cal.get(Calendar.MONTH)+1 && year == cal.get(Calendar.YEAR);
	}
}
