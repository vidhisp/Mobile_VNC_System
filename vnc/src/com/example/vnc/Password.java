package com.example.vnc;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
public class Password extends Activity{

	Button submit;
	EditText pass;
	String data;
	Socket soc;
	public Password(Socket soc)
	{
		this.soc=soc;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sec);
		submit=(Button) findViewById(R.id.submit);
		submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				pass=(EditText)findViewById(R.id.editText1);
				data=pass.getText().toString();
				
					try{
						PrintWriter out=new PrintWriter(soc.getOutputStream(),true);
						out.write(data);
						out.close();
					}
					catch(UnknownHostException e)
					{
						e.printStackTrace();
					}
					catch (IOException e) {
						 e.printStackTrace();
						}
				
					
			  Intent next2=new Intent(Password.this,Security.class);
			  startActivity(next2);
			
			}
		});
	}
}
