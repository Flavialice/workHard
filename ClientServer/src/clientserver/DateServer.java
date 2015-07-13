package clientserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Timer;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Senzor;
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

                    BufferedReader input =
                            new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String answer = input.readLine();
                    String[] s= answer.split(",");
                    if(s[3].equals("temp")){
                     
                        arguments[0]="-a";
                        arguments[1]="publish";
                        arguments[2]="-t";
                        arguments[3]="temp";
                        arguments[4]="-m";
                        arguments[5]="Tempratura are valoarea de "+s[2]+" grade Celsius";
                        Sample.main(arguments);
                        System.out.println("\n");
                    }
                    if(s[1].equals("lumina")){
                        
                        arguments[0]="-a";
                        arguments[1]="publish";
                        arguments[2]="-t";
                        arguments[3]="lumina";
                        arguments[4]="-m";
                        arguments[5]="Intensitatea luminii are valoarea de "+s[0];
                        Sample.main(arguments);
                        System.out.println("\n");
                    }
                    if(s[5].equals("umiditate")){
                        
                        arguments[0]="-a";
                        arguments[1]="publish";
                        arguments[2]="-t";
                        arguments[3]="umiditate";
                        arguments[4]="-m";
                        arguments[5]="Umiditate: "+s[4]+" %";
                        Sample.main(arguments);
                        System.out.println("\n");
                    }
                    final ExecutorService service;
                    final Future<Senzor>  task;
                    service = Executors.newFixedThreadPool(1);        
                    task    =   (Future<Senzor>) service.submit(new Tester());
                    try {
                        final Senzor str;
                        str = task.get();
                        if(str!=null){
                        System.out.println(str+"\n");
                        out.print(str.getTip()+" ");
                        out.println(str.getValoare());}
                    } catch(final InterruptedException ex) {
                        ex.printStackTrace();
                    } catch(final ExecutionException ex) {
                        ex.printStackTrace();
                    }
                    service.shutdownNow();

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