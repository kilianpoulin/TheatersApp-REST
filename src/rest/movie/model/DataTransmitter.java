package rest.movie.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "datatransmitter")
public class DataTransmitter {
	private Movie movie;
	private Theater theater;
	private Schedule schedule;
	
	public DataTransmitter(Movie movie, Theater theater, Schedule schedule) {
		this.setMovie(movie);
		this.setTheater(theater);
		this.setSchedule(schedule);
	}
	
	@XmlElement(name = "schedule")
	public Schedule getSchedule() {
		return schedule;
	}
	
	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}
	
	@XmlElement(name = "theater")
	public Theater getTheater() {
		return theater;
	}
	
	public void setTheater(Theater theater) {
		this.theater = theater;
	}
	
	@XmlElement(name = "movie")
	public Movie getMovie() {
		return movie;
	}
	public void setMovie(Movie movie) {
		this.movie = movie;
	}
}
