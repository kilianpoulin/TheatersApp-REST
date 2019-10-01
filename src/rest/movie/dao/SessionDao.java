package rest.movie.dao;

import java.util.HashMap;
import java.util.Map;

import rest.movie.model.Owner;
import rest.movie.model.Session;

public enum SessionDao {
	instance;

	private Map<String, Session> contentProvider = new HashMap<String, Session>();

	private SessionDao() {

	}

	public Map<String, Session> getModel() {
		return contentProvider;
	}

	/**
	 * Vérifie si l'utilisateur s'est connecté avec l'ID de la session donné.
	 * @param sessionId ID de la session donné à la connexion.
	 * @return Owner correcpondant à la session ou null s'il ne s'est pas connecté.
	 */
	public Owner getLoggedInOwner(String sessionId) {
		Session s = contentProvider.get(sessionId);
		if (s == null)
			return null;
		else
			return s.getOwner();
	}
}
