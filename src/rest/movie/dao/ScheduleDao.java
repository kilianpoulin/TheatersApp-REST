package rest.movie.dao;

import java.util.HashMap;
import java.util.Map;

import rest.movie.model.Schedule;


public enum ScheduleDao {
	instance;

	private Map<Integer, Schedule> contentProvider = new HashMap<Integer, Schedule>();
	private static int NUMBER;
	private ScheduleDao() {
		DBAccess dTransac = new DBAccess();
		String query = "SELECT * FROM SESSIONS";
		contentProvider = dTransac.getSchedules(dTransac.getResultSet(dTransac.getStatement(dTransac.getConnection()),query));
	}

	public Map<Integer, Schedule> getModel(){
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


