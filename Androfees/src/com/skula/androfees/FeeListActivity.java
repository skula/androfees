package com.skula.androfees;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.skula.androfees.definitions.Definitions;
import com.skula.androfees.models.Fee;
import com.skula.androfees.models.FeeList;
import com.skula.androfees.models.GraphItem;
import com.skula.androfees.services.DatabaseService;
import com.skula.androfees.utils.CalendarUtils;

public class FeeListActivity extends Activity {
	private ListView feeList;
	private int month;
	private int year;
	private DatabaseService dbService;
	//private Button btnAdd;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.list_layout);
		
		final FeeListActivity ma = this;
		
		this.dbService = new DatabaseService(this);
		dbService.bouchon();
		
		this.month = 10;
		this.year = 2012;

		View previousMonth = (View) findViewById(R.id.list_previousMonth);
		previousMonth.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				goPreviousMonth();
			}
		});

		View nextMonth = (View) findViewById(R.id.list_nextMonth);
		nextMonth.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				goNextMonth();
			}
		});

		this.feeList = (ListView) findViewById(R.id.list_feelist);
		feeList.setOnItemLongClickListener(new OnItemLongClickListener() {
			@SuppressWarnings("unchecked")
			@Override
			public boolean onItemLongClick(AdapterView<?> a, View v, int position, long id2) {
				FeeDialog feeDial = new FeeDialog(v.getContext(), ma,
							Definitions.MODE_MOD, "1");
				feeDial.show();
				return true;
			}
		});
		
		/*this.btnAdd = (Button) findViewById(R.id.btnAdd);
		btnAdd.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
			}
		});*/
		
		update();
	}

	private String getDateFormat() {
		return CalendarUtils.getMonthLabel(month) + " " + year;
	}

	private void goNextMonth() {
		Calendar cal = CalendarUtils.getNextMonth(month, year);
		month = cal.get(Calendar.MONTH) + 1;
		year = cal.get(Calendar.YEAR);
		update();
	}

	private void goPreviousMonth() {
		Calendar cal = CalendarUtils.getPreviousMonth(month, year);
		month = cal.get(Calendar.MONTH) + 1;
		year = cal.get(Calendar.YEAR);
		update();
	}

	public void update() {
		// update list
		String dateFrom = CalendarUtils.getFirstDay(month, year);
		String dateTo = CalendarUtils.getLastDay(month, year);
		FeeList list = dbService.getFees(dateFrom, dateTo);
		FeeAdapter adapter = new FeeAdapter(this, list.getFeeMap());
		feeList.setAdapter(adapter);

		// update date
		TextView month = (TextView) findViewById(R.id.list_month);
		month.setText(getDateFormat());

		// update total amount
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMinimumFractionDigits(2);
		TextView total = (TextView) findViewById(R.id.list_total);
		total.setText("Total: " + numberFormat.format(Double.valueOf(list.getAmount())) + " €");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.list_activity, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent myIntent = null;
		switch (item.getItemId()) {
		case R.id.menu_graph:
			myIntent = new Intent(this, GraphActivity.class);
			startActivityForResult(myIntent, 0);
			return true;
		case R.id.menu_stats:
			myIntent = new Intent(this, StatsActivity.class);
			startActivityForResult(myIntent, 0);
			return true;
		default:
			return false;
		}
	}
}