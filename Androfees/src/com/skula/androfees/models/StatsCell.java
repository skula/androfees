package com.skula.androfees.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatsCell {
	private double value;
	private double percent;
	private boolean increase;

	public StatsCell() {
	}

	public void setValue(double value) {
		this.value = value;
	}

	public double getValue() {
		return value;
	}

	public void setPercent(double value) {
		this.percent = value;
	}

	public double getPercent() {
		return percent;
	}

	public void setIncrease(boolean increase) {
		this.increase = increase;
	}
	
	public boolean isIncrease() {
		return increase;
	}
}