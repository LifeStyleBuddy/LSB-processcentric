package introsde.rest.models;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Person {
	
	private String birthdate;
	private String email;
	private String lastname;
	private String name;
	private String idPerson;
	
	public Person() {

	}

	public Person(String birthdate, String email, String lastname, String name, String idPerson) {
		
		this.birthdate = birthdate;
		this.email = email;
		this.lastname = lastname;
		this.name = name;
		this.idPerson = idPerson;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdPerson() {
		return idPerson;
	}

	public void setIdPerson(String idPerson) {
		this.idPerson = idPerson;
	}


}
