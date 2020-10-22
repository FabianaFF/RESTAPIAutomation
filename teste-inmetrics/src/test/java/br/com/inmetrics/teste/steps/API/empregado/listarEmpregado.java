package br.com.inmetrics.teste.steps.API.empregado;

import java.util.ArrayList;
import java.util.Map;

import org.junit.Assert;

import br.com.inmetrics.teste.support.ConfigManager;
import br.com.inmetrics.teste.support.CustomLogFilter;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class listarEmpregado {
	
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
		
	@Then("^verifico que o responseCode recebido pelo GET request é '(.*)'$")
	public void validarResponseCode(String expectedResponseCode) throws Throwable {
		Assert.assertEquals(response.getStatusCode(), Integer.parseInt(expectedResponseCode));
	}
	
	@Given("^Como usuário cadastrado com permissão de leitura de empregados$")
	public void setarCredenciaisAdm() throws Throwable {
		setAuthentication(ConfigManager.getInstance().getConfigs().get("admUser"),
					ConfigManager.getInstance().getConfigs().get("admPass"));		
	} 
	
	@When("^ao enviar o GET request para a API$")
	public void enviarGET() throws Throwable {		
		response = request.get("/empregado/list_all");		

		//Logging request and response at report using custom filter
		scenario.log("\n  API request:" + filter.getRequestBuilder() +
				"\n  API response:" + filter.getResponseBuilder());
	}

	@And("^verifico que o response contem uma lista de empregados$")
	public void validarDadosDoResponse() throws Throwable {
		JsonPath jsonPath = response.body().jsonPath();		
		Assert.assertNotEquals(jsonPath.getList("$").size(), 0);
	}
	
	@Then("verifico se response mostra todos os dados esperados de cada empregados$")
	public void validarDadosMandatoriosGET() throws Throwable {
		Map<String, Object> elemetFromResponse =  (Map<String, Object>) response.body().jsonPath().getList("").get(0);
		ArrayList<String> fields = new ArrayList<String>();
		
		ArrayList<String> expectedFields = new ArrayList<String>();
		//expectedFields.add("acesso");
		expectedFields.add("admissao");
		expectedFields.add("cargo");
		expectedFields.add("comissao");
		expectedFields.add("cpf");
		//expectedFields.add("departamento");
		expectedFields.add("empregadoId");
		expectedFields.add("nome");
		expectedFields.add("salario");
		expectedFields.add("sexo");
		expectedFields.add("tipoContratacao");
	
		System.out.println("Validando dados:\n");
		
		expectedFields.forEach(field -> {
			System.out.println(field +" is present!");
			Assert.assertTrue(elemetFromResponse.containsKey(field));
		});	
		
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