package com.example.socketclient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	private String URL="192.168.2.106";
	private Socket client;
	private PrintWriter printwriter;
	private EditText textField_tip;
	private EditText textField_valoare;
	private EditText textField_status;
	private String tip;
	private String valoare;
	private String status;
	private Button button;
	private Button button_clear;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		button = (Button) findViewById(R.id.connect);
		button_clear = (Button) findViewById(R.id.clear);
		textField_tip = (EditText) findViewById(R.id.tip);
		textField_valoare = (EditText) findViewById(R.id.valoare);
		textField_status = (EditText) findViewById(R.id.status);

		button_clear.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				textField_tip.setText("");
				textField_valoare.setText("");
				textField_status.setText("");
			}
		});

		button.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				SendMessage sendMessageTask = new SendMessage();
				sendMessageTask.execute();
			}
		});

	}

	private class SendMessage extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {

			try {

				tip = textField_tip.getText().toString();
				valoare = textField_valoare.getText().toString();
				status = textField_status.getText().toString();

				String senzor = "tip," + tip + ",valoare," + valoare
						+ ",status," + status;

				client = new Socket(URL, 6060);
				printwriter = new PrintWriter(client.getOutputStream(), true);
				printwriter.write(senzor.toString());

				printwriter.flush();
				printwriter.close();
				client.close();

			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

	}

}