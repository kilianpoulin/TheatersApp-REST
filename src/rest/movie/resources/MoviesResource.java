package rest.movie.resources;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
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

import rest.movie.dao.DBAccess;
import rest.movie.dao.MovieDao;
import rest.movie.dao.ScheduleDao;
import rest.movie.dao.SessionDao;
import rest.movie.errors.PermissionException;
import rest.movie.model.Movie;
import rest.movie.model.Owner;
import rest.movie.model.Schedule;



// Will map the resource to the URL movies
@Path("/movies")
public class MoviesResource {

	// Allows to insert contextual objects into the class, 
	// e.g. ServletContext, Request, Response, UriInfo
	Request request;
	// Allows to insert contextual objects into the class, 
	// e.g. ServletContext, Request, Response, UriInfo
	@Context
	UriInfo uriInfo;


	// Return the list of movies to the user in the browser
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMovies() {
		List<Movie> movies = new ArrayList<Movie>();
		movies.addAll(MovieDao.instance.getModel().values());
		return Response.ok().entity(new GenericEntity<List<Movie>>(movies) {})
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD")
				.build();
	}

	// Return the list of movies in a given city
	@GET
	@Path("/city/{city}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMoviesInCity(@PathParam("city") String city) {
		List<Movie> movies = new ArrayList<Movie>();
		// Récupération des séances et ajout des films ayant des séances dans les cinémas de la ville donnée
		for (Schedule sch : ScheduleDao.instance.getModel().values())
		{
			if (sch.getTheater().getCity().equalsIgnoreCase(city) && !movies.contains(sch.getMovie()))
				movies.add(sch.getMovie());
		}
		return Response.ok().entity(new GenericEntity<List<Movie>>(movies) {})
		.header("Access-Control-Allow-Origin", "*")
		.header("Access-Control-Allow-Mehtods", "GET, POST, DELETE, PUT, OPTIONS, HEAD")
		.build();
	}


	// returns the number of movies
	// Use http://localhost:8080/RestPower/rest/movies/count
	// to get the total number of records
	@GET
	@Path("count")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCount() {
		int count = MovieDao.instance.getModel().size();
		return String.valueOf(count);
	}

	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void newMovie(@CookieParam("sessionId") String sessionId,
			@FormParam("title") String description,
			@FormParam("summary") String summary,
			@FormParam("duration") int duration,
			@FormParam("language") String language,
			@FormParam("director") String director,
			@FormParam("actors") String actors,
			@FormParam("minAge") int minAge,
			@Context HttpServletResponse servletResponse) throws IOException, PermissionException {
		// Vérification de s'il est bien connecté
		Owner owner = SessionDao.instance.getLoggedInOwner(sessionId);
		if (owner == null)
			throw new PermissionException("Please log in before doing this.");
		if (owner.getTheater() != null)
			throw new PermissionException("Only movie owners can add movies, you are a theater owner.");
		
		String query = "INSERT INTO `movies` (`title`, `summary`, `duration`, `language`, `director`, `actors`, `minAge`, `owner_id`) VALUES('" + description + "','" + summary + "'," + duration + ",'" + language + "','" + director + "','" + actors + "'," + minAge + "," + owner.getId() + ")";
		DBAccess dTransac = new DBAccess();
		dTransac.UpdateDB(query);
		
		MovieDao.incr_NUMBER();
		Movie movie = new Movie(MovieDao.get_NUMBER(), description, owner);
		if (summary != null)
			movie.setSummary(summary);
		if (duration > 0)
			movie.setDuration(duration);
		if (language != null)
			movie.setLanguage(language);
		if (director != null)
			movie.setDirector(director);
		if (actors != null)
			movie.setActors(new ArrayList<String>(Arrays.asList(actors.split(",")))); // Acteurs séparés par une virgule
		if (minAge > 0)
			movie.setMinAge(minAge);
		MovieDao.instance.getModel().put(MovieDao.get_NUMBER(), movie);

		servletResponse.sendRedirect("../create_movie.html");
	}
	
	
	@Path("{movie}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response Movie(@PathParam("movie") int id) {

		Movie movie = MovieDao.instance.getModel().get(id);
		return Response.ok().entity(new GenericEntity<Movie>(movie) {})
	    		.header("Access-Control-Allow-Origin", "*")
	    		.header("Access-Control-Allow-Mehtods", "GET, POST, DELETE, PUT, OPTIONS, HEAD")
	    		.build();

	}
	
	
	@Path("/search")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	//@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response searchMovie(@FormParam("city") String city) {
		List<Movie> movies = new ArrayList<Movie>();
		// Récupération des séances et ajout des films ayant des séances dans les cinémas de la ville donnée
		for (Schedule sch : ScheduleDao.instance.getModel().values())
		{
			if (sch.getTheater().getCity().equalsIgnoreCase(city) && !movies.contains(sch.getMovie()))
				movies.add(sch.getMovie());
		}
	    return Response.ok().entity(new GenericEntity<List<Movie>>(movies) {})
	    		.header("Access-Control-Allow-Origin", "*")
	    		.header("Access-Control-Allow-Mehtods", "GET, POST, DELETE, PUT, OPTIONS, HEAD")
	    		.build();

	}

} 