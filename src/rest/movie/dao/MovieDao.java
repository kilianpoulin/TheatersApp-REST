package rest.movie.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rest.movie.model.Movie;


public enum MovieDao {
	instance;

	private Map<Integer, Movie> contentProvider = new HashMap<Integer, Movie>();
	private DBAccess dTransac = new DBAccess();
	private static int NUMBER;

	private MovieDao() {
		
		String query = "SELECT * FROM MOVIES";
		contentProvider = dTransac.getMovies(dTransac.getResultSet(dTransac.getStatement(dTransac.getConnection()),query));
	}
	
	public void InsertMovie(Movie m){
		ArrayList<String> actors = m.getActors();
		String actors_string = "";
		for(String s : actors) {
			actors_string += s;
			actors_string += ",";
		}
		String query = "INSERT INTO `movies`(`id`, `title`, `summary`, `duration`, `language`, `director`, `actors`, `minAge`) VALUES('" + m.getId() + "','" + m.getTitle() + "','" + m.getSummary() + "','" + m.getDuration()+ "','" + m.getLanguage() + "','" + m.getDirector() + "','" + actors_string+ "','" + m.getMinAge() + "')";
		dTransac.UpdateDB(query);
	}

	public Map<Integer, Movie> getModel(){
		return contentProvider;
	}
	
	public static int get_NUMBER() {
		return NUMBER;
	}
	
	public static void incr_NUMBER() {
		NUMBER++;
	}
	
	public static void set_NUMBER(int nb) {
		NUMBER = nb;
	}

} 