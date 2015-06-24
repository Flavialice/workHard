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
import model.Todo;

public class Tester extends Thread{
  public void run(String s, DateServer ds) throws Throwable {
    ClientConfig config = new DefaultClientConfig();
    Client client = Client.create(config);
    WebResource service = client.resource(getBaseURI());

    // Get the Todos
    System.out.println(service.path("rest").path("todos")
        .accept(MediaType.TEXT_XML).get(String.class));

    System.out.println(service.path("rest").path("todos/count")
        .accept(MediaType.TEXT_PLAIN).get(String.class));
    int last=Integer.parseInt(service.path("rest").path("todos/count").accept(MediaType.TEXT_PLAIN).get(String.class));
    // Get the Todo with id 1
    System.out.println(service.path("rest").path("todos/"+last)
        .accept(MediaType.APPLICATION_XML).get(Todo.class).toString());
    // get Todo with id 1
    //service.path("rest").path("todos/1").delete();
    // Get the all todos, id 1 should be deleted
    System.out.println(service.path("rest").path("todos")
        .accept(MediaType.APPLICATION_XML).get(String.class));

    // create a Todo
    Form form = new Form();
    form.add("id", last+1);
    form.add("summary", s);
     ClientResponse response = service.path("rest").path("todos")
        .type(MediaType.APPLICATION_FORM_URLENCODED)
        .post(ClientResponse.class, form);
    //System.out.println("Form response " + response.getEntity(String.class));
    // Get the all todos, id 4 should be created
    System.out.println(service.path("rest").path("todos")
        .accept(MediaType.APPLICATION_XML).get(String.class));
    ds.notify();
    this.finalize();
  }

  private static URI getBaseURI() {
    return UriBuilder.fromUri("http://localhost:9090/CRUDproj").build();
  }
} 