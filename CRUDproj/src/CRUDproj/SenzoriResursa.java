package CRUDproj;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

@Path("/senzori")
public class SenzoriResursa {

	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	/*@GET
	@Produces(MediaType.TEXT_HTML)
	public List<Senzor> getSenzoriBrowser(){
		List<Senzor> senzori=new ArrayList<Senzor>();
		senzori.addAll(ListaSenzor.instance.getModel().values());
		return senzori;
	}*/
	
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<Senzor> getSenzori(){
		List<Senzor> senzori=new ArrayList<Senzor>();
		senzori.addAll(ListaSenzor.instance.getModel().values());
		return senzori;
	}
	
	 @GET
	 @Path("numara")
	 @Produces(MediaType.TEXT_PLAIN)
	 public String getNumara(){
		 int numar= ListaSenzor.instance.getModel().size();
		 return String.valueOf(numar);
	 }
	 
	 @POST
	 @Produces(MediaType.TEXT_PLAIN)
	 @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	 public void adaugaSenzor(@FormParam("id") String id,
			 @FormParam("tip") String tip, 
			 @FormParam("valoare") float valoare, 
			 @FormParam("ultimul_vazut") String ultimul_vazut, 
			 @FormParam("status") String status,
			 @Context HttpServletResponse servletResponse) throws IOException  {
		 Senzor senzor=new Senzor(id, tip, valoare, ultimul_vazut, status);
		 ListaSenzor.instance.getModel().put(id, senzor);
		 
		 servletResponse.sendRedirect("../create_todo.html");
	 }
	 
	 @Path("{senzor}")
	 public SenzorResursa getSenzor(@PathParam("senzor") String id){
		 return new SenzorResursa(uriInfo, request, id);
	 }
}
