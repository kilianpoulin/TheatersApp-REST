package rest.movie.dao;

import java.util.HashMap;
import java.util.Map;

import rest.movie.model.Owner;

public enum OwnerDao {
	  instance;
	  
	  private Map<String, Owner> contentProvider = new HashMap<String, Owner>();
	  
	  private OwnerDao() {
		  DBAccess dTransac = new DBAccess();
			String query = "SELECT * FROM OWNERS";
			contentProvider = dTransac.getOwners(dTransac.getResultSet(dTransac.getStatement(dTransac.getConnection()),query));
	    
	  }
	  
	  public Map<String, Owner> getModel(){
	    return contentProvider;
	  }
}
