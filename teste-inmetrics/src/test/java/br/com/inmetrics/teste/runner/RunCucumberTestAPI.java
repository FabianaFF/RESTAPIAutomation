package br.com.inmetrics.teste.runner;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.inmetrics.teste.support.ConfigManager;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features="src/test/resources/features/API/empregado/alterar", 
glue="br/com/inmetrics/teste/steps/API", 
monochrome = true, plugin={"pretty", "json:target/cucumber.json"})
public class RunCucumberTestAPI {

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