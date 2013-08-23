package com.skula.androfees.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import com.skula.androfees.R;
import com.skula.androfees.models.StatsCell;
import com.skula.androfees.models.StatsRow;

public class StatsService {
	
	public static List<TableRow> calculate(Context ctx, LayoutInflater infl, String unit, int count){
		List<TableRow> res = new ArrayList<TableRow>();

		// bouchon
		Map<String, Double[]> map = new HashMap<String, Double[]>();
		map.put("Courses", new Double[] { 1.0, 2.0, 3.0 });
		map.put("Sorties", new Double[] { 10.0, 20.0, 30.0 });
		
		// headers
		res.add(buildHeader(ctx, infl, unit, count));
		
		// body
		List<StatsRow> sRows = StatsRow.getRows(map);
		for(TableRow tr : buildBody(ctx, infl, sRows)){
			res.add(tr);
		}
		
		// footer
		res.add(buildFooter(ctx, infl, sRows));
		
		return res;
	}
	
	private static TableRow buildHeader(Context ctx, LayoutInflater inflater, String unit, int count){
		TableRow res = new TableRow(ctx);
		res.addView(new TextView(ctx));
		res.addView(new TextView(ctx));
		
		for(int i=1; i<=count; i++){
			TextView t2 = new TextView(ctx);
			t2.setText("u"+i);
			res.addView(t2);
		}
		
		return res;
	}
	
	private static List<TableRow> buildBody(Context ctx, LayoutInflater inflater, List<StatsRow> sRows){
		List<TableRow> res = new ArrayList<TableRow>();

		TextView t1 = null;
		for (StatsRow sr : sRows) {
			TableRow tr = new TableRow(ctx);

			// titre categorie
			t1 = new TextView(ctx);
			t1.setText(sr.getLabel());
			tr.addView(t1);

			// sum & percent
			View v2 = inflater.inflate(R.layout.cell_layout, null);
			TextView sum = (TextView) v2.findViewById(R.id.value);
			sum.setText(castDouble(sr.getSum())+"");
			TextView percent = (TextView) v2.findViewById(R.id.percent);
			percent.setText(castDouble(sr.getPercent())+"");

			tr.addView(v2);
			View v = null;
			// cellules
			for (StatsCell c : sr.getCells()) {
				v = inflater.inflate(R.layout.cell_layout, null);
				TextView value = (TextView) v.findViewById(R.id.value);
				value.setText(castDouble(c.getValue())+"");
				TextView TextView = (TextView) v.findViewById(R.id.percent);
				TextView.setText(castDouble(c.getPercent())+"");
				TextView increase = (TextView) v.findViewById(R.id.increase);
				increase.setText(c.isIncrease()+"");
				tr.addView(v);
			}

			res.add(tr);
		}
		return res;
	}
	
	private static TableRow buildFooter(Context ctx, LayoutInflater inflater, List<StatsRow> sRows){
		TableRow res = new TableRow(ctx);	
		res.addView(new TextView(ctx));
		res.addView(new TextView(ctx));
		
		return res;
	}
	
	private static double castDouble(double in){
		return (int) (in*100)/100.0;
	}
}
