package br.com.inmetrics.teste.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;

import br.com.inmetrics.teste.support.BrowserFactory;
import br.com.inmetrics.teste.support.ConfigManager;

public class EdicaoFuncionario {
	WebDriver driver;
	 
	public EdicaoFuncionario(WebDriver driver){
		this.driver= BrowserFactory.getInstance().getDriver(ConfigManager.getInstance().getConfigs().get("defaultDriver"));
	}

	@FindBy(how=How.ID,using="inputNome")
	@CacheLookup
	WebElement nome;
	
	@FindBy(how=How.ID,using="cpf")
	@CacheLookup	
	WebElement cpf;
	
	@FindBy(how=How.ID,using="inputAdmissao")
	@CacheLookup
	WebElement admissao;
	
	@FindBy(how=How.ID,using="inputCargo")
	@CacheLookup
	WebElement cargo;
	
	@FindBy(how=How.ID,using="dinheiro")
	@CacheLookup
	WebElement salario;
	
	@FindBy(how=How.XPATH,using="//*[@id=\"clt\"]")
	@CacheLookup
	WebElement clt;
	
	@FindBy(how=How.XPATH,using="//*[@id=\"pj\"]")
	@CacheLookup
	WebElement pj;
	
	@FindBy(how=How.XPATH,using="//*[@id=\"slctSexo\"]")
	@CacheLookup
	WebElement comboSexo;
	Select comboSexoSelect;
	
	@FindBy(how=How.XPATH,using="/html/body/div/div/div/form/div[3]/input")
	@CacheLookup
	WebElement enviar;
	
	@FindBy(how=How.XPATH,using="/html/body/div/div/div/form/div[4]/input")
	@CacheLookup
	WebElement cancelar;
	
	public void doAlterar(String info, String value) {
		
		switch (info) {
			case "nome":
				this.nome.clear();
				this.nome.sendKeys(value);
				break;
			case "cpf":
				this.cpf.clear();
				this.cpf.sendKeys(value);
				break;
			case "cargo":
				this.cargo.clear();
				this.cargo.sendKeys(value);
				break;
			case "admissao":
				this.admissao.clear();
				this.cargo.sendKeys(value);
				break;			
			case "salario":
				this.salario.clear();
				this.salario.sendKeys(value);
				break;				
			default: break;				
		}
		
		if(info == "tipoContratacao") {
			switch(value) {			
				case "pj":
					pj.click();
					break;
					
				case "clt":
					clt.click();
					break;
					
				default: break;		
			}
		}
		
		if(info == "sexo") {
			comboSexoSelect = new Select(comboSexo);
			comboSexo.click();
			
			switch(value) {
				case "f" :
					comboSexoSelect.selectByIndex(2);				
					break;
					
				case "m" :
					comboSexoSelect.selectByIndex(3);
					break;	
				
				case "-" :
					comboSexoSelect.selectByIndex(1);
					break;
				
				default:
					break;				
			}
		}		
	}
	
	public void enviar() {
		enviar.click();		
	}
	
	public void cancelar() {
		cancelar.click();
	}
}
