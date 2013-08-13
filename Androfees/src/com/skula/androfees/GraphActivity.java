package com.skula.androfees;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.skula.androfees.definitions.Definitions;

public class GraphActivity extends Activity {
	private GraphView graphView;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		graphView = new GraphView(this);
		setContentView(graphView);
		
		graphView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				LayoutInflater li = LayoutInflater.from(v.getContext());
				View promptsView = li.inflate(R.layout.criteriaprompt, null);
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
				alertDialogBuilder.setTitle("Critères");
				alertDialogBuilder.setView(promptsView);
				
				final Spinner graphType = (Spinner) promptsView.findViewById(R.id.typeGraph);
				final EditText countPeriod = (EditText) promptsView.findViewById(R.id.countPeriod);
				final Spinner typePeriod = (Spinner) promptsView.findViewById(R.id.typePeriod);
				final Spinner timeUnit = (Spinner) promptsView.findViewById(R.id.timeUnit);

				alertDialogBuilder.setCancelable(false).setPositiveButton("Générer", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						String graphTypeStr = (String)graphType.getSelectedItem();
						String countPeriodStr = countPeriod.getText().toString();
						String typePeriodStr = (String)typePeriod.getSelectedItem();
						String timeUnitStr = (String)timeUnit.getSelectedItem();	

						int graph = Definitions.getCode(graphTypeStr);
						int periodUnité = Definitions.getCode(typePeriodStr);
						int unité = Definitions.getCode(timeUnitStr);
					}
				}).setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});

				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
			}
		});
	}
}
