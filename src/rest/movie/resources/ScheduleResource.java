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

import rest.movie.dao.ScheduleDao;
import rest.movie.model.Schedule;


public class ScheduleResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	int id;
	public ScheduleResource(UriInfo uriInfo, Request request, int id) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = id;
	}

	//Application integration     
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Schedule getSchedule() {
		Schedule schedule = ScheduleDao.instance.getModel().get(id);
		if(schedule==null)
			throw new RuntimeException("Get: Schedule with " + id +  " not found");
		return schedule;
	}

	// For the browser
	@GET
	@Produces(MediaType.TEXT_XML)
	public Schedule getScheduleHTML() {
		Schedule schedule = ScheduleDao.instance.getModel().get(id);
		if(schedule==null)
			throw new RuntimeException("Get: Schedule with " + id +  " not found");
		return schedule;
	}

	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response putSchedule(JAXBElement<Schedule> schedule) {
		Schedule c = schedule.getValue();
		return putAndGetResponse(c);
	}

	@DELETE
	public void deleteSchedule() {
		Schedule c = ScheduleDao.instance.getModel().remove(id);
		if(c==null)
			throw new RuntimeException("Delete: Schedule with " + id +  " not found");
	}

	private Response putAndGetResponse(Schedule schedule) {
		Response res;
		if(ScheduleDao.instance.getModel().containsKey(schedule.getId())) {
			res = Response.noContent().build();
		} else {
			res = Response.created(uriInfo.getAbsolutePath()).build();
		}
		ScheduleDao.instance.getModel().put(schedule.getId(), schedule);
		return res;
	}
} 