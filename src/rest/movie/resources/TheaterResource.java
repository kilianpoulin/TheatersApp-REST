package rest.movie.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import rest.movie.dao.TheaterDao;
import rest.movie.model.Theater;

public class TheaterResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	String id;
	public TheaterResource(UriInfo uriInfo, Request request, String id) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = id;
	}
 
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Theater getTheater() {
		Theater theater = TheaterDao.instance.getModel().get(id);
		if(theater==null)
			throw new RuntimeException("Get: Theater with " + id +  " not found");
		return theater;
	}

	@GET
	@Produces(MediaType.TEXT_XML)
	public Theater getTheaterHTML() {
		Theater theater = TheaterDao.instance.getModel().get(id);
		if(theater==null)
			throw new RuntimeException("Get: Theater with " + id +  " not found");
		return theater;
	}

	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response putTheater(JAXBElement<Theater> theater) {
		Theater c = theater.getValue();
		return putAndGetResponse(c);
	}

	@DELETE
	public void deleteTheater() {
		Theater c = TheaterDao.instance.getModel().remove(id);
		if(c==null)
			throw new RuntimeException("Delete: Theater with " + id +  " not found");
	}

	private Response putAndGetResponse(Theater theater) {
		Response res;
		if(TheaterDao.instance.getModel().containsKey(theater.getId())) {
			res = Response.noContent().build();
		} else {
			res = Response.created(uriInfo.getAbsolutePath()).build();
		}
		TheaterDao.instance.getModel().put(theater.getId(), theater);
		return res;
	}
}
