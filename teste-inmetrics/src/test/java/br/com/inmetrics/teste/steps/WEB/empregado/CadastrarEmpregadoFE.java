package br.com.inmetrics.teste.steps.WEB.empregado;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.fasterxml.jackson.databind.JsonNode;

import br.com.inmetrics.teste.PageObjects.CadastroFuncionario;
import br.com.inmetrics.teste.PageObjects.ListagemFuncionarios;
import br.com.inmetrics.teste.PageObjects.Login;
import br.com.inmetrics.teste.support.BrowserFactory;
import br.com.inmetrics.teste.support.ConfigManager;
import br.com.inmetrics.teste.support.YamlHelper;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import junit.framework.Assert;


public class CadastrarEmpregadoFE {
	
	static String testData = "src/test/resources/data/test_data.yaml";
	private Scenario scenario;
	private WebDriver driver;	
	private String pass;	
	private String user;
	
	private String nome;
	private String cargo;
	private String admissao;
	private String cpf;
	private String salario;
	private String tipoContratacao;
	private String sexo;

	@Before()
	public void before(Scenario scenario) {
		this.scenario = scenario;
		this.driver= BrowserFactory.getInstance().getDriver(ConfigManager.getInstance().getConfigs().get("defaultDriver"));
		driver.get(ConfigManager.getInstance().getConfigs().get("webBase"));
		configureData();
	}
	
	@Given("^Como usuário web cadastrado e logado com permissão de cadastro$")
	public void realizaAcesso() {		
		Login loginPage =  PageFactory.initElements(driver, Login.class);
		loginPage.doLogin(user, pass);	
	}
	
	@When("^ao selecionar opção Novo Funcionário$")
	public void acessaOpcaoNovoFuncionario() {		
		Assert.assertEquals(driver.getCurrentUrl(), "http://www.inmrobo.tk/empregados/");
		ListagemFuncionarios listagemPage = PageFactory.initElements(driver, ListagemFuncionarios.class);
		listagemPage.doCadastrarNovoFuncionario();
	}
	
	@And("^enviar todos os campos requeridos pelo cadastro$")
	public void realizaCadastroEmpregado() {
		Assert.assertEquals(driver.getCurrentUrl(), "http://www.inmrobo.tk/empregados/new_empregado");
		CadastroFuncionario cadastroPage = PageFactory.initElements(driver, CadastroFuncionario.class);
		cadastroPage.doCadastrar(nome, cpf, cargo, admissao, salario, tipoContratacao, sexo);
	}
	
	@Then("^verifico que a criação de novo funcionário foi realizada com sucesso$")
	public void validaCadastro() {
		try {
			driver.findElement(By.xpath("//div[contains(concat(' ',normalize-space(@class),' '),' alert-success ')]"));
		}catch(NoSuchElementException ex) {
			System.out.println("Driver Exception: "+ ex.getMessage());
			new AssertionError("Cadastro não realizado com sucesso.");
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
		
		JsonNode newEmpregadoInfo = YamlHelper.getInstance().convertYamlToNode(testData, "empregadoDTO4");	
		nome = newEmpregadoInfo.get("nome").asText();
		cargo = newEmpregadoInfo.get("cargo").asText();
		admissao = newEmpregadoInfo.get("admissao").asText();
		cpf = newEmpregadoInfo.get("cpf").asText();		
		salario = newEmpregadoInfo.get("salario").asText();
		tipoContratacao = newEmpregadoInfo.get("tipoContratacao").asText() ;
		sexo = newEmpregadoInfo.get("sexo").asText();
	}
}
