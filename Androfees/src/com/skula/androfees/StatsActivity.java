package com.skula.androfees;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.skula.androfees.services.StatsService;

public class StatsActivity extends Activity {
	private Spinner units;
	private EditText unitCount;
	private TableLayout table;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stats_layout);

		this.units = (Spinner) findViewById(R.id.stats_unit);
		this.unitCount = (EditText) findViewById(R.id.stats_count);	
		this.table = (TableLayout) findViewById(R.id.stats_table);

		TextView btnSearch = (TextView) findViewById(R.id.stats_btnSearch);
		btnSearch.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String u = (String) units.getSelectedItem();
				String c = unitCount.getText().toString();
				//if(!c.equals("")){
					LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					List<TableRow> list = StatsService.calculate(v.getContext(), inflater, "", 3);//Integer.valueOf(c));
					for(TableRow tr : list){
						table.addView(tr);
					}					
				//}
			}
		});
	}
}
