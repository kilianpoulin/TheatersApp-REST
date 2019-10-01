package rest.movie.dao;


import java.util.HashMap;
import java.util.Map;


import rest.movie.model.Theater;

public enum TheaterDao {
	instance;

	private Map<String, Theater> contentProvider = new HashMap<String, Theater>();

	private TheaterDao() {
		DBAccess dTransac = new DBAccess();
		String query = "SELECT * FROM THEATERS";
		contentProvider = dTransac.getTheaters(dTransac.getResultSet(dTransac.getStatement(dTransac.getConnection()),query));
	}
 
	public Map<String, Theater> getModel() {
		return contentProvider;
	}
}
