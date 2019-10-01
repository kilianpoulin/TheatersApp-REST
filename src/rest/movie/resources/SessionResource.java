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

import rest.movie.dao.SessionDao;
import rest.movie.model.Session;

public class SessionResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	String id;
	public SessionResource(UriInfo uriInfo, Request request, String id) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = id;
	}

	//Application integration     
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Session getSession() {
		Session session = SessionDao.instance.getModel().get(id);
		if(session==null)
			throw new RuntimeException("Get: Session with " + id +  " not found");
		return session;
	}

	// For the browser
	@GET
	@Produces(MediaType.TEXT_XML)
	public Session getSessionHTML() {
		Session session = SessionDao.instance.getModel().get(id);
		if(session==null)
			throw new RuntimeException("Get: Session with " + id +  " not found");
		return session;
	}

	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response putSession(JAXBElement<Session> session) {
		Session c = session.getValue();
		return putAndGetResponse(c);
	}

	@DELETE
	public void deleteSession() {
		Session c = SessionDao.instance.getModel().remove(id);
		if(c==null)
			throw new RuntimeException("Delete: Session with " + id +  " not found");
	}

	private Response putAndGetResponse(Session session) {
		Response res;
		if(SessionDao.instance.getModel().containsKey(session.getId())) {
			res = Response.noContent().build();
		} else {
			res = Response.created(uriInfo.getAbsolutePath()).build();
		}
		SessionDao.instance.getModel().put(session.getId(), session);
		return res;
	}
}
