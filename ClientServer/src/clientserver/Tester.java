package clientserver;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.representation.Form;
import java.util.Calendar;
import java.util.concurrent.Callable;
import model.Senzor;

public class Tester implements Callable<Senzor>{  
  private static URI getBaseURI() {
    return UriBuilder.fromUri("http://localhost:9090/CRUDproj").build();
  }

    @Override
    public Senzor call() throws InterruptedException{
        Thread.sleep(1000);
        Senzor senzor=null;
        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource service = client.resource(getBaseURI());

        System.out.println(service.path("rest").path("senzori/numara")
            .accept(MediaType.TEXT_PLAIN).get(String.class));
        int last=Integer.parseInt(service.path("rest").path("senzori/numara").accept(MediaType.TEXT_PLAIN).get(String.class));
        for(int i=1;i<=last;i++){
            senzor=service.path("rest").path("senzori/"+i).accept(MediaType.APPLICATION_XML).get(Senzor.class);
            if(senzor.getStatus().equals("now")){
                System.out.println(service.path("rest").path("senzori/"+i)
                    .accept(MediaType.APPLICATION_XML).get(Senzor.class).toString());
                Form form = new Form();
                form.add("id", i);
                form.add("tip", senzor.getTip());
                form.add("valoare", senzor.getValoare());
                form.add("ultimul_vazut", Calendar.getInstance().getTime());
                form.add("status", "citit");
                ClientResponse response = service.path("rest").path("senzori")
                    .type(MediaType.APPLICATION_FORM_URLENCODED)
                    .post(ClientResponse.class, form);
            }
        }        
        return senzor;
        }
} 