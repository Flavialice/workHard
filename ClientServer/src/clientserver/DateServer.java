package clientserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * A TCP server that runs on port 9090.  When a client connects, it
 * sends the client the current date and time, then closes the
 * connection with that client.  Arguably just about the simplest
 * server you can write.
 */
public class DateServer extends Thread{

    public  void run(){
        ServerSocket listener =null;
        try {
        listener = new ServerSocket(7070);
            while (true) {
                Socket socket = listener.accept();
                try {
                    PrintWriter out =
                        new PrintWriter(socket.getOutputStream(), true);
                    out.println(new Date().toString());
                    
                    BufferedReader input =
                            new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String answer = input.readLine();
                    float value=Float.parseFloat(answer);
                    if(value<0.3)
                    	System.out.println(answer);
                    else{
                        Thread.sleep(3000);
                    	System.out.println(answer+" Valoarea este prea mare");
                        //new Tester().run(answer+" Valoarea este prea mare", this);
                    }
                } catch (InterruptedException ex) {
                Logger.getLogger(DateServer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Throwable ex) {
                Logger.getLogger(DateServer.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                    socket.close();
                }
            }
        }
        catch (IOException ex) {
            Logger.getLogger(DateServer.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }

}