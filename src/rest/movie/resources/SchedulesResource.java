package rest.movie.resources;


import java.io.IOException;
import java.time.DayOfWeek;
import java.util.ArrayList;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import rest.movie.dao.DBAccess;
import rest.movie.dao.MovieDao;
import rest.movie.dao.ScheduleDao;
import rest.movie.dao.SessionDao;
import rest.movie.errors.PermissionException;
import rest.movie.model.Movie;
import rest.movie.model.Owner;
import rest.movie.model.Schedule;
import rest.movie.model.Theater;



@Path("/schedules")
public class SchedulesResource {

	Request request;
	@Context
	UriInfo uriInfo;


	// Return the list of schedules to the user in the browser
	@GET
	@Produces(MediaType.TEXT_XML)
	public List<Schedule> getSchedulesBrowser() {
		List<Schedule> schedules = new ArrayList<Schedule>();
		schedules.addAll(ScheduleDao.instance.getModel().values());
		return schedules; 
	}

	// Return the list of schedules for applications
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<Schedule> getSchedules() {
		List<Schedule> schedules = new ArrayList<Schedule>();
		schedules.addAll(ScheduleDao.instance.getModel().values());
		return schedules; 
	}

	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void newSchedule(@CookieParam("sessionId") String sessionId,
			@FormParam("movieId") int movieId,
			@FormParam("startDate") String startDate,
			@FormParam("endDate") String endDate,
			@FormParam("day1") String day1,
			@FormParam("day2") String day2,
			@FormParam("day3") String day3,
			@FormParam("time1") String time1,
			@FormParam("time2") String time2,
			@FormParam("time3") String time3,
			@Context HttpServletResponse servletResponse) throws IOException, PermissionException {

		// Vérification de s'il est bien connecté
		Owner owner = SessionDao.instance.getLoggedInOwner(sessionId);
		if (owner == null)
			throw new PermissionException("Please log in before doing this.");
		Theater theater = owner.getTheater();
		if (theater == null)
			throw new PermissionException("You are not a theater owner.");

		// Récupération du film
		Movie movie = MovieDao.instance.getModel().get(movieId);

		// Jours
		DayOfWeek j1 = DayOfWeek.valueOf(day1);
		DayOfWeek j2 = DayOfWeek.valueOf(day2);
		DayOfWeek j3 = DayOfWeek.valueOf(day3);
		
		String query = "INSERT INTO `sessions` (`movie_id`, `theater_id`, `startDate`, `endDate`, `listOfDates`, `listOfTimes`) VALUES("+ movieId + "," + owner.getTheater().getId() + ",'"+ startDate + "','" +  endDate + "','" +  day1+","+day2+","+day3+ "','" + time1+","+time2+","+time3 +"')";
		DBAccess dTransac = new DBAccess();
		dTransac.UpdateDB(query);
		// Création du schedule
		ScheduleDao.incr_NUMBER();
		Schedule sch = new Schedule(ScheduleDao.get_NUMBER(), movie, theater, startDate, endDate, j1, j2, j3, time1, time2, time3);
		ScheduleDao.instance.getModel().put(ScheduleDao.get_NUMBER(), sch);

		servletResponse.sendRedirect("../create_schedule.html");
	}

	// Return the list of shedules for a movie
	@GET
	@Path("/movie/{movieId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Schedule> getShedulesOfMovie(@PathParam("movieId") int movieId) {
		List<Schedule> schedules = new ArrayList<Schedule>();
		// Ajout des séances pour ce film dans la ville
		for (Schedule sch : ScheduleDao.instance.getModel().values())
		{
			if (sch.getMovie().getId() == movieId)
				schedules.add(sch);
		}
		return schedules; 
	}

	// Return the list of shedules for a movie in a given city
	@GET
	@Path("/movie/{movieId}/{city}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Schedule> getShedulesOfMovieInCity(@PathParam("movieId") int movieId, @PathParam("city") String city) {
		List<Schedule> schedules = new ArrayList<Schedule>();
		// Ajout des séances pour ce film dans la ville
		for (Schedule sch : ScheduleDao.instance.getModel().values())
		{
			if (sch.getTheater().getCity().equalsIgnoreCase(city) && sch.getMovie().getId() == movieId && !schedules.contains(sch))
				schedules.add(sch);
		}
		return schedules; 
	}


	@Path("{schedule}")
	public ScheduleResource getSchedule(@PathParam("schedule") int id) {
		return new ScheduleResource(uriInfo, request, id);
	}

} 