package com.skula.androfees.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatsTable {
	private String title;
	private List<String> headers;
	private List<StatsRow> rows;
	private StatsRow footer;

	public static List<StatsRow> getRows(Map<String, Double[]> map){
		List<StatsRow> rows = new ArrayList<StatsRow>();
		// row.cells[i].value && row.sum && row.label
		double sum = 0.0;
		StatsRow tmp = null;
		for (String cat : map.keySet()) {
			Double[] vals = map.get(cat);
			tmp = new StatsRow(cat, vals);
			sum += tmp.getSum();
			rows.add(tmp);
		}

		// row.percent
		double[] tot = new double[rows.get(0).getCells().length];
		for (StatsRow row : rows) {
			row.setPercent((row.getSum() / sum) * 100);
			for (int i = 0; i < row.getCells().length; i++) {
				tot[i] += row.getCells()[i].getValue();
			}
		}

		// row.cells[i].percent && row.cells[i].increase
		double last = -1.0;
		for (StatsRow row : rows) {
			for (int i = 0; i < row.getCells().length; i++) {
				row.getCells()[i]
						.setPercent((row.getCells()[i].getValue() / tot[i]) * 100);
				boolean a = last < row.getCells()[i].getValue();
				row.getCells()[i].setIncrease(a);
				last = row.getCells()[i].getValue();
			}
		}
		return rows;
	}
}