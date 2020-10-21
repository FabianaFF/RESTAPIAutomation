package br.com.inmetrics.teste.steps.ui.login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.fasterxml.jackson.databind.JsonNode;

import br.com.inmetrics.teste.PageObjects.CadastroNovoLogin;
import br.com.inmetrics.teste.PageObjects.Login;
import br.com.inmetrics.teste.support.BrowserFactory;
import br.com.inmetrics.teste.support.ConfigManager;
import br.com.inmetrics.teste.support.EvidencesHelper;
import br.com.inmetrics.teste.support.YamlHelper;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import junit.framework.Assert;

public class RealizarLogin {
	static String testData = "src/test/resources/data/test_data.yaml";
	WebDriver driver;
	CadastroNovoLogin novoLoginPage;
	String pass;	
	String user;

	@Before()
	public void before(Scenario scenario) {
		driver = this.driver= BrowserFactory.getInstance().getDriver(ConfigManager.getInstance().getConfigs().get("defaultDriver"));
		configureData();		
	}
	
	@Given("^Como usuário web cadastrado$")
	public void acessarTelaLogin() {
		driver.get(ConfigManager.getInstance().getConfigs().get("webBase"));
	}
	
	@When("^ao enviar todos os dados para acessar sistema$")
	public void realizarLogin() {
		Login loginPage = PageFactory.initElements(driver, Login.class);
		loginPage.doLogin(user, pass);
	}
	
	@Then("^quero ter meu login realizado com sucesso$")
	public void validaAcesso() {
		Assert.assertEquals(driver.getCurrentUrl(), "http://www.inmrobo.tk/empregados/");
		String lofingFile = System.getProperty("user.dir")+"\\ScreenShots\\RealizarLogin.png";
		
		try {EvidencesHelper.getInstance().getScreenShot(driver, lofingFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@After
	public void tearDown()
	{
		driver.quit();
	}
	
	private void configureData() {
		JsonNode newLoginInfo = YamlHelper.getInstance().convertYamlToNode(testData, "loginDTO");
		Long random = System.currentTimeMillis();
		user = newLoginInfo.get("email").asText();
		pass = newLoginInfo.get("pasword").asText();
	}
}
