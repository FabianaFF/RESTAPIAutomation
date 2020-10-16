package br.com.inmetrics.teste.support;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public final class YamlHelper {
	
	private static YamlHelper instance;
	
	private void YamlHelper() {}
	
	public static YamlHelper getInstance() {
		if(instance == null) {
			instance = new YamlHelper();
		}
		return instance;
	}
 
	public String convertYamlToJson(String yaml) {
        try {
        	File f = new File(yaml);
        	ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());            
            Object obj = yamlReader.readValue(f, Object.class);
            ObjectMapper jsonWriter = new ObjectMapper();
            return jsonWriter.writerWithDefaultPrettyPrinter().writeValueAsString(obj);            
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }	
	
	public Object convertYamlToObject(String yaml, Object objToReturn ) {
        try {        	
            String json = convertYamlToJson(yaml);
            Object obj = new ObjectMapper().readValue(json, objToReturn.getClass());
            return obj;
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
	}
	
	public JsonNode convertYamlToNode(String yaml, String node) {
        try {        	
            String json = convertYamlToJson(yaml);
            JsonNode jsonNode = new ObjectMapper().readTree(json);
            return jsonNode.get(node);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}