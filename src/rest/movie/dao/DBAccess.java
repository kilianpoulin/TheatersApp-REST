package rest.movie.dao;

import java.sql.Statement;
import java.time.DayOfWeek;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import rest.movie.model.*;


public class DBAccess {
	private Connection dbConn;
    private Statement stmt;
    private ResultSet rs;
    private String dbUrl = "jdbc:mysql://localhost/allocinev2?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private String user = "root";
    private String pwd = "";
    
    public Connection getConnection() {
    	
    	try {
	    	Class.forName("com.mysql.jdbc.Driver"); 
	        dbConn = DriverManager.getConnection(dbUrl, user, pwd);
     } catch (SQLException | ClassNotFoundException e) {
    	 System.out.println(e);
     }
    	return dbConn;
    }
    
    public Statement getStatement(Connection dbConn) {
        try {
            stmt = dbConn.createStatement();
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
        return stmt;
    }

    public ResultSet getResultSet(Statement stmt, String query) {

        try {
            rs = stmt.executeQuery(query);
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
        return rs;
    }
    
    public HashMap<Integer, Movie> getMovies(ResultSet rs){
    	HashMap<Integer, Movie> MovieList = new HashMap<Integer, Movie>();
    	 try {
    		 int i = 0;
			while (rs.next()) {
				Movie movie = new Movie(rs.getInt("id"), rs.getString("title"), OwnerDao.instance.getModel().get(String.valueOf(rs.getInt("owner_id"))));
				movie.setSummary(rs.getString("summary"));
				movie.setDirector(rs.getString("director"));
				movie.setDuration(rs.getInt("duration"));
				movie.setLanguage(rs.getString("language"));
				movie.setMinAge(rs.getInt("minAge"));
				
				String [] temp = rs.getString("actors").split(",");
				ArrayList<String> actors_list = new ArrayList<String>();
				for(String s : temp) {
					actors_list.add(s);
				}
				movie.setActors(actors_list);
				
				MovieList.put(rs.getInt("id"), movie);
				
				i++;
			}
	    	 MovieDao.set_NUMBER(i);
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
		}
    	return MovieList;
    }
    
    public HashMap<String, Owner> getOwners(ResultSet rs){
    	HashMap<String, Owner> OwnerList = new HashMap<String, Owner>();
    	 try {
			while (rs.next()) {
				Owner owner = new Owner(String.valueOf(rs.getInt("id")), rs.getString("username"), rs.getString("password"));
				owner.setTheater(TheaterDao.instance.getModel().get(String.valueOf(rs.getInt("theater_id"))));
				OwnerList.put(String.valueOf(rs.getString("id")), owner);
			 }
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
		}
    	return OwnerList;
    }
    
    public HashMap<Integer, Schedule> getSchedules(ResultSet rs){
    	HashMap<Integer, Schedule> ScheduleList = new HashMap<Integer, Schedule>();
    	 try {
    		 int i = 0;
			while (rs.next()) {
				String[] days = rs.getString("listOfDates").split(","); 
				String[] hours = rs.getString("listOfTimes").split(",");
				Movie m = MovieDao.instance.getModel().get(rs.getInt("movie_id"));
				Theater t = TheaterDao.instance.getModel().get(String.valueOf(rs.getInt("theater_id")));
				Schedule schedule = new Schedule(rs.getInt("id"), m , t, rs.getString("startDate"), rs.getString("endDate"), 
						DayOfWeek.valueOf(days[0]), DayOfWeek.valueOf(days[1]), DayOfWeek.valueOf(days[2]), 
						hours[0], hours[1], hours[2]
						);
				
				ScheduleList.put(rs.getInt("id"), schedule);
				i++;
			 }
			ScheduleDao.set_NUMBER(i);
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
		}
    	return ScheduleList;
    }
    
    public HashMap<String, Theater> getTheaters(ResultSet rs){
    	HashMap<String, Theater> TheaterList = new HashMap<String, Theater>();
    	 try {
			while (rs.next()) {
				Theater theater = new Theater(rs.getString("id"), rs.getString("name"), rs.getString("address"));
				TheaterList.put(rs.getString("id"), theater);
			 }
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
		}
    	return TheaterList;
    }
    
    public void UpdateDB(String query) {
    	try {
			getStatement(getConnection()).executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }


}
