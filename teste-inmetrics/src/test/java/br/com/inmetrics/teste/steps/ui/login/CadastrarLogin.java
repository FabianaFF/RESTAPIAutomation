package br.com.inmetrics.teste.steps.ui.login;

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

public class CadastrarLogin {
	
	static String testData = "src/test/resources/data/test_data.yaml";
	WebDriver driver;
	CadastroNovoLogin novoLoginPage;
	String pass;	
	String randomUser;

	@Before()
	public void before(Scenario scenario) {
		driver = this.driver= BrowserFactory.getInstance().getDriver(ConfigManager.getInstance().getConfigs().get("defaultDriver"));
		configureData();		
	}
	
	@Given("^Como usuário web não cadastrado$")
	public void acessarOpcaoCadastrate() {
		driver.get(ConfigManager.getInstance().getConfigs().get("webBase"));
		
		//Redirecidonando a tela de novo login
		Login loginPage = PageFactory.initElements(driver, Login.class);
		loginPage.doNovoCadastro();
		Assert.assertEquals(driver.getCurrentUrl(), "http://www.inmrobo.tk/accounts/signup/");
	}
	
	@When("^ao enviar todos os dados para cadastrar novo acesso$")
	public void preencheDadosCadstroNovoAcesso() {		
		novoLoginPage = PageFactory.initElements(driver, CadastroNovoLogin.class);
		novoLoginPage.doRegister(randomUser, pass, pass);		
	}
			
	@Then("^quero ter meu cadastro realizado com sucesso$")
	public void validarCadastroRealizado() {
		//Apos cadastro é realizado o redirecionamento a tela de login
		Assert.assertEquals(driver.getCurrentUrl(), "http://www.inmrobo.tk/accounts/login/");
		String novoCadastroFile = System.getProperty("user.dir")+"\\ScreenShots\\NovoLogin.png";
		
		try {EvidencesHelper.getInstance().getScreenShot(driver, novoCadastroFile);
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
		randomUser = new String("U" + random).substring(0, 8);
		pass = newLoginInfo.get("pasword").toString();
	}
	
}
