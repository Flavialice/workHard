package com.example.socketclient;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
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

				/*
				 * textField_id.setText(""); textField_tip.setText("");
				 * textField_valoare.setText(""); textField_vazut.setText("");
				 * textField_status.setText("");
				 */

				tip = textField_tip.getText().toString();
				valoare = textField_valoare.getText().toString();
				status = textField_status.getText().toString();

				String senzor = "tip," + tip + ",valoare," + valoare
						+ ",status," + status;

				client = new Socket("192.168.2.106", 7070);
				printwriter = new PrintWriter(client.getOutputStream(), true);
				printwriter.write(senzor.toString());

				/*
				 * BufferedReader input = new BufferedReader(new
				 * InputStreamReader(client.getInputStream())); String answer =
				 * input.readLine();
				 */
				// textField.setText(answer);
				printwriter.flush();
				printwriter.close();
				client.close();
				// Toast t= Toast.makeText(getApplicationContext(),
				// "Connection closed", 1);
				// t.show();

			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

	}

}