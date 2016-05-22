package com.example.vnc;

//import java.io.BufferedReader;
import java.io.IOException;
//import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
//import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
//import android.widget.Toast;


public class MainActivity extends Activity
{
	final Context context = this;
		Button connect;
	public	EditText ipadd,portadd;
		Socket soc=null,sock1;
		String ip,port,result,a;
	public	int port1;
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		connect=(Button) findViewById(R.id.button1);
		connect.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ipadd=(EditText) findViewById(R.id.editText1);
				portadd=(EditText) findViewById(R.id.editText2);
				ip=ipadd.getText().toString();
				port=portadd.getText().toString();
				port1=Integer.parseInt(port);
				
			new Thread(new Runnable()
			{
				public void run()
				{
				try{
					soc=new Socket(ip,port1);
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
					LayoutInflater li=LayoutInflater.from(context);
					View promptsView = li.inflate(R.layout.prompt, null);
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
					alertDialogBuilder.setView(promptsView);
					final EditText userInput = (EditText) promptsView.findViewById(R.id.editText1);
					
					alertDialogBuilder
					.setCancelable(false)
					.setPositiveButton("OK",
					  new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog,int id) {
							// get user input and set it to result
							// edit text
							result=userInput.getText().toString();
							try{
							PrintWriter out=new PrintWriter(soc.getOutputStream(),true);
							out.write(result);
							out.close();	
							//Toast.makeText(MainActivity.this,"Connecting to Server"+ip,Toast.LENGTH_SHORT).show();
							soc.close();
							
							
							
							Intent next1=new Intent(MainActivity.this,Security.class);
							next1.putExtra("ip", ip);
							next1.putExtra("port", port1);
							
							startActivity(next1);
							}
							catch(Exception e){}
							}
						  })
						  .setNegativeButton("Cancel",
								  new DialogInterface.OnClickListener() {
								    public void onClick(DialogInterface dialog,int id) {
									dialog.cancel();
								    }
								  });
					AlertDialog alertDialog = alertDialogBuilder.create();
					alertDialog.show();
			    
				 
			}
		});
		
	}
	
}




