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
import model.Senzor;


public class Tester extends Thread{
  public void run(){
    ClientConfig config = new DefaultClientConfig();
    Client client = Client.create(config);
    WebResource service = client.resource(getBaseURI());

    // Get the Todos
    /*System.out.println(service.path("rest").path("senzori")
        .accept(MediaType.TEXT_PLAIN).get(String.class));*/

    System.out.println(service.path("rest").path("senzori/numara")
        .accept(MediaType.TEXT_PLAIN).get(String.class));
    int last=Integer.parseInt(service.path("rest").path("senzori/numara").accept(MediaType.TEXT_PLAIN).get(String.class));
    // Get the Todo with id 1
    for(int i=1;i<=last;i++){
        Senzor senzor=service.path("rest").path("senzori/"+i).accept(MediaType.APPLICATION_XML).get(Senzor.class);
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
        
    // get Todo with id 1
    //service.path("rest").path("todos/1").delete();
    // Get the all todos, id 1 should be deleted
    /*
    System.out.println(service.path("rest").path("senzori")
        .accept(MediaType.APPLICATION_XML).get(String.class));
    */
    // create a Todo
    Form form = new Form();
    form.add("id", last+1);
    form.add("tip", "temperatura");
    form.add("valoare", 2);
    form.add("ultimul_vazut", Calendar.getInstance().getTime());
    form.add("status", "citit");
     //ClientResponse response = service.path("rest").path("senzori")
       // .type(MediaType.APPLICATION_FORM_URLENCODED)
        //.post(ClientResponse.class, form);
    //System.out.println("Form response " + response.getEntity(String.class));
    // Get the all todos, id 4 should be created
    /*System.out.println(service.path("rest").path("senzori")
        .accept(MediaType.APPLICATION_XML).get(String.class));*/
    //ds.notify();
    //this.finalize();
  }

  private static URI getBaseURI() {
    return UriBuilder.fromUri("http://localhost:9090/CRUDproj").build();
  }
} 