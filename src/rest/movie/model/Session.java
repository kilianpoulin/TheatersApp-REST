package rest.movie.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Session {
	private String id;
	private Owner owner;
	
	public Session() {
		
	}
	
	public Session(String id, Owner owner) {
		this.id = id;
		this.owner = owner;
	}
	
	public Owner getOwner() {
		return owner;
	}
	
	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
