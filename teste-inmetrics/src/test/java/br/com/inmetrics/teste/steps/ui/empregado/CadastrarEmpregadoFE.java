package br.com.inmetrics.teste.steps.ui.empregado;

import org.openqa.selenium.By;
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

public class CadastrarEmpregadoFE {
	
	static String testData = "src/test/resources/data/test_data.yaml";
	WebDriver driver;	
	String pass;	
	String user;
	
	String nome;
	String cargo;
	String admissao;
	String cpf;
	String salario;
	String tipoContratacao;
	String sexo;

	@Before()
	public void before(Scenario scenario) {
		driver = this.driver= BrowserFactory.getInstance().getDriver(ConfigManager.getInstance().getConfigs().get("defaultDriver"));
		configureData();
	}
	
	@Given("^Como usuário web cadastrado e logado com permissão de cadastro$")
	public void realizaAcesso() {
		driver.get(ConfigManager.getInstance().getConfigs().get("webBase"));
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
	public void validaCadastroRealizado() {
		Assert.assertNotNull(driver.findElement(By.xpath("//div[contains(concat(' ',normalize-space(@class),' '),' alert-success ')]")));
		//Salvando evidencias da execução
		String novoCadastroFile = System.getProperty("user.dir")+"\\ScreenShots\\NovoEmpregado.png";
		
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
		user = newLoginInfo.get("email").asText();
		pass = newLoginInfo.get("pasword").asText();
		
		JsonNode newEmpregadoInfo = YamlHelper.getInstance().convertYamlToNode(testData, "empregadoDTO4");	
		nome = newEmpregadoInfo.get("nome").asText();
		cargo = newEmpregadoInfo.get("cargo").asText();
		admissao = newEmpregadoInfo.get("admissao").asText();
		//cpf = newEmpregadoInfo.get("cpf").asText();
		cpf = "83753429805";
		salario = newEmpregadoInfo.get("salario").asText();
		tipoContratacao = newEmpregadoInfo.get("tipoContratacao").asText() ;
		sexo = newEmpregadoInfo.get("sexo").asText();
	}
}
