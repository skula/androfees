package com.skula.androfees;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
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
		List<String> unitList = new ArrayList<String>();
		unitList.add("Jours");
		unitList.add("Semaines");
		unitList.add("Mois");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item,
				unitList);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		units.setAdapter(adapter);		
		this.unitCount = (EditText) findViewById(R.id.stats_count);	
		unitCount.setText("15");
		this.table = (TableLayout) findViewById(R.id.stats_table);

		TextView btnSearch = (TextView) findViewById(R.id.stats_btnSearch);
		btnSearch.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String u = (String) units.getSelectedItem();
				String c = unitCount.getText().toString();
				if(!c.equals("")){
					LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					List<TableRow> list = StatsService.calculate(v.getContext(), inflater, u, Integer.valueOf(c));
					for(TableRow tr : list){
						table.addView(tr);
					}					
				}
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.stats_activity, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent myIntent = null;
		switch (item.getItemId()) {
		case R.id.menu_list:
			myIntent = new Intent(this, FeeListActivity.class);
			startActivityForResult(myIntent, 0);
			return true;
		case R.id.menu_graph:
			myIntent = new Intent(this, GraphActivity.class);
			startActivityForResult(myIntent, 0);
			return true;
		case R.id.menu_cats:
			myIntent = new Intent(this, CategoriesActivity.class);
			startActivityForResult(myIntent, 0);
			return true;
		default:
			return false;
		}
	}
}
