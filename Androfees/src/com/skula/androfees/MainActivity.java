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

import com.skula.androfees.models.Fee;
import com.skula.androfees.models.FeeList;
import com.skula.androfees.models.GraphItem;
import com.skula.androfees.services.DatabaseService;
import com.skula.androfees.utils.CalendarUtils;

public class MainActivity extends Activity {
	private ListView feeList;
	private int month;
	private int year;
	private DatabaseService dbService;
	Button btnAdd;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.activity_main);

		this.dbService = new DatabaseService(this);
		
		 dbService.bouchon();
		
		 List<GraphItem> graphVarTot= dbService.graphVarTot("2012-10-27",Calendar.DAY_OF_MONTH);
		
		this.month = 10;
		this.year = 2012;

		EditText label = (EditText) findViewById(R.id.cremodLabel);
		label.setText("flskf");
		EditText date = (EditText) findViewById(R.id.cremodDate);
		date.setText("10/10/2012");
		EditText amount = (EditText) findViewById(R.id.cremodAmount);
		amount.setText("2.20");

		EditText location = (EditText) findViewById(R.id.cremodLocation);
		location.setText("fklklk");

		TextView month = (TextView) findViewById(R.id.month);
		month.setText(getDateFormat());
		feeList = (ListView) findViewById(R.id.feelist);
		update();
		fillSpinners();

		View previousMonth = (View) findViewById(R.id.previousMonth);
		previousMonth.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				goPreviousMonth();
			}
		});

		View nextMonth = (View) findViewById(R.id.nextMonth);
		nextMonth.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				goNextMonth();
			}
		});

		btnAdd = (Button) findViewById(R.id.btnAdd);
		btnAdd.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				EditText label = (EditText) findViewById(R.id.cremodLabel);
				EditText date = (EditText) findViewById(R.id.cremodDate);
				EditText amount = (EditText) findViewById(R.id.cremodAmount);
				Spinner category = (Spinner) findViewById(R.id.cremodCategory);
				EditText location = (EditText) findViewById(R.id.cremodLocation);

				Fee fee = new Fee();
				fee.setLabel(label.getText().toString());
				fee.setDate(date.getText().toString());
				fee.setAmount(amount.getText().toString());
				fee.setCategory((String) category.getSelectedItem());
				fee.setLocation(location.getText().toString());

				dbService.insertFee(fee);
				update();
			}
		});

		Button btnDelCategory = (Button) findViewById(R.id.btnDelCategory);
		btnDelCategory.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Spinner categoryToDel = (Spinner) findViewById(R.id.categoryToDel);
				final String label = (String) categoryToDel.getSelectedItem();
				AlertDialog.Builder helpBuilder = new AlertDialog.Builder(v.getContext());
				helpBuilder.setTitle("Suppression");
				helpBuilder.setMessage("Etes-vous sûr de vouloir supprimer '" + label + "' ?");
				helpBuilder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dbService.deleteCategory(label);
						fillSpinners();
						update();
					}
				});
				helpBuilder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				});
				AlertDialog helpDialog = helpBuilder.create();
				helpDialog.show();
			}
		});

		Button btnAddCategory = (Button) findViewById(R.id.btnAddCategory);
		btnAddCategory.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText categoryToAdd = (EditText) findViewById(R.id.categoryToAdd);
				dbService.insertCategory(categoryToAdd.getText().toString());
				fillSpinners();
				categoryToAdd.setText("");
			}
		});

		Button btnClear = (Button) findViewById(R.id.btnClear);
		btnClear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText id = (EditText) findViewById(R.id.cremodId);
				id.setText("");
				EditText label = (EditText) findViewById(R.id.cremodLabel);
				label.setText("");
				EditText date = (EditText) findViewById(R.id.cremodDate);
				date.setText("");
				EditText amount = (EditText) findViewById(R.id.cremodAmount);
				amount.setText("");
				EditText location = (EditText) findViewById(R.id.cremodLocation);
				location.setText("");

				Button btnClear = (Button) findViewById(R.id.btnClear);
				btnClear.setVisibility(View.GONE);

				Button btnMod = (Button) findViewById(R.id.btnMod);
				btnMod.setVisibility(View.GONE);

				Button btnAdd = (Button) findViewById(R.id.btnAdd);
				btnAdd.setVisibility(View.VISIBLE);
			}
		});

		Button btnMod = (Button) findViewById(R.id.btnMod);
		btnMod.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText id = (EditText) findViewById(R.id.cremodId);
				EditText label = (EditText) findViewById(R.id.cremodLabel);
				EditText date = (EditText) findViewById(R.id.cremodDate);
				EditText amount = (EditText) findViewById(R.id.cremodAmount);
				Spinner category = (Spinner) findViewById(R.id.cremodCategory);
				EditText location = (EditText) findViewById(R.id.cremodLocation);

				Fee fee = new Fee();
				fee.setLabel(label.getText().toString());
				fee.setDate(date.getText().toString());
				fee.setAmount(amount.getText().toString());
				fee.setCategory((String) category.getSelectedItem());
				fee.setLocation(location.getText().toString());

				dbService.updateFee(id.getText().toString(), fee);
				update();

				Button btnClear = (Button) findViewById(R.id.btnClear);
				btnClear.setVisibility(View.GONE);

				Button btnMod = (Button) findViewById(R.id.btnMod);
				btnMod.setVisibility(View.GONE);

				Button btnAdd = (Button) findViewById(R.id.btnAdd);
				btnAdd.setVisibility(View.VISIBLE);
			}
		});

		feeList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View v, int position, long id2) {
				@SuppressWarnings("unchecked")
				HashMap<String, String> map = (HashMap<String, String>) feeList.getItemAtPosition(position);
				String feeId = map.get("id");
				String feeLabel = map.get("label");
				String feeCategory = map.get("category");
				String feeAmount = map.get("amount");
				String feeLocation = map.get("location");
				String feeDate = map.get("date");

				EditText id = (EditText) findViewById(R.id.cremodId);
				id.setText(feeId);
				EditText label = (EditText) findViewById(R.id.cremodLabel);
				label.setText(feeLabel);
				EditText date = (EditText) findViewById(R.id.cremodDate);
				date.setText(feeDate);
				EditText amount = (EditText) findViewById(R.id.cremodAmount);
				amount.setText(feeAmount);
				Spinner category = (Spinner) findViewById(R.id.cremodCategory);
				EditText location = (EditText) findViewById(R.id.cremodLocation);
				location.setText(feeLocation);

				Button btnClear = (Button) findViewById(R.id.btnClear);
				btnClear.setVisibility(View.VISIBLE);

				Button btnMod = (Button) findViewById(R.id.btnMod);
				btnMod.setVisibility(View.VISIBLE);

				Button btnAdd = (Button) findViewById(R.id.btnAdd);
				btnAdd.setVisibility(View.GONE);
			}
		});

		feeList.setOnItemLongClickListener(new OnItemLongClickListener() {
			@SuppressWarnings("unchecked")
			@Override
			public boolean onItemLongClick(AdapterView<?> a, View v, int position, long id2) {
				HashMap<String, String> map = (HashMap<String, String>) feeList.getItemAtPosition(position);
				final String id = map.get("id");

				AlertDialog.Builder helpBuilder = new AlertDialog.Builder(v.getContext());
				helpBuilder.setTitle("Suppression");
				helpBuilder.setMessage("Voulez-vous supprimer cette dépense?");
				helpBuilder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dbService.deleteFee(id);
						update();
					}
				});
				helpBuilder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				});
				AlertDialog helpDialog = helpBuilder.create();
				helpDialog.show();

				return true;
			}
		});
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

	private void update() {
		// update list
		String dateFrom = CalendarUtils.getFirstDay(month, year);
		String dateTo = CalendarUtils.getLastDay(month, year);
		FeeList list = dbService.getFees(dateFrom, dateTo);
		FeeAdapter adapter = new FeeAdapter(this, list.getFeeMap());
		feeList.setAdapter(adapter);

		// update date
		TextView month = (TextView) findViewById(R.id.month);
		month.setText(getDateFormat());

		// update total amount
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMinimumFractionDigits(2);
		TextView total = (TextView) findViewById(R.id.total);
		total.setText("TOTAL: " + numberFormat.format(Double.valueOf(list.getAmount())) + " Euros");
	}

	private void fillSpinners() {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dbService.getCategoriesLabel());
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		Spinner category = (Spinner) findViewById(R.id.cremodCategory);
		category.setAdapter(adapter);
		Spinner categoryToDel = (Spinner) findViewById(R.id.categoryToDel);
		categoryToDel.setAdapter(adapter);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent myIntent = null;
		switch (item.getItemId()) {
		case R.id.menu_graph:
			myIntent = new Intent(this, GraphActivity.class);
			startActivityForResult(myIntent, 0);
			return true;
		default:
			return false;
		}
	}
}