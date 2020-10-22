package br.com.inmetrics.teste.steps.API.empregado;

import java.util.ArrayList;

import org.junit.Assert;

import br.com.inmetrics.teste.support.ConfigManager;
import br.com.inmetrics.teste.support.CustomLogFilter;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class deletarEmpregado {
	
	private RequestSpecification request;
	private Response response;
	private Scenario scenario;
	private CustomLogFilter filter;
	private int empregadoId;
	
	@Before()
	public void before(Scenario scenario) {
		this.scenario = scenario;
		request = RestAssured.given();
		
		filter = new CustomLogFilter();
		request.filter(filter);
		configureRequest();
	}
		
	@Then("^verifico que o responseCode recebido pelo DELETE request é '(.*)' ou '(.*)'$")
	public void validarResponseCode(String expectedResponseCode1, String expectedResponseCode2) throws Throwable {
		ArrayList<Integer> resultCodes = new ArrayList<Integer>();
		resultCodes.add(new Integer(expectedResponseCode1));
		resultCodes.add(new Integer(expectedResponseCode2));
		
		Assert.assertTrue(resultCodes.contains(new Integer(response.getStatusCode())));
	}
	
	@Given("^Como usuário cadastrado com permissão de Deleção de empregados$")
	public void setarCredenciaisAdm() throws Throwable {
		setAuthentication(ConfigManager.getInstance().getConfigs().get("admUser"),
					ConfigManager.getInstance().getConfigs().get("admPass"));		
	} 
	
	@When("^ao enviar o DELETE request para a API$")
	public void enviarGET() throws Throwable {		
		response = request.delete("/empregado/deletar/" + empregadoId);		

		//Logging request and response at report using custom filter
		scenario.write("\n  API request:" + filter.getRequestBuilder() +
				"\n  API response:" + filter.getResponseBuilder());
	}

	@And("^usando o id de um empregado ja cadastrado$")
	public void getIDEmpregado() throws Throwable {
		empregadoId = request.get("/empregado/list_all")
				.body().jsonPath().get("[0].empregadoId");
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
}