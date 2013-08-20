package com.skula.androfees;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PasswordActivity extends Activity implements OnClickListener {
	private Button btnPw0;
	private Button btnPw1;
	private Button btnPw2;
	private Button btnPw3;
	private Button btnPw4;
	private Button btnPw5;
	private Button btnPw6;
	private Button btnPw7;
	private Button btnPw8;
	private Button btnPw9;
	private Button btnPwDel;

	private EditText valPw1;
	private EditText valPw2;
	private EditText valPw3;
	private EditText valPw4;

	private TextView pwError;

	private String passwd;
	private int number;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.passwd_layout);

		this.passwd = "1789";
		this.number = 1;

		this.valPw1 = (EditText) findViewById(R.id.valPw1);
		this.valPw2 = (EditText) findViewById(R.id.valPw2);
		this.valPw3 = (EditText) findViewById(R.id.valPw3);
		this.valPw4 = (EditText) findViewById(R.id.valPw4);

		this.pwError = (TextView) findViewById(R.id.pwError);

		this.btnPw0 = (Button) findViewById(R.id.btnPw0);
		btnPw0.setOnClickListener(this);
		this.btnPw1 = (Button) findViewById(R.id.btnPw1);
		btnPw1.setOnClickListener(this);
		this.btnPw2 = (Button) findViewById(R.id.btnPw2);
		btnPw2.setOnClickListener(this);
		this.btnPw3 = (Button) findViewById(R.id.btnPw3);
		btnPw3.setOnClickListener(this);
		this.btnPw4 = (Button) findViewById(R.id.btnPw4);
		btnPw4.setOnClickListener(this);
		this.btnPw5 = (Button) findViewById(R.id.btnPw5);
		btnPw5.setOnClickListener(this);
		this.btnPw6 = (Button) findViewById(R.id.btnPw6);
		btnPw6.setOnClickListener(this);
		this.btnPw7 = (Button) findViewById(R.id.btnPw7);
		btnPw7.setOnClickListener(this);
		this.btnPw8 = (Button) findViewById(R.id.btnPw8);
		btnPw8.setOnClickListener(this);
		this.btnPw9 = (Button) findViewById(R.id.btnPw9);
		btnPw9.setOnClickListener(this);
		this.btnPwDel = (Button) findViewById(R.id.btnPwDel);
		btnPwDel.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnPw0:
			addValue("0");
			break;
		case R.id.btnPw1:
			addValue("1");
			break;
		case R.id.btnPw2:
			addValue("2");
			break;
		case R.id.btnPw3:
			addValue("3");
			break;
		case R.id.btnPw4:
			addValue("4");
			break;
		case R.id.btnPw5:
			addValue("3");
			break;
		case R.id.btnPw6:
			addValue("6");
			break;
		case R.id.btnPw7:
			addValue("7");
			break;
		case R.id.btnPw8:
			addValue("8");
			break;
		case R.id.btnPw9:
			addValue("9");
			break;
		case R.id.btnPwDel:
			deleteLastValue();
			break;
		default:
			break;
		}
		
		if (number == 2) {
			pwError.setText("");
		}

		if (number > 4) {
			if (passwd.equals(getEntry())) {
				pwError.setText("YOUHOU.");
			} else {
				pwError.setText("Code secret incorrecte.");
				number = 1;
				valPw1.setText("");
				valPw2.setText("");
				valPw3.setText("");
				valPw4.setText("");
			}
		}
	}

	private void addValue(String val) {
		switch (number) {
		case 1:
			valPw1.setText(val);
			number++;
			break;
		case 2:
			valPw2.setText(val);
			number++;
			break;
		case 3:
			valPw3.setText(val);
			number++;
			break;
		case 4:
			valPw4.setText(val);
			number++;
			break;
		default:
			break;
		}
	}

	private void deleteLastValue() {
		switch (number) {
		case 2:
			valPw1.setText("");
			number--;
			break;
		case 3:
			valPw2.setText("");
			number--;
			break;
		case 4:
			valPw3.setText("");
			number--;
			break;
		default:
			break;
		}
	}

	private String getEntry() {
		String res = "";
		res += valPw1.getText().toString();
		res += valPw2.getText().toString();
		res += valPw3.getText().toString();
		res += valPw4.getText().toString();
		return res;
	}
}
