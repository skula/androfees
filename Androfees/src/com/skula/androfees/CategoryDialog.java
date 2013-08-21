package com.skula.androfees;

import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.skula.androfees.definitions.Definitions;
import com.skula.androfees.models.Fee;
import com.skula.androfees.services.DatabaseService;

public class CategoryDialog extends Dialog {
	private Button btn1;
	private Button btn2;
	private Button btn3;

	private EditText labelTxt;
	
	private int mode;
	private String label;
	private DatabaseService dbService;

	public CategoryDialog(final Context context,
			final CategoriesActivity mainActivity, int mode, String label) {
		super(context);
		this.setContentView(R.layout.cat_dialog_layout);
		
		this.mode = mode;
		this.label = label;
		this.dbService = new DatabaseService(context);
		
		this.btn1 = (Button) findViewById(R.id.cat_dial_btn1);
		this.btn2 = (Button) findViewById(R.id.cat_dial_btn2);
		this.btn3 = (Button) findViewById(R.id.cat_dial_btn3);
		
		this.labelTxt = (EditText) findViewById(R.id.cat_dial_label);

		if (mode == Definitions.MODE_MOD) {
			setTitle("Modification de la catégorie");
			labelTxt.setText(label);
		} else {
			setTitle("Ajout d'une catégorie");
		}
		
		btn1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (getMode() == Definitions.MODE_CRE) {
					insert();
					mainActivity.update();
				} else {
					handleUpdate(context, mainActivity);
				}
				dismiss();
			}
		});

		btn2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				handleDelete(context, mainActivity);
				dismiss();
			}
		});

		btn3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dismiss();
			}
		});
		
		handleMode();
	}

	private void handleDelete(Context ctx, final CategoriesActivity mainActivity) {
		AlertDialog.Builder helpBuilder = new AlertDialog.Builder(ctx);
		helpBuilder.setTitle("Confirmation");
		helpBuilder
				.setMessage("Etes-vous sûr de vouloir supprimer cette catégorie ?");
		final Context tmpCtx = ctx;
		helpBuilder.setPositiveButton("Oui",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						delete();
						mainActivity.update();
					}
				});
		helpBuilder.setNegativeButton("Non",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				});
		AlertDialog helpDialog = helpBuilder.create();
		helpDialog.show();
	}

	private void handleUpdate(Context ctx, final CategoriesActivity mainActivity) {
		AlertDialog.Builder helpBuilder = new AlertDialog.Builder(ctx);
		helpBuilder.setTitle("Confirmation");
		helpBuilder
				.setMessage("Etes-vous sûr de vouloir modifier cette catégorie ?");
		final Context tmpCtx = ctx;
		helpBuilder.setPositiveButton("Oui",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						update();
						mainActivity.update();
					}
				});
		helpBuilder.setNegativeButton("Non",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				});
		AlertDialog helpDialog = helpBuilder.create();
		helpDialog.show();
	}

	private void handleMode() {
		switch (mode) {
		case Definitions.MODE_CRE:
			btn1.setVisibility(View.VISIBLE);
			btn1.setText("Créer");
			btn2.setVisibility(View.GONE);
			btn3.setVisibility(View.VISIBLE);
			btn3.setText("Annuler");
			break;
		case Definitions.MODE_MOD:
			btn1.setVisibility(View.VISIBLE);
			btn1.setText("Modifier");
			btn2.setVisibility(View.VISIBLE);
			btn2.setText("Supprimer");
			btn3.setVisibility(View.VISIBLE);
			btn3.setText("Annuler");
			break;
		default:
			break;
		}
	}
	
	private void insert() {
		String newLabel = labelTxt.getText().toString();
		if(!newLabel.equals("")){
			dbService.insertCategory(labelTxt.getText().toString());
		}
	}

	private void update() {
		String newLabel = labelTxt.getText().toString();
		if(!newLabel.equals("")){
			dbService.updateCategory(label, labelTxt.getText().toString());
		}
	}

	private void delete() {
		dbService.deleteCategory(label);
	}

	public int getMode() {
		return mode;
	}
}
