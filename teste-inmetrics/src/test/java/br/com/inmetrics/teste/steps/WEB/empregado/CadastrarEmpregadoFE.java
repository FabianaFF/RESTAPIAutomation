package br.com.inmetrics.teste.steps.WEB.empregado;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.fasterxml.jackson.databind.JsonNode;

import br.com.inmetrics.teste.PageObjects.CadastroFuncionario;
import br.com.inmetrics.teste.PageObjects.ListagemFuncionarios;
import br.com.inmetrics.teste.PageObjects.Login;
import br.com.inmetrics.teste.support.BrowserFactory;
import br.com.inmetrics.teste.support.ConfigManager;
import br.com.inmetrics.teste.support.YamlHelper;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import junit.framework.Assert;


public class CadastrarEmpregadoFE {
	
	static String testData = "src/test/resources/data/test_data.yaml";
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

	@Before("@CadastrarEmpregadoFE")
	public void before() {			
		this.driver= BrowserFactory.getInstance().getDriver(ConfigManager.getInstance().getConfigs().get("defaultDriver"));
		driver.get(ConfigManager.getInstance().getConfigs().get("webBase"));
		configureData();
	}
		
	@Given("^Como usuário web cadastrado e logado com permissão de cadastro$")
	public void realizaAcesso() {		
		Login loginPage =  PageFactory.initElements(driver, Login.class);
		loginPage.waitForPageLoaded();
		loginPage.doLogin(user, pass);	
	}
	
	@When("^ao selecionar opção Novo Funcionário$")
	public void acessaOpcaoNovoFuncionario() {
		ListagemFuncionarios listagemPage = new ListagemFuncionarios(this.driver);
		listagemPage.waitForPageLoaded();
		listagemPage.doCadastrarNovoFuncionario();
	}
	
	@And("^enviar todos os campos requeridos pelo cadastro$")
	public void realizaCadastroEmpregado() {
		CadastroFuncionario cadastroPage = new CadastroFuncionario(this.driver);
		cadastroPage.waitForPageLoaded();
		cadastroPage.doCadastrar(nome, cpf, cargo, admissao, salario, tipoContratacao, sexo);
	}
	
	@Then("^verifico que a criação de novo funcionário foi realizada com sucesso$")
	public void validaCadastro() {
		try {
			ListagemFuncionarios listagemPage = new ListagemFuncionarios(this.driver);
			listagemPage.waitForPageLoaded();
			Assert.assertTrue(
					listagemPage.getStatusAtualizacao().
					contains("Usuário cadastrado com sucesso"));

		}catch(Exception ex) {
			System.out.println("===== Exception ====\n" + ex.getMessage());
			new AssertionError("Cadastro não realizado com sucesso.");
		}	
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
