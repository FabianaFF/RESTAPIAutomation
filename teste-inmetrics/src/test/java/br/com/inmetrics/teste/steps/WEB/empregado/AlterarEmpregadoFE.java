package br.com.inmetrics.teste.steps.WEB.empregado;

import org.openqa.selenium.WebDriver;

import com.fasterxml.jackson.databind.JsonNode;

import br.com.inmetrics.teste.PageObjects.EdicaoFuncionario;
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

public class AlterarEmpregadoFE {
	
	static String testData = "src/test/resources/data/test_data.yaml";
	private WebDriver driver;	
	private String pass;	
	private String user;
	private String idUser;
	private String dado;
	private String valor;
	private String urlEdit;
	
	@Before("@AlterarEmpregadoFE")
	public void before() {		
		this.driver = BrowserFactory.getInstance().getDriver(ConfigManager.getInstance().getConfigs().get("defaultDriver"));
		this.driver.get(ConfigManager.getInstance().getConfigs().get("webBase"));
		configureData();
	}
			
	@Given("^Como usuário web cadastrado e logado com permissão de alteração$")
	public void realizaAcesso() {				
		Login loginPage = new Login(this.driver);
		loginPage.waitForPageLoaded();
		loginPage.doLogin(user, pass);		
	}
	
	@When("^ao selecionar funcionário para alterar na listagem$")
	public void selecionarEmpregado() {		
		ListagemFuncionarios listagemPage = new ListagemFuncionarios(this.driver);
		listagemPage.waitForPageLoaded();
		listagemPage.doSelecionaPrimeiroFuncionario();
		listagemPage.doEditarFuncionario();
	}
	
	@And("^enviar '(.*)' alterado para '(.*)'$")
	public void alterarEmpregado(String dado, String valor) {
		this.dado = dado;
		this.valor = valor;
		int index = this.driver.getCurrentUrl().indexOf("/edit/");		
		idUser = this.driver.getCurrentUrl().substring(index + 6).trim();
		urlEdit = "http://www.inmrobo.tk/empregados/edit/"+idUser;
		
		Assert.assertEquals(this.driver.getCurrentUrl(), urlEdit);	
						
		EdicaoFuncionario edicaoPage = new EdicaoFuncionario(this.driver);
		edicaoPage.waitForPageLoaded();
		edicaoPage.doAlterar(dado, valor);
		edicaoPage.enviar();		
	}
	
	@Then("^verifico que a alteração de funcionário foi realizada com sucesso$")
	public void validaAlteracao() {
		try{
			//Validando se não houve erro ao salvar
			ListagemFuncionarios listagemPage = new ListagemFuncionarios(this.driver);
			listagemPage.waitForPageLoaded();
			Assert.assertTrue(
					listagemPage.getStatusAtualizacao().
					contains("Informações atualizadas com sucesso"));
			
			driver.get(urlEdit);
			
			//Validando se os valores foram realmemte persistidos
			EdicaoFuncionario edicaoPage = new EdicaoFuncionario(this.driver);
			Assert.assertEquals(edicaoPage.getFieldValue(dado), valor);
		}catch(Exception ex) {
			System.out.println("===== Exception ====\n" + ex.getMessage());
			new AssertionError("Alteração não realizada com sucesso.");
		}
	}
	
	private void configureData() {
		JsonNode newLoginInfo = YamlHelper.getInstance().convertYamlToNode(testData, "loginDTO");		
		user = newLoginInfo.get("email").asText();
		pass = newLoginInfo.get("pasword").asText();
	}
}
