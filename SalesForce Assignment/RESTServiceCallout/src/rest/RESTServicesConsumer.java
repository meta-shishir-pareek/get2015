package rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class RESTServicesConsumer {

	static final String USERNAME = "shishirpareek@metacube.com";
	static final String PASSWORD = "shishir#123dQti8jIUVIsOsjqFKj3Mr6XX1";
	static final String LOGINURL = "https://login.salesforce.com";
	static final String GRANTSERVICE = "/services/oauth2/token?grant_type=password";
	static final String CLIENTID = "3MVG9ZL0ppGP5UrAUv..VKZjsb89.ZaUaTx8MMvz7Zzp7MTD9yUUcc2AVrMgSzvHaxomRmZ8mh6Ku_pm_IWFc";
	static final String CLIENTSECRET = "7953799018493999961";
	private static String REST_ENDPOINT = "/services/data";
	private static String API_VERSION = "/v32.0";
	private static String baseUri;
	private static Header oauthHeader;
	private static Header prettyPrintHeader = new BasicHeader("X-PrettyPrint",
			"1");
	private static String ContactId;
	private static String ContactName;
	private static String tName;
	private static String leadCompany;

	public static void main(String[] args) throws ParseException, IOException {

		 JSONObject authJsonObject=OAuthServlet.oAuthSessionProvider(LOGINURL, USERNAME, PASSWORD,CLIENTID, CLIENTSECRET);
		/*HttpClient httpclient = HttpClientBuilder.create().build();

		// Assemble the login request URL
		String loginURL = LOGINURL + GRANTSERVICE + "&client_id=" + CLIENTID
				+ "&client_secret=" + CLIENTSECRET + "&username=" + USERNAME
				+ "&password=" + PASSWORD;

		// Login requests must be POSTs
		HttpPost httpPost = new HttpPost(loginURL);
		HttpResponse response = null;

		try {
			// Execute the login POST request
			response = httpclient.execute(httpPost);
		} catch (ClientProtocolException cpException) {
			cpException.printStackTrace();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}

		// verify response is HTTP OK
		final int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode != HttpStatus.SC_OK) {
			System.out.println("Error authenticating to Force.com: "
					+ statusCode);
			System.out.println(EntityUtils.toString(response.getEntity()));
			// Error is in EntityUtils.toString(response.getEntity())
			return;
		}

		String getResult = null;
		try {
			getResult = EntityUtils.toString(response.getEntity());
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}

		JSONObject jsonObject = null;*/
		String loginAccessToken = null;
		String loginInstanceUrl = null;

		try {
			//authJsonObject = (JSONObject) new JSONTokener(getResult).nextValue();
			loginAccessToken = authJsonObject.getString("access_token");
			loginInstanceUrl = authJsonObject.getString("instance_url");
		} catch (JSONException jsonException) {
			jsonException.printStackTrace();
		}

		baseUri = "https://ap2.salesforce.com" + REST_ENDPOINT + API_VERSION;
		oauthHeader = new BasicHeader("Authorization", "OAuth "
				+ loginAccessToken);
		System.out.println("oauthHeader1: " + oauthHeader);
		//System.out.println("\n" + response.getStatusLine());
		System.out.println("Successful login");
		//System.out.println("instance URL: " + loginInstanceUrl);
		System.out.println("access token/session ID: " + loginAccessToken);
		System.out.println("baseUri: " + baseUri);

		// Run codes to query, insert, update and delete records in Salesforce
		// using REST API
		queryContacts();
		createStudents();
		//updateLeads();
		//deleteLeads();

		// release connection
		//httpPost.releaseConnection();
	}

	// Query Leads using REST HttpGet
	public static void queryContacts() {
		System.out.println("\n_______________ Contacts QUERY _______________");
		try {

			// Set up the HTTP objects needed to make the request.
			HttpClient httpClient = HttpClientBuilder.create().build();

			String uri = baseUri
					+ "/query?q=SELECT+Id+,+Name,+Field_Experience__c,+Languages__c,+Level__c,+Next_Birthday__c,+Subjects__c+FROM+Contact";
			System.out.println("Query URL: " + uri);
			HttpGet httpGet = new HttpGet(uri);
			System.out.println("oauthHeader2: " + oauthHeader);
			httpGet.addHeader(oauthHeader);
			httpGet.addHeader(prettyPrintHeader);

			// Make the request.
			HttpResponse response = httpClient.execute(httpGet);

			// Process the result
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				String response_string = EntityUtils.toString(response
						.getEntity());
				try {
					JSONObject json = new JSONObject(response_string);
					System.out.println("JSON result of Query:\n"
							+ json.toString(1));
				} catch (JSONException je) {
					je.printStackTrace();
				}
			} else {
				System.out
						.println("Query was unsuccessful. Status code returned is "
								+ statusCode);
				System.out.println("An error has occured. Http status: "
						+ response.getStatusLine().getStatusCode());
				System.out.println(getBody(response.getEntity().getContent()));
				System.exit(-1);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (NullPointerException npe) {
			npe.printStackTrace();
		}
	}

	// Create Leads using REST HttpPost
	public static void createStudents() {
		System.out.println("\n_______________ Student INSERT _______________");

		String uri = baseUri + "/sobjects/Student__c/";
		try {

			// create the JSON object containing the new lead details.
			JSONObject student = new JSONObject();
			student.put("Name","DeadPool");
			student.put("First_Name__c", "Wade");
			student.put("Last_Name__c", "Wilson");
			student.put("Class__c", "a0828000008V87E");

			System.out.println("JSON for lead record to be inserted:\n"
					+ student.toString(1));

			// Construct the objects needed for the request
			HttpClient httpClient = HttpClientBuilder.create().build();

			HttpPost httpPost = new HttpPost(uri);
			httpPost.addHeader(oauthHeader);
			httpPost.addHeader(prettyPrintHeader);
			// The message we are going to post
			StringEntity body = new StringEntity(student.toString(1));
			body.setContentType("application/json");
			httpPost.setEntity(body);

			// Make the request
			HttpResponse response = httpClient.execute(httpPost);

			// Process the results
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 201) {
				String response_string = EntityUtils.toString(response
						.getEntity());
				JSONObject json = new JSONObject(response_string);
				// Store the retrieved lead id to use when we update the lead.
				//leadId = json.getString("id");
				//System.out.println("New Lead id from response: " + leadId);
			} else {
				System.out.println("Insertion unsuccessful. Status code returned is "
								+ statusCode);
				System.out.println(EntityUtils.toString(response
						.getEntity()));
			}
		} catch (JSONException e) {
			System.out.println("Issue creating JSON or processing results");
			e.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (NullPointerException npe) {
			npe.printStackTrace();
		}
	}

	private static String getBody(InputStream inputStream) {
		String result = "";
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					inputStream));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				result += inputLine;
				result += "\n";
			}
			in.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return result;
	}
}
