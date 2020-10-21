package br.com.inmetrics.teste.steps.ui.empregado;

import org.openqa.selenium.By;
<<<<<<< HEAD
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
=======
>>>>>>> b9416fa452dce976fdff3d7e1bcf03837526c3b2
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.fasterxml.jackson.databind.JsonNode;

import br.com.inmetrics.teste.PageObjects.CadastroFuncionario;
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

public class DeletarEmpregadoFE {
	
	static String testData = "src/test/resources/data/test_data.yaml";
	private Scenario scenario;
	private ListagemFuncionarios listagemPage;
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
	
	@Given("^Como usuário web cadastrado e logado com permissão de deleção$")
	public void realizaAcesso() {		
		Login loginPage =  (Login) PageFactory.initElements(this.driver, Login.class);
		loginPage.doLogin(user, pass);	
	}
	
	@When("^ao selecionar funcionário para deletar na listagem$")
	public void selecionarEmpregado() {		
		Assert.assertEquals(this.driver.getCurrentUrl(), "http://www.inmrobo.tk/empregados/");
		listagemPage = PageFactory.initElements(this.driver, ListagemFuncionarios.class);
		listagemPage.doSelecionaPrimeiroFuncionario();
	}
	
	@And("^clicar no botão para deletar$")
	public void apagarEmpregado() {
		listagemPage.doApagarFuncionario();
	}
	
	@Then("^verifico que a deleção de funcionário foi realizada com sucesso$")
	public void validaDelecao() {
		Assert.assertNotNull(this.driver.findElement(By.xpath("//div[contains(concat(' ',normalize-space(@class),' '),' alert-success ')]")));		
	}
	
	@After
	public void tearDown()
	{
		//Salvando screenshot no report
		byte[] image = ((TakesScreenshot)this.driver).getScreenshotAs(OutputType.BYTES);
		this.scenario.embed(image, "image/png");
		
		this.driver.quit();
	}
	
	public void configureData() {
		JsonNode newLoginInfo = YamlHelper.getInstance().convertYamlToNode(testData, "loginDTO");		
		user = newLoginInfo.get("email").asText();
		pass = newLoginInfo.get("pasword").asText();
	}
}
