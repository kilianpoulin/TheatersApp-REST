package rest.movie.model;

import java.time.DayOfWeek;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "schedule")
public class Schedule {

	private int id;
	private Movie movie;
	private Theater theater;
	private String startDate;
	private String endDate;
	private DayOfWeek day1;
	private DayOfWeek day2;
	private DayOfWeek day3;
	private String time1; // Starting time for day1
	private String time2; // Starting time for day2
	private String time3; // Starting time for day3

	public Schedule () {}
	
	public Schedule(int id,
			Movie movie, Theater theater,
			String startDate, String endDate,
			DayOfWeek day1, DayOfWeek day2, DayOfWeek day3, 
			String time1, String time2, String time3)
	{
		this.id = id;
		this.movie = movie;
		this.theater = theater;
		this.startDate = startDate;
		this.endDate = endDate;
		this.day1 = day1;
		this.day2 = day2;
		this.day3 = day3;
		this.time1 = time1;
		this.time2 = time2;
		this.time3 = time3;
	}

	@XmlElement(name = "id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@XmlElement(name = "movie")
	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	@XmlElement(name = "theater")
	public Theater getTheater() {
		return theater;
	}

	public void setTheater(Theater theater) {
		this.theater = theater;
	}

	@XmlElement(name = "startDate")
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	@XmlElement(name = "endDate")
	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	@XmlElement(name = "day1")
	public DayOfWeek getDay1() {
		return day1;
	}

	public void setDay1(DayOfWeek day1) {
		this.day1 = day1;
	}

	@XmlElement(name = "day2")
	public DayOfWeek getDay2() {
		return day2;
	}

	public void setDay2(DayOfWeek day2) {
		this.day2 = day2;
	}

	@XmlElement(name = "day3")
	public DayOfWeek getDay3() {
		return day3;
	}

	public void setDay3(DayOfWeek day3) {
		this.day3 = day3;
	}

	@XmlElement(name = "time1")
	public String getTime1() {
		return time1;
	}

	public void setTime1(String time1) {
		this.time1 = time1;
	}

	@XmlElement(name = "time2")
	public String getTime2() {
		return time2;
	}

	public void setTime2(String time2) {
		this.time2 = time2;
	}
	
	@XmlElement(name = "time3")
	public String getTime3() {
		return time3;
	}

	public void setTime3(String time3) {
		this.time3 = time3;
	}

}
