package com.skula.androfees.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.skula.androfees.R;
import com.skula.androfees.models.StatsCell;
import com.skula.androfees.models.StatsRow;
import com.skula.androfees.models.StatsTable;

public class StatsService {
	
	public static List<TableRow> calculate(Context ctx, LayoutInflater infl, String unit, int count){
		List<TableRow> res = new ArrayList<TableRow>();

		// bouchon
		Map<String, Double[]> map = new HashMap<String, Double[]>();
		map.put("Courses", new Double[] { 1.0, 2000.0, 3.0, 4.0, 5.0,1.0, 2.0, 3.0, 4.0, 5.0, 1.0, 2.0, 3.0, 4.0, 5.0 });
		map.put("Sorties", new Double[] { 10.0, 20.0, 30.0, 40.0, 50.0, 1.0, 2.0, 3.0, 4.0, 5.0, 1.0, 2.0, 3.0, 4.0, 5.0 });	
		map.put("Bar", new Double[] { 10.0, 20.0, 30.0, 40.0, 50.0, 1.0, 2.0, 3.0, 4.0, 5.0, 1.0, 2.0, 3.0, 4.0, 5.0 });	
		map.put("Autre", new Double[] { 10.0, 20.0, 30.0, 40.0, 50.0, 1.0, 2.0, 3.0, 4.0, 5.0, 1.0, 2.0, 3.0, 4.0, 5.0 });	
		map.put("Egarette", new Double[] { 10.0, 20.0, 30.0, 40.0, 50.0, 1.0, 2.0, 3.0, 4.0, 5.0, 1.0, 2.0, 3.0, 4.0, 5.0 });	
		map.put("Plop", new Double[] { 10.0, 20.0, 30.0, 40.0, 50.0, 1.0, 2.0, 3.0, 4.0, 5.0, 1.0, 2.0, 3.0, 4.0, 5.0 });	
		map.put("LOL", new Double[] { 10.0, 20.0, 30.0, 40.0, 50.0, 1.0, 2.0, 3.0, 4.0, 5.0, 1.0, 2.0, 3.0, 4.0, 5.0 });	
		map.put("Prout", new Double[] { 10.0, 20.0, 30.0, 40.0, 50.0, 1.0, 2.0, 3.0, 4.0, 5.0, 1.0, 2.0, 3.0, 4.0, 5.0 });	
		StatsTable st = new StatsTable(unit, count, map);
		
		// header
		res.add(buildHeader(ctx, infl, st.getHeader()));
		
		// body
		for(TableRow tr : buildBody(ctx, infl, st.getBody())){
			res.add(tr);
		}
		
		// footer
		res.add(buildFooter(ctx, infl, st.getFooter()));
		
		return res;
	}
	
	private static TableRow buildHeader(Context ctx, LayoutInflater inflater, List<String> headers){
		TableRow res = new TableRow(ctx);
		res.addView(new TextView(ctx));
		res.addView(new TextView(ctx));
		
		for(String h: headers){
			TextView t2 = new TextView(ctx);
			t2.setText(h);
			t2.setGravity(Gravity.CENTER);
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
			t1.setGravity(Gravity.CENTER_VERTICAL);
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
				value.setText(castDouble(c.getValue())+"€");
				TextView TextView = (TextView) v.findViewById(R.id.percent);
				TextView.setText(castDouble(c.getPercent())+"%");
				TextView increase = (TextView) v.findViewById(R.id.increase);
				increase.setText(c.isIncrease()+"");
				tr.addView(v);
			}

			res.add(tr);
		}
		return res;
	}
	
	private static TableRow buildFooter(Context ctx, LayoutInflater inflater, StatsRow footer){
		TableRow res = new TableRow(ctx);	
		res.addView(new TextView(ctx));
		
		View v2 = inflater.inflate(R.layout.cell_layout, null);
		TextView sum = (TextView) v2.findViewById(R.id.value);
		sum.setText(castDouble(footer.getSum())+"");
		TextView percent = (TextView) v2.findViewById(R.id.percent);
		percent.setText(castDouble(footer.getPercent())+"");
		
		res.addView(v2);
		View v = null;
		// cellules
		for (StatsCell c : footer.getCells()) {
			v = inflater.inflate(R.layout.cell_layout, null);
			TextView value = (TextView) v.findViewById(R.id.value);
			value.setText(castDouble(c.getValue())+"€");
			TextView TextView = (TextView) v.findViewById(R.id.percent);
			TextView.setText(castDouble(c.getPercent())+"%");
			TextView increase = (TextView) v.findViewById(R.id.increase);
			increase.setText(c.isIncrease()+"");
			res.addView(v);
		}
		
		return res;
	}
	
	private static double castDouble(double in){
		return (int) (in*100)/100.0;
	}
}
