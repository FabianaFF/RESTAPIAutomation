package br.com.inmetrics.teste.steps.WEB.login;

import org.openqa.selenium.WebDriver;

import com.fasterxml.jackson.databind.JsonNode;

import br.com.inmetrics.teste.PageObjects.CadastroNovoLogin;
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

public class CadastrarLogin {
	
	static String testData = "src/test/resources/data/test_data.yaml";
	private WebDriver driver;
	private CadastroNovoLogin novoLoginPage;
	private Login loginPage;
	private String pass;	
	private String randomUser;

	@Before("@CadastrarLoginFE")
	public void before(Scenario scenario) {
		driver= BrowserFactory.getInstance().getDriver(ConfigManager.getInstance().getConfigs().get("defaultDriver"));
		driver.get(ConfigManager.getInstance().getConfigs().get("webBase"));
		configureData();		
	}
	
	@Given("^Como usuário web não cadastrado$")
	public void acessarOpcaoCadastrate() {
		loginPage = new Login(this.driver);
		loginPage.waitForPageLoaded();
		loginPage.doNovoCadastro();		
	}
	
	@When("^ao enviar todos os dados para cadastrar novo acesso$")
	public void preencheDadosCadstroNovoAcesso() {		
		novoLoginPage = new CadastroNovoLogin(this.driver);
		novoLoginPage.waitForPageLoaded();
		Assert.assertEquals(this.driver.getCurrentUrl(), "http://www.inmrobo.tk/accounts/signup/");
		novoLoginPage.doRegister(randomUser, pass, pass);		
	}
			
	@Then("^quero ter meu cadastro realizado com sucesso$")
	public void validarCadastro() {
		try{
			//Apos cadastro é realizado o redirecionamento a tela de login
			loginPage = new Login(this.driver);
			loginPage.waitForPageLoaded();
			loginPage.doLogin(randomUser, pass);
			
			ListagemFuncionarios listagemPage = new ListagemFuncionarios(this.driver);
			listagemPage.waitForPageLoaded();
			Assert.assertEquals(driver.getCurrentUrl(), "http://www.inmrobo.tk/empregados/");
		}catch(Exception ex) {
			System.out.println("===== Exception ====\n" + ex.getMessage());
			new AssertionError("Cadastro não realizado com sucesso.");
		}
	}
		
	private void configureData() {
		JsonNode newLoginInfo = YamlHelper.getInstance().convertYamlToNode(testData, "loginDTO");
		Long random = System.currentTimeMillis();
		randomUser = new String("U" + random).substring(0, 12);
		pass = newLoginInfo.get("pasword").toString();
	}
	
}
