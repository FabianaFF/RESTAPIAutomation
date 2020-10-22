package br.com.inmetrics.teste.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EdicaoFuncionario extends PageObject{
	
	public EdicaoFuncionario(WebDriver driver) {
		super(driver);
		super.elementVisibility = "inputNome";
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
		
		if(info.equals("tipoContratacao")) {
			
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
		
		if(info.equals("sexo")) {
			comboSexoSelect = new Select(comboSexo);
			comboSexo.click();
			
			switch(value) {
				case "f" :
					comboSexoSelect.selectByVisibleText("Feminino");				
					break;
					
				case "m" :
					comboSexoSelect.selectByVisibleText("Masculino");
					break;	
				
				case "-" :
					comboSexoSelect.selectByVisibleText("Indiferente");
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
	
	public String getFieldValue(String field) {
		String value = "";
	
		switch (field) {
			case "nome":		
				value = this.nome.getAttribute("value");
				break;
			case "cpf":			
				value = this.cpf.getAttribute("value");
				break;
			case "cargo":			
				value = this.cargo.getAttribute("value");
				break;
			case "admissao":
				value = this.cargo.getAttribute("value");
				break;			
			case "salario":
				value = this.salario.getAttribute("value");
				break;				
			default: break;				
		}
	
		if(field.equals("tipoContratacao")) {
			if(pj.isSelected())
				value = "pj";
			
			if(clt.isSelected())
				value = "clt";
		}
	
		if(field.equals("sexo")) {
			comboSexoSelect = new Select(comboSexo);
			String sexo = comboSexoSelect.getFirstSelectedOption().getText();

			switch(sexo) {
				case "Feminino" :
					value = "f";		
					break;
					
				case "Masculino" :
					value = "m";
					break;	
				
				case "Indiferente" :
					value = "-";
					break;
				
				default:
					break;				
			}
		}
		
		return value;
	}
	
}
