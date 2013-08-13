package com.skula.androfees.models;

import java.util.List;
import java.util.Map;

public class FeeList {
	private String amount;
	private Map<String,List<Fee>> feeMap;

	public FeeList() {
	}

	public FeeList(String amount, Map<String,List<Fee>> feeMap) {
		this.feeMap = feeMap;
		this.amount = amount;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public Map<String,List<Fee>> getFeeMap() {
		return feeMap;
	}

	public void setFeeMap(Map<String,List<Fee>> feeMap) {
		this.feeMap = feeMap;
	}
}
