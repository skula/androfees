package com.skula.androfees.models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.skula.androfees.utils.CalendarUtils;

public class StatsTable {
	private static SimpleDateFormat dtfr = new SimpleDateFormat("dd/MM");

	private String title;
	private List<StatsRow> body;
	private List<String> header;
	private StatsRow footer;

	public StatsTable(String unit, int count, Map<String, Double[]> map) {
		/** TITLE **/
		this.setTitle(unit, count);

		/** HEADER **/
		this.setHeader(unit, count);

		/** BODY **/
		this.body = new ArrayList<StatsRow>();
		// row.cells[i].value && row.sum && row.label
		double sum = 0.0;
		StatsRow tmp = null;
		for (String cat : map.keySet()) {
			Double[] vals = map.get(cat);
			tmp = new StatsRow(cat, vals);
			sum += tmp.getSum();
			body.add(tmp);
		}

		// row.percent
		Double[] tot = new Double[body.get(0).getCells().length];
		for (int i = 0; i < tot.length; i++) {
			tot[i] = new Double(0.0);
		}
		for (StatsRow row : body) {
			row.setPercent((row.getSum() / sum) * 100);
			for (int i = 0; i < row.getCells().length; i++) {
				tot[i] += row.getCells()[i].getValue();
			}
		}

		// row.cells[i].percent && row.cells[i].increase
		double last = -1.0;
		for (StatsRow row : body) {
			for (int i = 0; i < row.getCells().length; i++) {
				row.getCells()[i]
						.setPercent((row.getCells()[i].getValue() / tot[i]) * 100);
				boolean a = last < row.getCells()[i].getValue();
				row.getCells()[i].setIncrease(a);
				last = row.getCells()[i].getValue();
			}
		}

		/** FOOTER **/
		this.footer = new StatsRow("TOTAL", tot);
		footer.setPercent(100.0);
	}

	private void setTitle(String unit, int count) {
		String tmp = unit.equals("Semaines") ? "dernières" : "derniers";
		this.title = "Tableau de statistiques sur les " + count + " " + tmp
				+ " " + unit.toLowerCase() + ".";
	}

	private void setHeader(String unit, int count) {
		if (unit.equals("Jours")) {
			this.header = setHeaderDay(count);
		} else if (unit.equals("Semaines")) {
			this.header = setHeaderWeek(count);
		} else {
			this.header = setHeaderMonth(count);
		}
	}

	private List<String> setHeaderDay(int count) {
		List<String> res = new ArrayList<String>();

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -1 * (count - 1));
		Date d = cal.getTime();
		res.add(dtfr.format(d));
		cal.add(Calendar.DAY_OF_YEAR, 1);
		for (int i = 0; i < count-1; i++) {
			d = cal.getTime();
			res.add(dtfr.format(d));
			cal.add(Calendar.DAY_OF_YEAR, 1);
		}

		return res;
	}

	private List<String> setHeaderWeek(int count) {
		List<String> res = new ArrayList<String>();

		Calendar cal = Calendar.getInstance();

		return res;
	}

	private List<String> setHeaderMonth(int count) {
		List<String> res = new ArrayList<String>();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1 * (count - 1));
		String tmp = CalendarUtils.getMonthLabel(cal.get(Calendar.MONTH));
		res.add(tmp.substring(0, 3).toLowerCase());
		for (int i = 0; i < count-1; i++) {
			tmp = CalendarUtils.getMonthLabel(cal.get(Calendar.MONTH));
			res.add(tmp.substring(0, 3).toLowerCase());
			cal.add(Calendar.MONTH, 1);
		}
		return res;
	}

	public List<StatsRow> getBody() {
		return body;
	}

	public void setBody(List<StatsRow> body) {
		this.body = body;
	}

	public List<String> getHeader() {
		return header;
	}

	public void setHeader(List<String> header) {
		this.header = header;
	}

	public StatsRow getFooter() {
		return footer;
	}

	public void setFooter(StatsRow footer) {
		this.footer = footer;
	}
}