package br.com.inmetrics.teste.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import br.com.inmetrics.teste.support.BrowserFactory;
import br.com.inmetrics.teste.support.ConfigManager;

public class CadastroNovoLogin {
	WebDriver driver;
	 
	public CadastroNovoLogin(WebDriver driver){
		this.driver= BrowserFactory.getInstance().getDriver(ConfigManager.getInstance().getConfigs().get("defaultDriver"));
	}
	
	@FindBy(how=How.NAME,using="username")
	@CacheLookup
	WebElement username;
	
	@FindBy(how=How.NAME,using="pass")
	@CacheLookup
	WebElement password;
	
	@FindBy(how=How.NAME,using="confirmpass")
	@CacheLookup
	WebElement confirmPassword;
	
	@FindBy(how=How.CLASS_NAME,using="login100-form-btn")
	@CacheLookup
	WebElement login;
	 
	public void doRegister(String use, String pass, String confirmationPass) {
		username.sendKeys(use);	
		password.sendKeys(pass);
		confirmPassword.sendKeys(confirmationPass);
		login.click();
	}
}
