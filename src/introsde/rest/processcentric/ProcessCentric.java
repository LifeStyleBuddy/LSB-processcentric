package introsde.rest.processcentric;

import introsde.rest.models.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.ejb.*;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.glassfish.jersey.client.ClientConfig;
import org.json.*;

import javax.xml.ws.Holder;

@Stateless
@LocalBean
@Path("/pcentric")
public class ProcessCentric {

	@POST
	@Path("/newMeasure/{personId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response newMeasure(@PathParam("personId") int idPerson, Measure measure) throws IOException {

		// Reading the person info
		String ENDPOINT1 = "https://aqueous-thicket-19730.herokuapp.com/introsde/storage/getPersonInfo/" + idPerson;

		DefaultHttpClient client1 = new DefaultHttpClient();
		HttpGet request1 = new HttpGet(ENDPOINT1);
		HttpResponse response1 = client1.execute(request1);

		BufferedReader rd1 = new BufferedReader(new InputStreamReader(response1.getEntity().getContent()));
		StringBuffer result1 = new StringBuffer();
		String line1 = "";

		while ((line1 = rd1.readLine()) != null) {
			result1.append(line1);
		}

		JSONObject o1 = new JSONObject(result1.toString());

		if (response1.getStatusLine().getStatusCode() != 200) {
			System.out.println("Personal Info URL return nothing");
			return Response.status(204).build();
		}

		// Update measures of life status
		String ENDPOINT = "https://aqueous-thicket-19730.herokuapp.com/introsde/storage/newMeasure/" + idPerson;
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);

		WebTarget service = client.target(ENDPOINT);
		Response res = null;
		String putResp = null;

		String newM = "{" + "\"type\":\"" + measure.getType() + "\"," + "\"valueType\":\"" + measure.getValueType()
				+ "\"," + "\"value\":\"" + measure.getValue() + "\"}";

		res = service.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(newM));
		putResp = res.readEntity(String.class);

		if (res.getStatus() != 200) {
			return Response.status(400).build();
		}

		// reading the current Goal of person:

		// BAD WAY TO CHECK IF THERE IS A NULL INSIDE THE STRING (no other way
		// found)
		String test = o1.getJSONObject("currentGoal").toString();
		String test2 = "null";
		
		String result = "";

		if (!test.toLowerCase().contains(test2.toLowerCase())) {
			
			JSONArray arr2 = o1.getJSONObject("currentGoal").getJSONArray("goal");
			for (int i = 0; i < arr2.length(); i++) {

				String type = arr2.getJSONObject(i).getString("type");
				String value = arr2.getJSONObject(i).getString("value");
				int valueint = Integer.parseInt(value);
				String nmeasuretype = measure.getType();
				String nmeasurevalue = measure.getValue();
				int nmeasurevalueint = Integer.parseInt(nmeasurevalue);
				
				if (nmeasuretype.equals(type)){
					if (nmeasurevalueint > valueint){
						if(nmeasuretype.equals("steps") || nmeasuretype.equals("wtime") || nmeasuretype.equals("sleep")){
							result = "Congratulation! you reached your "+ nmeasuretype + " goal!";
						}
						if(nmeasuretype.equals("weight")){
							result = "C'mon! work hard to improve "+ nmeasuretype;
						}
					}
					if (nmeasurevalueint < valueint){
						if(nmeasuretype.equals("weight")){
							result = "Congratulation! you reached your "+ nmeasuretype + " goal!";
						}
						if(nmeasuretype.equals("steps") || nmeasuretype.equals("wtime") || nmeasuretype.equals("sleep")){
							result = "C'mon! work hard to improve "+ nmeasuretype;
						}
					}
				}

			}
		}

		// Prepare the xml--> to send back

		String textXml = "";
		textXml = "<result>" + result + "</result>";

		JSONObject xmlJSONObj = XML.toJSONObject(textXml);

		String Json = xmlJSONObj.toString(4);

		System.out.println(Json);
		return Response.ok(Json).build();
	}

	@POST
	@Path("/newGoal/{personId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response newGoal(@PathParam("personId") int idPerson, Goal goal) throws IOException {

		// Update measures of life status
		String ENDPOINT = "https://aqueous-thicket-19730.herokuapp.com/introsde/storage/newGoal/" + idPerson;
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);

		WebTarget service = client.target(ENDPOINT);
		Response res = null;
		String putResp = null;

		String newG = "{" + "\"type\":\"" + goal.getType() + "\"," + "\"valueType\":\"" + goal.getValueType() + "\","
				+ "\"value\":\"" + goal.getValue() + "\"}";

		res = service.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(newG));
		putResp = res.readEntity(String.class);

		if (res.getStatus() != 200) {
			return Response.status(400).build();
		}

		// Prepare the xml--> to send back

		String result = "New goal correctly inserted!";
		String textXml = "";
		textXml = "<result>" + result + "</result>";

		JSONObject xmlJSONObj = XML.toJSONObject(textXml);

		String Json = xmlJSONObj.toString(4);

		System.out.println(Json);
		return Response.ok(Json).build();
	}

	// Create Person is not working well but it's ready
	/*
	 * @POST
	 * 
	 * @Path("/createPerson")
	 * 
	 * @Produces(MediaType.APPLICATION_JSON)
	 * 
	 * @Consumes(MediaType.APPLICATION_JSON) public Response createPerson(Person
	 * person) throws IOException {
	 * 
	 * // Update measures of life status String ENDPOINT =
	 * "https://aqueous-thicket-19730.herokuapp.com/introsde/storage/createPerson";
	 * ClientConfig clientConfig = new ClientConfig(); Client client =
	 * ClientBuilder.newClient(clientConfig);
	 * 
	 * WebTarget service = client.target(ENDPOINT); Response res = null; String
	 * putResp = null;
	 * 
	 * String parentesi = "{}";
	 * 
	 * String newP = "{" + "\"birthdate\":\"" + person.getBirthdate() + "\"," +
	 * "\"email\":\"" + person.getEmail() + "\"," + "\"lastname\":\"" +
	 * person.getLastname() + "\"," + "\"name\":\"" + person.getName() + "\"}";
	 * 
	 * 
	 * System.out.println(newP);
	 * 
	 * res =
	 * service.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(newP));
	 * putResp = res.readEntity(String.class);
	 * 
	 * if (res.getStatus() != 200) { return Response.status(400).build(); }
	 * 
	 * // Prepare the xml--> to send back
	 * 
	 * String result = "New person correctly created!"; String textXml = "";
	 * textXml = "<result>" + result + "</result>";
	 * 
	 * 
	 * JSONObject xmlJSONObj = XML.toJSONObject(textXml);
	 * 
	 * String Json = xmlJSONObj.toString(4);
	 * 
	 * System.out.println(Json); return Response.ok(Json).build(); }
	 */

}
