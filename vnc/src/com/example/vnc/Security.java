package com.example.vnc;

//import java.io.BufferedReader;
import java.io.BufferedReader;
//import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
//import java.io.InputStreamReader;
//import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;

//import android.util.Log;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Security extends Activity {

	Socket sock,sock1;
	String a,ip,no1;
	int port1,flag=0,wid, hei;
	Button b1,b2;
	EditText no;	
	
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.security);
		
		Intent inn=getIntent();
		Bundle b= inn.getExtras();
		Display dis1 = getWindowManager().getDefaultDisplay();
		Point size=new Point();
		dis1.getSize(size);
		wid = size.x;
		hei = size.y;
		Log.d(" " + wid, " " + hei);
		ip=(String) b.get("ip");
		port1=(int)b.getInt("port");
		//a=(String) b.getString("no");
		
		b2=(Button) findViewById(R.id.button2);
		b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread(new Runnable()
				{
					public void run()
					{
					try{
						if(flag==0){
						sock=new Socket(ip,port1);
						BufferedReader br=new BufferedReader(new InputStreamReader(sock.getInputStream()));
						a=br.readLine();
						
						sock.close();
						flag=1;
						}
						}
					catch(UnknownHostException e)
					{
						e.printStackTrace();
					}
					catch (IOException e) {
						 e.printStackTrace();
						}
			    }	
				}).start();
				Toast.makeText(Security.this,"Enter the result for no "+a,Toast.LENGTH_SHORT).show();		
			}
		});
							
			b1=(Button)findViewById(R.id.button1);
			b1.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					new Thread(new Runnable()
					{
						public void run()
						{
							try
							{
								sock1=new Socket(ip,port1);
								no=(EditText) findViewById(R.id.editText1);
								no1=no.getText().toString();
								//PrintWriter out=new PrintWriter(sock1.getOutputStream(),true);
								DataOutputStream out = new DataOutputStream(sock1.getOutputStream());
								out.writeInt(Integer.parseInt(no1));								
								out.writeInt(wid);								
								out.writeInt(hei);
								out.close();
								sock1.close();												
							}
							catch(UnknownHostException e)
							{
								e.printStackTrace();
							}
							catch(IOException e)
							{
								
							}
						}
					}).start();					
					Toast.makeText(Security.this,"Connecting to Server"+ip,Toast.LENGTH_SHORT).show();
					Intent next=new Intent(Security.this,Screen.class);
					next.putExtra("ip", ip);
					next.putExtra("port", port1);
					startActivity(next);					
				}
			});
	}
}
