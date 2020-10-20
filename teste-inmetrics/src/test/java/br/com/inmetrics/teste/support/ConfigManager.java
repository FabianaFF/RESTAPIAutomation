package br.com.inmetrics.teste.support;

import java.util.HashMap;
import java.util.Map;

import io.restassured.RestAssured;

public class ConfigManager {
	
	private static ConfigManager instance;
	private static Map<String, String> configs;
	static String configPath = "src/test/resources/config/config.yaml";
		
	private void ConfigManager() {}
	
	public static ConfigManager getInstance() {
		if(instance == null) {
			instance = new ConfigManager();						
		}
		return instance;
	}
	
	//All global configs from yaml file or not needs to be add here
	public void setConfigs() {
		 configs = new HashMap<String, String>();
		 String admUser = YamlHelper.getInstance().convertYamlToNode(configPath,"admUser").asText();
		 String admPass = YamlHelper.getInstance().convertYamlToNode(configPath,"admPass").asText();
		 String urlBase = YamlHelper.getInstance().convertYamlToNode(configPath,"urlBase").asText();		 
		 String webBase = YamlHelper.getInstance().convertYamlToNode(configPath,"webBase").asText();
		 String chromeDriverPath = YamlHelper.getInstance().convertYamlToNode(configPath,"chromeDriverPath").asText();
		 String defaultDriver = YamlHelper.getInstance().convertYamlToNode(configPath,"defaultDriver").asText();
		 
		 configs.put("admUser", admUser);
		 configs.put("admPass", admPass);
		 configs.put("urlBase", urlBase);
		 configs.put("webBase", webBase);
		 configs.put("chromeDriverPath", chromeDriverPath);
		 configs.put("defaultDriver", defaultDriver);
		 
		 RestAssured.baseURI = urlBase;
		 System.out.println("Configurações setadas");
	}
	
	public Map<String,String> getConfigs(){
		return configs;
	}
}
