package rest.movie.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import rest.movie.dao.OwnerDao;
import rest.movie.model.Owner;

public class OwnerResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	String id;
	public OwnerResource(UriInfo uriInfo, Request request, String id) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = id;
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response putOwner(JAXBElement<Owner> owner) {
		Owner o = owner.getValue();
		return putAndGetResponse(o);
	}

	private Response putAndGetResponse(Owner owner) {
		Response res;
		if(OwnerDao.instance.getModel().containsKey(owner.getId())) {
			res = Response.noContent().build();
		} else {
			res = Response.created(uriInfo.getAbsolutePath()).build();
		}
		OwnerDao.instance.getModel().put(owner.getId(), owner);
		return res;
	}
}
