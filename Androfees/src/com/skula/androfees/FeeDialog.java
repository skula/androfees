package com.skula.androfees;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.skula.androfees.definitions.Definitions;
import com.skula.androfees.models.Fee;
import com.skula.androfees.services.DatabaseService;

public class FeeDialog extends Dialog {
	private Button btn1;
	private Button btn2;
	private Button btn3;

	private EditText labelTxt;
	private EditText amountTxt;
	private EditText dateTxt;
	private EditText locationTxt;
	
	private int mode;
	private int idItem;
	private DatabaseService dbService;

	public FeeDialog(final Context context,
			final FeeListActivity mainActivity, int mode, int idItem) {
		super(context);
		this.setContentView(R.layout.fee_dialog_layout);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		this.mode = mode;
		this.idItem = idItem;
		this.dbService = new DatabaseService(context);
		
		this.btn1 = (Button) findViewById(R.id.fee_dial_btn1);
		this.btn2 = (Button) findViewById(R.id.fee_dial_btn2);
		this.btn3 = (Button) findViewById(R.id.fee_dial_btn3);
		
		this.labelTxt = (EditText) findViewById(R.id.fee_dial_label);
		this.amountTxt = (EditText) findViewById(R.id.fee_dial_amount);
		this.dateTxt = (EditText) findViewById(R.id.fee_dial_date);
		this.locationTxt = (EditText) findViewById(R.id.fee_dial_location);

		/*this.catlist = dbService.getCategoriesLabel();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				this.getContext(), android.R.layout.simple_spinner_item,
				catlist);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		categorySpn.setAdapter(adapter);*/		

		if (mode == Definitions.MODE_MOD) {
			setTitle("Modification de l'article");
			
			Fee fee = dbService.getFee(idItem);
			labelTxt.setText(fee.getLabel());
			amountTxt.setText(fee.getAmount());
			dateTxt.setText(fee.getDate());
			locationTxt.setText(fee.getLocation());
		} else {
			setTitle("Ajout d'un article");
		}
		
		btn1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (getMode() == Definitions.MODE_CRE) {
					insert();
					mainActivity.update();
				} else {
					//handleUpdate(context, mainActivity);
				}
				dismiss();
			}
		});

		btn2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//handleDelete(context, mainActivity);
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

	private void handleDelete(Context ctx, final MainActivity mainActivity) {
		AlertDialog.Builder helpBuilder = new AlertDialog.Builder(ctx);
		helpBuilder.setTitle("Confirmation");
		helpBuilder
				.setMessage("Etes-vous sûr de vouloir supprimer cet élément ?");
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

	private void handleUpdate(Context ctx, final MainActivity mainActivity) {
		AlertDialog.Builder helpBuilder = new AlertDialog.Builder(ctx);
		helpBuilder.setTitle("Confirmation");
		helpBuilder
				.setMessage("Etes-vous sûr de vouloir modifier cet élément ?");
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
	
	public void insert() {
		/*String cat = ((String) categorySpn.getSelectedItem());
		dbService.insertArticle(idParent, labelTxt.getText().toString(),
				quantityTxt.getText().toString(), cat);*/
	}

	public void update() {
		/*String cat = ((String) categorySpn.getSelectedItem());
		dbService.updateArticle(idItem, labelTxt.getText().toString(),
				quantityTxt.getText().toString(), cat);*/
	}

	public void delete() {
		//dbService.deleteArticle(idItem);
	}

	public int getMode() {
		return mode;
	}
}
