package br.com.inmetrics.teste.PageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import br.com.inmetrics.teste.support.BrowserFactory;
import br.com.inmetrics.teste.support.ConfigManager;

public class ListagemFuncionarios {
	WebDriver driver;
	 
	public ListagemFuncionarios(WebDriver driver){
		this.driver= BrowserFactory.getInstance().getDriver(ConfigManager.getInstance().getConfigs().get("defaultDriver"));
	}
	
	@FindBy(how=How.LINK_TEXT,using="FUNCIONÁRIOS")
	@CacheLookup
	WebElement funcionarios;
	
	@FindBy(how=How.LINK_TEXT,using="NOVO FUNCIONÁRIO")
	@CacheLookup
	WebElement novoFuncionario;
	
	@FindBy(how=How.LINK_TEXT,using="SAIR")
	@CacheLookup
	WebElement sair;
		
	//@FindBy(how=How.XPATH,using="//*[@id=\"tabela_filter\"]/label/input")
	//@CacheLookup
	//WebElement presquisar;
	
	@FindBy(how=How.ID,using="tabela")
	@CacheLookup
	WebElement lista;
	
	WebElement editar;	
	WebElement apagar;
	
	public void doSelecionaPrimeiroFuncionario() {
		WebElement linhaPrimeiroUsuario = lista.findElement(By.xpath("//tbody/tr[1]"));		
			
		apagar = linhaPrimeiroUsuario.findElements(By.tagName("a")).get(0);
		editar = linhaPrimeiroUsuario.findElements(By.tagName("a")).get(1);
	}
	
	public void doEditarFuncionario() {
		editar.click();		
	}
	
	public void doApagarFuncionario() {
		apagar.click();		
	}
	
	public void doCadastrarNovoFuncionario() {
		novoFuncionario.click();
	}
	
	public void doLogout() {
		sair.click();
	}
}
