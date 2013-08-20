package com.skula.androfees;

import java.util.HashMap;
import java.util.Map;

import com.skula.androfees.models.*;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class StatsActivity extends Activity {

	private TableLayout table;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stats_layout);

		this.table = (TableLayout) findViewById(R.id.table);
		LayoutInflater inflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		Map<String, Double[]> map = new HashMap<String, Double[]>();
		map.put("Courses", new Double[] { 1.0, 2.0, 3.0 });
		map.put("Sorties", new Double[] { 10.0, 20.0, 30.0 });

		// headers

		// categories
		TextView t1 = null;
		for (StatsRow r : StatsTable.getRows(map)) {
			TableRow row = new TableRow(this);

			// titre categorie
			t1 = new TextView(this);
			t1.setText("Titre");
			row.addView(t1);

			// sum & percent
			View v2 = inflater.inflate(R.layout.cell_layout, null);
			TextView sum = (TextView) v2.findViewById(R.id.value);
			sum.setText(castDouble(r.getSum())+"");
			TextView percent = (TextView) v2.findViewById(R.id.percent);
			percent.setText(castDouble(r.getPercent())+"");

			row.addView(v2);
			View v = null;
			// cellules
			for (StatsCell c : r.getCells()) {
				v = inflater.inflate(R.layout.cell_layout, null);
				TextView value = (TextView) v.findViewById(R.id.value);
				value.setText(castDouble(c.getValue())+"");
				TextView TextView = (TextView) v.findViewById(R.id.percent);
				TextView.setText(castDouble(c.getPercent())+"");
				TextView increase = (TextView) v.findViewById(R.id.increase);
				increase.setText(c.isIncrease()+"");
				row.addView(v);
			}

			table.addView(row);
		}
	}
	
	private static double castDouble(double in){
		return (int) (in*100)/100.0;
	}
}
