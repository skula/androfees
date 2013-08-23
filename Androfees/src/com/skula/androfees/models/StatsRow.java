package com.skula.androfees.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StatsRow {
	private String label;
	private double percent;
	private double sum;
	private StatsCell[] cells;

	public StatsRow() {
	}
	
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

	public StatsRow(String label, Double[] vals) {
		this.label = label;
		this.cells = new StatsCell[vals.length];

		this.sum = 0.0;
		for (int i = 0; i < vals.length; i++) {
			cells[i] = new StatsCell();
			cells[i].setValue(vals[i]);
			this.sum += vals[i];
		}
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setPercent(double value) {
		this.percent = value;
	}

	public double getPercent() {
		return percent;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}

	public double getSum() {
		return sum;
	}

	public StatsCell[] getCells() {
		return cells;
	}
}