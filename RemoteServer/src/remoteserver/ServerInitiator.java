package remoteserver;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDesktopPane;
import java.net.UnknownHostException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class ServerInitiator {
   
    private String pass="123456";
	private int wid, hei;	
  
	
	public static void main(String args[]){
        String port = JOptionPane.showInputDialog("Please enter listening port");
        new ServerInitiator().initialize(Integer.parseInt(port));
    }
	
	public void initialize(int port){
		Robot robot = null;
        Rectangle rectangle = null; 
		Socket client=null;
		ServerSocket sc=null;
        try {
		
		
            sc = new ServerSocket(port);
            
            drawGUI();
            
            
                client = sc.accept();
				BufferedReader br=new BufferedReader(new InputStreamReader(client.getInputStream()));
				String str=br.readLine();
				br.close();
				client.close();
				if(pass.equals(str))
				{
                Random rand=new Random();
				int rint=rand.nextInt(6);
				int result=rint*rint*rint;
				System.out.println(result);
				Socket cli=sc.accept();
				PrintWriter out=new PrintWriter(cli.getOutputStream());
				out.print(rint);
				out.close();
				cli.close();
				Socket c=sc.accept();
				//BufferedReader b=new BufferedReader(new InputStreamReader(c.getInputStream()));
				DataInputStream dis = new DataInputStream(c.getInputStream());
				int a = dis.readInt();
				wid = dis.readInt();
				System.out.println(wid);
				hei = dis.readInt();
				System.out.println(hei);
				dis.close();
				c.close();
								
				if(a==result){				
				System.out.println("New client Connected to the server "+wid+ " "+hei);				
				}
				
				}
				else
				{
				System.exit(0);
				}
				client= sc.accept();
			
            GraphicsEnvironment gEnv=GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice gDev=gEnv.getDefaultScreenDevice();

            
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            rectangle = new Rectangle(dim);

            
            robot = new Robot(gDev);

            
            new ScreenSpyer(client,robot,rectangle,wid,hei);
            
            new ServerDelegate(client,robot);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		
		
    }

    private void drawGUI() {
        JFrame frame = new JFrame("Remote Admin");
        JButton button= new JButton("Terminate");
        
        frame.setBounds(100,100,150,150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(button);
        button.addActionListener( new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        }
      );
      frame.setVisible(true);
    }
}