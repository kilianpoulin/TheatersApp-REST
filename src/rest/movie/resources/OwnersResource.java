package rest.movie.resources;


import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import rest.movie.dao.OwnerDao;
import rest.movie.model.Owner;

// Will map the resource to the URL movies
@Path("/owners")
public class OwnersResource {

	// Allows to insert contextual objects into the class, 
	// e.g. ServletContext, Request, Response, UriInfo
	Request request;
	// Allows to insert contextual objects into the class, 
	// e.g. ServletContext, Request, Response, UriInfo
	@Context
	UriInfo uriInfo;

	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void newOwner(@FormParam("id") String id, 
			@FormParam("username") String username,
			@FormParam("password") String password,
			@Context HttpServletResponse servletResponse) throws IOException {
		Owner owner = new Owner(id, username, password);
		OwnerDao.instance.getModel().put(username, owner);

		servletResponse.sendRedirect("../create_movie.html");
	}

	@Path("{owner}")
	public OwnerResource getMovie(@PathParam("owner") String id) {
		return new OwnerResource(uriInfo, request, id);
	}
	
	
	

} 