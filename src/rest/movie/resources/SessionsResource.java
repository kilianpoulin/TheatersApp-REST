package rest.movie.resources;


import java.io.IOException;
import java.util.Map.Entry;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
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
import rest.movie.dao.SessionDao;
import rest.movie.model.Owner;
import rest.movie.model.Session;

// Will map the resource to the URL movies
@Path("/sessions")
public class SessionsResource {

	Request request;
	@Context
	UriInfo uriInfo;


	@POST
	@Path("login")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public void login(@FormParam("username") String username, @FormParam("password") String password,
			@Context HttpServletResponse servletResponse) throws IOException {
		boolean valid = true;
		// Recherche d'un Owner avec ce nom et ce mot de passe
		for (Entry<String, Owner> e : OwnerDao.instance.getModel().entrySet())
		{
			Owner o = e.getValue();
			if (o.getUsername().equalsIgnoreCase(username) && o.getPassword().equals(password))
			{
				String id = UUID.randomUUID().toString(); // ID aléatoire complexe
				Session session = new Session(id, o);
				SessionDao.instance.getModel().put(id, session);
				
				// Cookie contenant l'ID de la session
				Cookie cookie = new Cookie("sessionId", id);
				cookie.setMaxAge(3600); // Une heure
				cookie.setPath("/");
				servletResponse.addCookie(cookie);
				
				if(session.getOwner().getTheater() == null) {
					servletResponse.sendRedirect("../../create_movie.html");
					valid = false;
				}
				else {
					servletResponse.sendRedirect("../../create_schedule.html");
					valid = false;
				}
			}
		}
		if(valid)
			servletResponse.sendRedirect("../../login.html#error");
	}

	@Path("{session}")
	public SessionResource getSession(@PathParam("session") String id) {
		return new SessionResource(uriInfo, request, id);
	}

}