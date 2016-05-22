package com.example.vnc;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

//import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
//import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
//import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;

import android.widget.ImageView;
//import android.widget.Toast;

public class Screen extends Activity implements OnTouchListener {

	DataInputStream dis;
	Socket soc;
	byte data[];
	String ip;
	int port1,sWid,sHei,wid,hei;
    float x,y,prex,prey;
    
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen);
		View v1 = findViewById(R.id.imageView1);
        v1.setOnTouchListener(this);
		Intent inn = getIntent();		
		Bundle b = inn.getExtras();
		ip = (String) b.get("ip");
		port1 = (int) b.getInt("port");
		Display dis1 = getWindowManager().getDefaultDisplay();
		Point size=new Point();
		dis1.getSize(size);
		wid = size.x;
		hei = size.y;
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					soc = new Socket(ip, port1);
					dis = new DataInputStream(soc.getInputStream());
					sWid = dis.readInt(); 
					sHei = dis.readInt();
					//Log.d("***" +sWid, "^^^"+sHei);
 					while (true) {
						int len = dis.readInt();
						Log.d("===============" + len, "-------------------"
								+ len);

						data = new byte[len];
						dis.readFully(data);

						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								// TODO Auto-generated method stub
								ByteArrayInputStream in = new ByteArrayInputStream(
										data);
								Bitmap bmp = BitmapFactory.decodeStream(in);
								ImageView image = (ImageView) findViewById(R.id.imageView1);
								image.setImageBitmap(bmp);								
							}
						});
					}
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	@Override
	public boolean onTouch(View v, final MotionEvent ae) {
		// TODO Auto-generated method stub
		Log.d("***" +sWid, "^^^"+sHei);
		//if(!(prex==e.getX() && prey==e.getY()))
			//	{
				 x=ae.getX();
			     y=ae.getY();
			     Log.d(x+"----",y+"-----");
			     new Thread(new Runnable()
			     {
			      @Override
			      public void run()
			      {
			       try
			       {
			       switch(ae.getAction())
			       {
			       case MotionEvent.ACTION_MOVE:
			    	   try
				       {
			    		   Socket soc1 = new Socket(ip,5801);	   
					       DataOutputStream dos = new DataOutputStream(soc1.getOutputStream());
					       dos.writeInt(-5);
					       int te=(int)(x*(float)(sWid/wid));
					       dos.writeInt(te);
					       dos.flush();
					       int te1=(int)(y*(float)(sHei/wid));
					       dos.writeInt(te1);
					       dos.flush();
					       
					       dos.close();
					       soc1.close();
				       }
			    	   catch(Exception e)
				       {}
			    	   break;
			    	   
			       case MotionEvent.ACTION_UP:
			    	   try
				       {    
			    		   Socket soc1 = new Socket(ip,5801);
			    		   DataOutputStream dos = new DataOutputStream(soc1.getOutputStream());
				       dos.writeInt(-2);
				       dos.writeInt(16);
				       dos.close();
				       soc1.close();
				       }
				       catch(Exception e)
				       {}
			
			    	   break;	
			    	   
			       case MotionEvent.ACTION_DOWN:
			    	   try
				       {
			    		   
			    		   Socket soc1 = new Socket(ip,5801);   
			    		   DataOutputStream dos = new DataOutputStream(soc1.getOutputStream());
				       dos.writeInt(-1);
				       dos.writeInt(16);
				       dos.close();
				       soc1.close();
				       }
				       catch(Exception e)
				       {}   
			       break;
			   
			       
				       }
			       }
			       
			       catch(Exception e)
			       {			    	   
			       }
			      }
			     }).start();
			 
		return true;
	}
}
