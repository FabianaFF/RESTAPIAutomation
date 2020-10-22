package br.com.inmetrics.teste.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ListagemFuncionarios extends PageObject{
	
	public ListagemFuncionarios(WebDriver driver) {
		super(driver);
		super.elementVisibility = "tabela";
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
	
	public String getStatusAtualizacao() {
				
		String statusAtualizacao = "";
		try {
			WebElement status = driver.findElement(By.xpath("//div[@class='container-message']/div"));
			statusAtualizacao = status.getText().trim();			
			System.out.println("statusAtualizacao:" + statusAtualizacao);
		}catch(NoSuchElementException ex) {
			System.out.println("============= ELEMENTO NÃO ENCONTRADO ==============");
		}
		return statusAtualizacao;
	}
	
}
