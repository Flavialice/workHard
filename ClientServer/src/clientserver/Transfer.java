package clientserver;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.representation.Form;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

public class Transfer extends Thread{

    @Override
    public  void run() {
        ServerSocket listener =null;
        try {
        listener = new ServerSocket(6060);
            while (true) {
                Socket socket = listener.accept();
                try {
                    PrintWriter out
                            = new PrintWriter(socket.getOutputStream(), true);

                    BufferedReader input
                            = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String answer = input.readLine();
                    String[] s = answer.split(",");
                    ClientConfig config = new DefaultClientConfig();
                    Client client = Client.create(config);
                    WebResource service = client.resource(getBaseURI());

                    int last = Integer.parseInt(service.path("rest").path("senzori/numara").accept(MediaType.TEXT_PLAIN).get(String.class));
                    Form form = new Form();
                    form.add("id", last + 1);
                    form.add("tip", s[1]);
                    form.add("valoare", Float.parseFloat(s[3]));
                    form.add("ultimul_vazut", Calendar.getInstance().getTime());
                    form.add("status", s[5]);
                    ClientResponse response = service.path("rest").path("senzori")
                            .type(MediaType.APPLICATION_FORM_URLENCODED)
                            .post(ClientResponse.class, form);
                } finally {
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Transfer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
        }
    }

    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost:9090/CRUDproj").build();
    }
}
