package br.com.inmetrics.teste.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;

public class CadastroFuncionario extends PageObject {
	
	public CadastroFuncionario(WebDriver driver) {
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
	
	@FindBy(how=How.XPATH,using="/html/body/div/div[2]/div/form/div[3]/input")
	@CacheLookup
	WebElement cadastrar;
	
	@FindBy(how=How.XPATH,using="/html/body/div/div[2]/div/form/div[4]/input")
	@CacheLookup
	WebElement cancelar;
	
	public void doCadastrar(String nome, String cpf, String cargo, String admissao, String salario,
			String tipoContratacao, String sexo) {
		
		comboSexoSelect = new Select(comboSexo);
		
		this.nome.sendKeys(nome);
		this.cpf.sendKeys(cpf);
		this.cargo.sendKeys(cargo);
		this.admissao.sendKeys(admissao);
		this.salario.sendKeys(salario);
		
		switch(tipoContratacao) {
		
			case "pj":
				pj.click();
				break;
				
			case "clt":
				clt.click();
				break;
				
			default: break;		
		}
		
		comboSexo.click();
		
		switch(sexo) {
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
		
		cadastrar.click();		
	}
	
	public void cancelar() {
		cancelar.click();
	}
	
}
