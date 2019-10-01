package rest.movie.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "theater")
public class Theater {
	private String id;
	private String name;
	private String city;
	
	public Theater() {
		
	}
	
	public Theater(String id, String name, String city) {
		this.id = id;
		this.name = name;
		this.city = city;
	}
	

	@XmlElement(name = "id")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@XmlElement(name = "name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@XmlElement(name = "city")
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
}
