package br.com.inmetrics.teste.support;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;


public final class Hooks {
	
	@Before
    public void init(Scenario scenario) {
        System.out.println("==== Iniciando teste do scenario: " + scenario.getName() + "=====");
    }

    @After
    public void savingEvidences(Scenario scenario) {    	
    	WebDriver driver = BrowserFactory.getInstance().getDriver(ConfigManager.getInstance()
				.getConfigs()
				.get("defaultDriver"));    	
    	generateEvidence(scenario, driver);
    	System.out.println("==== Finalizando teste do scenario: " + scenario.getName() + "====");
    }
    
    private void generateEvidence(Scenario scenario, WebDriver driver) {
    	System.out.println("===== Salvando sreenshot no report ===========");
		byte[] image = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
		scenario.attach(image, "image/png", "Image");
	}
	
}