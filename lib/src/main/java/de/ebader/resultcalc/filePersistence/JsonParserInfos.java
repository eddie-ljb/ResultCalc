package de.ebader.resultcalc.filePersistence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.ebader.resultcalc.util.Util;

public class JsonParserInfos {
	
	Util util = new Util();
	
	public static JsonNode jsonNode;
	public ObjectMapper objectmapper;
	
	public JsonParserInfos() {
		this.objectmapper = new ObjectMapper();
		if (this.jsonNode == null) {
			jsonNode = baueResponseAuf();
		}
	}

	private JsonNode baueResponseAuf() {
		String dateiPfad = util.getLocaleJsonPath("jsonDateien/info.json");
		String jsonContent = "";
		try {
			jsonContent = new String(Files.readAllBytes(Paths.get(dateiPfad)));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			jsonNode = objectmapper.readTree(jsonContent);
		}
		catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return jsonNode;
	}
	
	public String parseHomepageInfos() {
		return jsonNode.get("Info").get("Homepage").asText();
	}

}
