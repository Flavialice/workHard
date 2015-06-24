package CRUDproj;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

public class SenzorResursa {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	String id;
	public SenzorResursa(UriInfo uriInfo, Request request, String id) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = id;
	}
	
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Senzor getSenzor(){
		Senzor senzor=ListaSenzor.instance.getModel().get(id);
		if(senzor==null)
			throw new RuntimeException("Get: Senzorul cu id-ul " + id +  " nu a fost gasit");
	    return senzor;
	}
	
	/*@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Senzor getSenzorHTML(){
		Senzor senzor=ListaSenzor.instance.getModel().get(id);
		if(senzor==null)
			throw new RuntimeException("Get: Senzorul cu id-ul " + id +  " nu a fost gasit");
	    return senzor;
	}*/
	
	@PUT
	@Consumes(MediaType.TEXT_PLAIN)
	public Response putSenzor(JAXBElement<Senzor> senzor){
		Senzor s=senzor.getValue();
		return getResponse(s);
	}
	
	private Response getResponse(Senzor senzor){
		Response raspuns;
		if(ListaSenzor.instance.getModel().containsKey(senzor.getId())){
			raspuns=Response.noContent().build();	
		}
		else{
			raspuns=Response.created(uriInfo.getAbsolutePath()).build();
		}
		ListaSenzor.instance.getModel().put(senzor.getId(), senzor);
		return raspuns;
	}
}
