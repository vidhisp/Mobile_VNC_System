package remoteserver;

import javax.imageio.*;
import java.awt.Image;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.net.Socket;
import javax.swing.ImageIcon;
//import com.sun.media.jai.codec.BMPEncodeParam;
//import com.sun.media.jai.codec.*;
import java.io.*;

class ScreenSpyer extends Thread {

    Socket socket = null; 
    Robot robot = null; 
    Rectangle rectangle ; 
    int wid,sWid,hei,sHei;
    boolean continueLoop = true;
    DataOutputStream dos = null;
    
    public ScreenSpyer(Socket socket, Robot robot,Rectangle rect, int w, int h) {
        this.socket = socket;
        this.robot = robot;
        rectangle = rect;
        wid = w;
        hei = h;
        start();
    }

    public void run(){
        /*ObjectOutputStream oos = null; 


        try{
            
            oos = new ObjectOutputStream(socket.getOutputStream());
                        oos.writeObject(rectangle);
        }catch(IOException ex){
            ex.printStackTrace();
        }*/        
      try
      {      
      dos = new DataOutputStream(socket.getOutputStream());
      dos.writeInt(rectangle.width);
      dos.writeInt(rectangle.height);
      }
      catch(Exception e)
      {}
      
      while(continueLoop){
            
			try{
            BufferedImage image = robot.createScreenCapture(rectangle);            			
            byte[] imageInByte;
            
		    Image im1 = image.getScaledInstance(wid,hei, Image.SCALE_SMOOTH);
            BufferedImage bi1 = new BufferedImage(wid,hei,BufferedImage.TYPE_INT_RGB);
	        Graphics g = bi1.createGraphics();
            g.drawImage(im1,0,0,new Color(0,0,0), null);
            g.dispose(); 
		    
		                
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bi1,"jpg", baos);
            baos.flush();
            imageInByte = baos.toByteArray();
            baos.close();

            
            dos.writeInt(imageInByte.length);
			System.out.println(imageInByte.length);
            dos.write(imageInByte);
			Thread.sleep(1000);
            dos.flush();
			}
			catch(Exception e)
			{}
                       try{
                Thread.sleep(100);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }

    }

}
