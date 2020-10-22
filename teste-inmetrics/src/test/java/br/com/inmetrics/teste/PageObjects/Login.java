package br.com.inmetrics.teste.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Login extends PageObject{
		
	public Login(WebDriver driver) {
		super(driver);		
	}

	@FindBy(how=How.NAME,using="username")
	@CacheLookup
	WebElement username;
	
	@FindBy(how=How.NAME,using="pass")
	@CacheLookup
	WebElement password;
	
	@FindBy(how=How.CLASS_NAME,using="login100-form-btn")
	@CacheLookup
	WebElement login;
	
	@FindBy(how=How.PARTIAL_LINK_TEXT,using="Cadastre-se")
	@CacheLookup
	WebElement novoCadastro;
	 
	public void doLogin(String user, String pass) {
		username.sendKeys(user);	
		password.sendKeys(pass);		
		login.click();
	}
	
	public void doNovoCadastro() {
		novoCadastro.click();
	}
	
	@Override
	public void waitForPageLoaded() {
		System.out.println("==== Aguardando página ser renderizada =======");
		WebDriverWait wait = new WebDriverWait(driver, 15);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
	}
}
