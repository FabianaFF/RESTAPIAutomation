package br.com.inmetrics.teste.runner;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.inmetrics.teste.support.ConfigManager;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features="src/test/resources/features/empregado/", 
glue="br/com/inmetrics/teste/steps", 
monochrome = true, plugin={"pretty", "json:target/cucumber.json"})
public class RunCucumberTest {

    @BeforeClass
    public static void setup() {
        System.out.println("Starting tests...");
        ConfigManager.getInstance().setConfigs();		
	}

	@AfterClass
    public static void tearDown() throws JsonProcessingException, IOException {
		System.out.println("Finishing tests...");
    }

}