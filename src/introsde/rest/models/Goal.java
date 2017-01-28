package introsde.rest.models;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Goal {
	private String type;
	private String value;
	private String valueType;
	
	public Goal(String type, String value, String valueType) {
		this.type = type;
		this.value = value;
		this.valueType = valueType;
	}
	
	public Goal() {

	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

}

