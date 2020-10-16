package br.com.inmetrics.teste.steps;

import org.junit.Assert;

import com.fasterxml.jackson.databind.JsonNode;

import br.com.inmetrics.teste.support.ConfigManager;
import br.com.inmetrics.teste.support.CustomLogFilter;
import br.com.inmetrics.teste.support.YamlHelper;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class cadastrarEmpregado {

	//Path of test_data value to be used 
	static String testData = "src/test/resources/data/test_data.yaml";
	private JsonNode payload;
	private RequestSpecification request;
	private Response response;
	private Scenario scenario;
	private CustomLogFilter filter;	

	@Before()
	public void before(Scenario scenario) {
		this.scenario = scenario;
		request = RestAssured.given();
		
		filter = new CustomLogFilter();
		request.filter(filter);
		configureRequest();
	}

	@Given("^Como usuário não cadastrado$")
	public void setarCredenciaisFake() throws Throwable {		
		setAuthentication("FakeUser", "FakePass");
	} 

	@Given("Como usuário cadastrado com permissão de criaçao de empregados$")
	public void setarCredenciaisAdm() throws Throwable {
		setAuthentication(ConfigManager.getInstance().getConfigs().get("admUser"),
				ConfigManager.getInstance().getConfigs().get("admPass"));		
	} 

	@And("^forneço todos os dados para cadastrar novo empregado$")
	public void configurarTodosDadosDoPayload() throws Throwable {
		payload = YamlHelper.getInstance().convertYamlToNode(testData, "empregadoDTO");
		request.body(payload.toString());
	}

	@And("^forneço dados com formato errado para cadastrar novo empregado$")
	public void configurarDadosErradosNoPayload() throws Throwable {
		payload = YamlHelper.getInstance().convertYamlToNode(testData, "empregadoDTO3");
		request.body(payload.toString());
	}

	@When("^envio o request para a API$")
	public void enviarPost() throws Throwable {
		response = request.post("/empregado/cadastrar");

		//Logging request and response at report using custom filter
		scenario.write("\n  API request:" + filter.getRequestBuilder() +
				"\n  API response:" + filter.getResponseBuilder());
	}

	@Then("^verifico que os dados foram persistidos corretamente$")
	public void validarDadosDoResponse() throws Throwable {		
		JsonPath responseJson = response.getBody().jsonPath();
		
		System.out.println("Validando dados...\n");
		payload.fields().forEachRemaining(entry ->
         		Assert.assertTrue(
				   compare(responseJson.get(entry.getKey()),
				             entry.getValue())
				)	
		);
	}

	@Then("^valido o responseCode '(.*)'$")
	public void validarResponseCode(String expectedResponseCode) throws Throwable {
		Assert.assertEquals(response.getStatusCode(), Integer.parseInt(expectedResponseCode));
	}

	public void setAuthentication(String user, String password) {
		request.auth().preemptive().basic(user, password);    	
	}

	public void configureRequest() {
		//Logging request and response at console
		request.given().log().all();
		request.then().log().all();
		
		request.contentType("application/json");
	}
	
	public boolean compare (Object responseValue, JsonNode payloadValue) {
		boolean isEquals = false;		
				
		   if(payloadValue.isTextual()) {			   
			   System.out.println(" responseValue: " + responseValue + " Payload:" +  payloadValue.asText());
			   isEquals = (((String) responseValue).compareTo(payloadValue.asText())) == 0 ? true : false;
		   }else if (payloadValue.isInt()){
			   //Some data at payload are not being returned at response like departmentoId			   
			   if(responseValue != null) {
			     System.out.println(" responseValue: " + new Integer((int) responseValue).intValue() + " Payload:" +  payloadValue.asInt());
			     isEquals = new Integer((int) responseValue).intValue() == payloadValue.asInt() ? true : false;
			   }else {
				   isEquals = true;
			   }
		   }
		   
		   return isEquals;
	}
	
}