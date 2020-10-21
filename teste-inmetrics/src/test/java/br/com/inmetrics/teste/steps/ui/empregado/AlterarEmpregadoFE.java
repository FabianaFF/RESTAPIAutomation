package br.com.inmetrics.teste.steps.ui.empregado;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.fasterxml.jackson.databind.JsonNode;

import br.com.inmetrics.teste.PageObjects.EdicaoFuncionario;
import br.com.inmetrics.teste.PageObjects.ListagemFuncionarios;
import br.com.inmetrics.teste.PageObjects.Login;
import br.com.inmetrics.teste.support.BrowserFactory;
import br.com.inmetrics.teste.support.ConfigManager;
import br.com.inmetrics.teste.support.EvidencesHelper;
import br.com.inmetrics.teste.support.YamlHelper;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import junit.framework.Assert;

public class AlterarEmpregadoFE {
	
	static String testData = "src/test/resources/data/test_data.yaml";
	private Scenario scenario;
	private ListagemFuncionarios listagemPage;
	private EdicaoFuncionario edicaoPage;
	private WebDriver driver;	
	private String pass;	
	private String user;
	
	@Before()
	public void before(Scenario scenario) {
		this.scenario = scenario;
		this.driver = BrowserFactory.getInstance().getDriver(ConfigManager.getInstance().getConfigs().get("defaultDriver"));
		this.driver.get(ConfigManager.getInstance().getConfigs().get("webBase"));
		configureData();
	}
	
	@Given("^Como usuário web cadastrado e logado com permissão de alteração$")
	public void realizaAcesso() {		
		Login loginPage =  PageFactory.initElements(this.driver, Login.class);
		loginPage.doLogin(user, pass);	
	}
	
	@When("^ao selecionar funcionário para alterar na listagem$")
	public void selecionarEmpregado() {		
		listagemPage = PageFactory.initElements(this.driver, ListagemFuncionarios.class);
		listagemPage.doSelecionaPrimeiroFuncionario();
		listagemPage.doEditarFuncionario();
	}
	
	@And("^enviar as alterações de dados do empregado$")
	public void alterarEmpregado() {		
		EdicaoFuncionario edicaoPage = PageFactory.initElements(this.driver, EdicaoFuncionario.class);
		edicaoPage.doAlterar("nome", "nomeUPD");
		edicaoPage.enviar();		
	}
	
	@Then("^verifico que a alteração de funcionário foi realizada com sucesso$")
	public void validaAlteracao() {
		Assert.assertNotNull(this.driver.findElement(By.xpath("//div[contains(concat(' ',normalize-space(@class),' '),' alert-success ')]")));
	}
	
	@After
	public void tearDown()
	{
		//Salvando screenshot no report
		byte[] image = ((TakesScreenshot)this.driver).getScreenshotAs(OutputType.BYTES);
		this.scenario.embed(image, "image/png");

		driver.quit();
	}
	
	private void configureData() {
		JsonNode newLoginInfo = YamlHelper.getInstance().convertYamlToNode(testData, "loginDTO");		
		user = newLoginInfo.get("email").asText();
		pass = newLoginInfo.get("pasword").asText();
	}
}
