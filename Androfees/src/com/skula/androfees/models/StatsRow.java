package com.skula.androfees.models;

public class StatsRow {
	private String label;
	private double percent;
	private double sum;
	private StatsCell[] cells;

	public StatsRow() {
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