package br.com.inmetrics.teste.steps.WEB.empregado;

import org.openqa.selenium.WebDriver;

import com.fasterxml.jackson.databind.JsonNode;

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

public class DeletarEmpregadoFE {
	
	static String testData = "src/test/resources/data/test_data.yaml";
	private ListagemFuncionarios listagemPage;
	private WebDriver driver;	
	private String pass;	
	private String user;
	
	@Before("@DeletarEmpregadoFE")
	public void before() {		
		this.driver = BrowserFactory.getInstance().getDriver(ConfigManager.getInstance().getConfigs().get("defaultDriver"));
		this.driver.get(ConfigManager.getInstance().getConfigs().get("webBase"));
		configureData();
	}
	
	@Given("^Como usuário web cadastrado e logado com permissão de deleção$")
	public void realizaAcesso() {		
		Login loginPage = new Login(this.driver);
		loginPage.waitForPageLoaded();
		loginPage.doLogin(user, pass);
	}
	
	@When("^ao selecionar funcionário para deletar na listagem$")
	public void selecionarEmpregado() {
		listagemPage = new ListagemFuncionarios(this.driver);
		listagemPage.waitForPageLoaded();
		listagemPage.doSelecionaPrimeiroFuncionario();
	}
	
	@And("^clicar no botão para deletar$")
	public void apagarEmpregado() {
		listagemPage.doApagarFuncionario();
	}
	
	@Then("^verifico que a deleção de funcionário foi realizada com sucesso$")
	public void validaDelecao() {
		try {
			ListagemFuncionarios listagemPage = new ListagemFuncionarios(this.driver);
			listagemPage.waitForPageLoaded();
			Assert.assertTrue(listagemPage.getStatusAtualizacao().contains("Funcionário removido com sucesso"));
		}catch(Exception ex) {
			System.out.println("===== Exception ====\n" + ex.getMessage());
			new AssertionError("Funcionário não deletado com sucesso.");
		}
	}
		
	public void configureData() {
		JsonNode newLoginInfo = YamlHelper.getInstance().convertYamlToNode(testData, "loginDTO");		
		user = newLoginInfo.get("email").asText();
		pass = newLoginInfo.get("pasword").asText();
	}
}
