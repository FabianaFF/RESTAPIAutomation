package br.com.inmetrics.teste.steps.WEB.login;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.fasterxml.jackson.databind.JsonNode;

import br.com.inmetrics.teste.PageObjects.CadastroNovoLogin;
import br.com.inmetrics.teste.PageObjects.Login;
import br.com.inmetrics.teste.support.BrowserFactory;
import br.com.inmetrics.teste.support.ConfigManager;
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
	private Scenario scenario;
	private WebDriver driver;
	private CadastroNovoLogin novoLoginPage;
	private Login loginPage;
	private String pass;	
	private String user;

	@Before()
	public void before(Scenario scenario) {
		this.scenario = scenario;
		this.driver= BrowserFactory.getInstance().getDriver(ConfigManager.getInstance().getConfigs().get("defaultDriver"));
		driver.get(ConfigManager.getInstance().getConfigs().get("webBase"));
		configureData();		
	}
	
	@Given("^Como usuário web cadastrado$")
	public void acessarTelaLogin() {		
		loginPage = PageFactory.initElements(driver, Login.class);
	}
	
	@When("^ao enviar todos os dados para acessar sistema$")
	public void realizarLogin() {		
		loginPage.doLogin(user, pass);
	}
	
	@Then("^quero ter meu login realizado com sucesso$")
	public void validaAcesso() {
		try {
			driver.findElement(By.xpath("//div[contains(concat(' ',normalize-space(@class),' '),' alert-success ')]"));
		}catch(NoSuchElementException ex) {
			System.out.println("Driver Exception: "+ ex.getMessage());
			new AssertionError("Acesso não realizado com sucesso.");
		}		
		generateEvidence();
	}
	
	@After()
	public void tearDown()
	{
		if(driver != null)
			driver.quit();
	}
	
	private void generateEvidence() {
		//Salvando screenshot no report
		byte[] image = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
		this.scenario.embed(image, "image/png");
	}
	
	private void configureData() {
		JsonNode newLoginInfo = YamlHelper.getInstance().convertYamlToNode(testData, "loginDTO");
		user = newLoginInfo.get("email").asText();
		pass = newLoginInfo.get("pasword").asText();
	}
}
