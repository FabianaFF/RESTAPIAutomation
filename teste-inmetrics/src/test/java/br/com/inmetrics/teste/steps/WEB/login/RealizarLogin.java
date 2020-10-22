package br.com.inmetrics.teste.steps.WEB.login;

import org.openqa.selenium.WebDriver;

import com.fasterxml.jackson.databind.JsonNode;

import br.com.inmetrics.teste.PageObjects.ListagemFuncionarios;
import br.com.inmetrics.teste.PageObjects.Login;
import br.com.inmetrics.teste.support.BrowserFactory;
import br.com.inmetrics.teste.support.ConfigManager;
import br.com.inmetrics.teste.support.YamlHelper;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import junit.framework.Assert;

public class RealizarLogin {
	static String testData = "src/test/resources/data/test_data.yaml";
	private WebDriver driver;
	private Login loginPage;
	private String pass;	
	private String user;

	@Before("@RealizarLoginFE")
	public void before(Scenario scenario) {		
		this.driver= BrowserFactory.getInstance().getDriver(ConfigManager.getInstance().getConfigs().get("defaultDriver"));
		driver.get(ConfigManager.getInstance().getConfigs().get("webBase"));
		configureData();		
	}
	
	@Given("^Como usuário web cadastrado$")
	public void acessarTelaLogin() {		
		loginPage = new Login(this.driver);
		loginPage.waitForPageLoaded();
	}
	
	@When("^ao enviar todos os dados para acessar sistema$")
	public void realizarLogin() {		
		loginPage.doLogin(user, pass);
	}
	
	@Then("^quero ter meu login realizado com sucesso$")
	public void validaAcesso() {
		try{
			ListagemFuncionarios listagemPage = new ListagemFuncionarios(this.driver);
			listagemPage.waitForPageLoaded();
			Assert.assertEquals(driver.getCurrentUrl(), "http://www.inmrobo.tk/empregados/");
		}catch(Exception ex) {
			System.out.println("===== Exception ====\n" + ex.getMessage());
			new AssertionError("Login não realizado com sucesso.");
		}
	}
		
	private void configureData() {
		JsonNode newLoginInfo = YamlHelper.getInstance().convertYamlToNode(testData, "loginDTO");
		user = newLoginInfo.get("email").asText();
		pass = newLoginInfo.get("pasword").asText();
	}
}
