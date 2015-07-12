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
import org.eclipse.paho.sample.mqttv3app.Sample;

public class DateServer extends Thread{

    @Override
    public  void run(){
        String[] arguments=new String[6];
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
                    String[] s= answer.split(",");
                    System.out.println(answer);
                    if(s[1].equals("temp")){
                        System.out.println("Temperatura");
                        arguments[0]="-a";
                        arguments[1]="publish";
                        arguments[2]="-t";
                        arguments[3]="temp";
                        arguments[4]="-m";
                        arguments[5]="Tempratura are valoarea de "+s[0]+" grade Celsius";
                        Sample.main(arguments);
                    }
                    else
                        System.out.println("Altceva");
            } catch (IOException ex) {
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