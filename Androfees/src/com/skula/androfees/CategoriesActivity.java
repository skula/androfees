package com.skula.androfees;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.skula.androfees.services.DatabaseService;



public class CategoriesActivity extends Activity {
	private ListView catList;
	private DatabaseService dbService;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.categories_layout);
		
		this.dbService = new DatabaseService(this);
		
		this.catList = (ListView) findViewById(R.id.cats_list);
		catList.setOnItemLongClickListener(new OnItemLongClickListener() {
			@SuppressWarnings("unchecked")
			@Override
			public boolean onItemLongClick(AdapterView<?> a, View v, int position, long id2) {
				
				return true;
			}
		});
		
		TextView btnAdd = (TextView) findViewById(R.id.cats_btnAdd);
		btnAdd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
		
		update();
	}
	
	public void update(){
		SimpleAdapter mSchedule = new SimpleAdapter(this.getBaseContext(),
				dbService.getCategoriesMap(), R.layout.category_layout,
				new String[] { "label", "id" }, new int[] {
						R.id.cat_label, R.id.cat_id });
		catList.setAdapter(mSchedule);
	}
}
