package rest.movie.resources;


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
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import rest.movie.dao.TheaterDao;
import rest.movie.model.Theater;

@Path("/theaters")
public class TheatersResource {

	Request request;
	@Context
	UriInfo uriInfo;


	@GET
	@Produces(MediaType.TEXT_XML)
	public List<Theater> getTheatersBrowser() {
		List<Theater> theaters = new ArrayList<Theater>();
		theaters.addAll(TheaterDao.instance.getModel().values());
		return theaters; 
	}

	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<Theater> getTheaters() {
		List<Theater> theaters = new ArrayList<Theater>();
		theaters.addAll(TheaterDao.instance.getModel().values());
		return theaters; 
	}

	@GET
	@Path("count")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCount() {
		int count = TheaterDao.instance.getModel().size();
		return String.valueOf(count);
	}

	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void newTheater(@FormParam("id") String id,
			@FormParam("name") String name,
			@FormParam("city") String city,
			@Context HttpServletResponse servletResponse) throws IOException {
		Theater theater = new Theater(id, name, city);
		TheaterDao.instance.getModel().put(id, theater);

		servletResponse.sendRedirect("../create_theater.html");
	}

	@Path("{theater}")
	public TheaterResource getTheater(@PathParam("theater") String id) {
		return new TheaterResource(uriInfo, request, id);
	}
	
	@Path("/search")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response search() {
		List<Theater> result = new ArrayList<Theater>();
		result.addAll(TheaterDao.instance.getModel().values());
        return Response.ok().entity(new GenericEntity<List<Theater>>(result) {})
        		.header("Access-Control-Allow-Origin", "*")
        		.header("Access-Control-Allow-Mehtods", "GET, POST, DELETE, PUT, OPTIONS, HEAD")
        		.build();
	}
	
}