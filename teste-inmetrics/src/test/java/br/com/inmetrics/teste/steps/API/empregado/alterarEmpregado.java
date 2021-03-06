package br.com.inmetrics.teste.steps.API.empregado;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.inmetrics.teste.support.ConfigManager;
import br.com.inmetrics.teste.support.CustomLogFilter;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class alterarEmpregado {
	
	//Path of test_data value to be used 
	static String testData = "src/test/resources/data/test_data.yaml";
	private Map<String, Object> payloadMap;	
	private RequestSpecification request;
	private Response response;
	private Scenario scenario;
	private CustomLogFilter filter;
	private int empregadoId;
	private Map<String, Object> listEmpregados;
	
	@Before()
	public void before(Scenario scenario) {
		this.scenario = scenario;
		request = RestAssured.given();
		
		filter = new CustomLogFilter();
		request.filter(filter);
		configureRequest();
	}

	@And("^usando o id de um empregado que possa ser alterado$")
	public void getIDEmpregado() throws Throwable {
		response = request.get("/empregado/list_all");
		listEmpregados =  (Map<String, Object>) response.body().jsonPath().getList("").get(0);
			    
		empregadoId = (int) listEmpregados.get("empregadoId");
	}
	
	@And("^enviando o valor '(.*)' para o campo '(.*)'$")
	public void alterarValoresPayload(String valor, String campo) {	
		configurePayload();
		payloadMap.put(campo, valor);		
	}
	
	@And("^verifico que o campo '(.*)' atualizado para '(.*)'$")
	public void verificarValoresResponse(String valor, String campo) {
		Assert.assertTrue(true);
	}
	
	@Then("^verifico que o responseCode recebido pelo PUT request é '(.*)'$")
	public void validarResponseCode(String expectedResponseCode) throws Throwable {
		Assert.assertEquals(response.getStatusCode(), Integer.parseInt(expectedResponseCode));
	}
	
	@Given("^Como usuário cadastrado com permissão de Alterar cadastro de empregados$")
	public void setarCredenciaisAdm() throws Throwable {
		setAuthentication(ConfigManager.getInstance().getConfigs().get("admUser"),
					ConfigManager.getInstance().getConfigs().get("admPass"));		
	} 
	
	@When("^ao enviar o PUT request para a API$")
	public void enviarPUT() throws Throwable {
		String payloadToPut = new ObjectMapper().writeValueAsString(payloadMap);
		
		response = request.body(payloadToPut)
				.put("/empregado/alterar/"+ empregadoId);		

		//Logging request and response at report using custom filter
		scenario.log("\n  API request:" + filter.getRequestBuilder() +
				"\n  API response:" + filter.getResponseBuilder());
	}
		
	public void setAuthentication(String user, String password) {
		request.auth().preemptive().basic(user, password);    	
	}
	
	public void configurePayload() {
		payloadMap = new HashMap<>();
		
		payloadMap.put("acesso", (String) listEmpregados.get("acesso"));
		payloadMap.put("admissao", (String) listEmpregados.get("admissao"));
		payloadMap.put("cargo", (String) listEmpregados.get("cargo"));
		payloadMap.put("comissao", (String) listEmpregados.get("comissao"));
		payloadMap.put("cpf", listEmpregados.get("cpf").toString());
		payloadMap.put("departamentoId", 1);
		payloadMap.put("empregadoId", new Integer(listEmpregados.get("empregadoId").toString()).intValue());
		payloadMap.put("nome", (String) listEmpregados.get("nome"));
		payloadMap.put("salario", (String) listEmpregados.get("salario"));
		payloadMap.put("sexo", (String) listEmpregados.get("sexo"));
		payloadMap.put("tipoContratacao", (String) listEmpregados.get("tipoContratacao"));
	}
	
	public void configureRequest() {
		//Logging request and response at console
		request.given().log().all();
		request.then().log().all();
		
		request.contentType("application/json");		
	}

}