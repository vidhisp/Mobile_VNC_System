package remoteserver;

import java.awt.Robot;
import java.io.IOException;
import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

class ServerDelegate extends Thread {

    Socket socket = null,soc;
    Robot robot = null;
    boolean continueLoop = true;

    public ServerDelegate(Socket socket, Robot robot) {        
    	soc = socket;
        this.robot = robot;
        start(); 
    }

    public void run(){
        //Scanner scanner = null;
        try {
            ServerSocket ss = new ServerSocket(5801);
            System.out.println("Preparing InputStream");
            
            //scanner = new Scanner(socket.getInputStream());
            
            while(continueLoop){
 		        socket = ss.accept();
                System.out.println("Waiting for command");
                DataInputStream scanner = new DataInputStream(socket.getInputStream());
                int command = scanner.readInt();                
                System.out.println("New command: " + command);
                switch(command){
                    case -1:
                    	System.out.println("Mouse Clicked");
                        robot.mousePress(scanner.readInt());
                    break;
                    case -2:
                    	System.out.println("Mouse Released");
                        robot.mouseRelease(scanner.readInt());
                    break;
                    case -3:
                        robot.keyPress(scanner.readInt());
                    break;
                    case -4:
                        robot.keyRelease(scanner.readInt());
                    break;
                    case -5:
                    	System.out.println("Mouse Moved");
						int x=scanner.readInt();
						System.out.println(x);
						int y=scanner.readInt();
						System.out.println(y);
                        robot.mouseMove(x,y);
                    break;
                }
                scanner.close();
                socket.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }        
    }

}
