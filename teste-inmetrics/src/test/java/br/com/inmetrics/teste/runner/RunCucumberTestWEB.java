package br.com.inmetrics.teste.runner;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.inmetrics.teste.support.BrowserFactory;
import br.com.inmetrics.teste.support.ConfigManager;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features="src/test/resources/features/WEB", 
glue={"br/com/inmetrics/teste/support","br/com/inmetrics/teste/steps/WEB"}, 
monochrome = true, plugin={"pretty", "json:target/cucumber.json"})
public class RunCucumberTestWEB {

    @BeforeClass
    public static void setup() {
        System.out.println("Starting tests...");
        ConfigManager.getInstance().setConfigs();
        
        System.out.println("===== Iniciando driver ===========");
        WebDriver driver = BrowserFactory.getInstance().getDriver(ConfigManager.getInstance()
				.getConfigs()
				.get("defaultDriver"));
	}

	@AfterClass
    public static void tearDown() throws JsonProcessingException, IOException {
		System.out.println("Finishing tests...");
		WebDriver driver = BrowserFactory.getInstance().getDriver(ConfigManager.getInstance()
				.getConfigs()
				.get("defaultDriver"));
		
		System.out.println("===== Encerrando driver ===========");
		if(driver != null)
			driver.quit();		
    }

}