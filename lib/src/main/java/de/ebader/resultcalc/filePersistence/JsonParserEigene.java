package de.ebader.resultcalc.filePersistence;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.ebader.resultcalc.util.Util;

public class JsonParserEigene {
	
	Util util = new Util();
	String dateiPfad = util.getLocaleJsonPath("erstellteTeams.json");
	String jsonContent = "";

	private ObjectMapper objectMapper = new ObjectMapper();
	public JsonNode jsonNode;

	public JsonParserEigene() {
	    this.objectMapper = new ObjectMapper();

	    if (this.jsonNode == null) {
	            jsonNode = baueResponseAuf();

	    }
	}

	public void refresheJsonNode() {
	    this.jsonNode = baueResponseAuf();
	}

	public JsonNode baueResponseAuf() {
	   String dateiName = "erstellteTeams.json";
	   this.dateiPfad = System.getProperty("user.home") + File.separator + dateiName;

	   try {
	       if (!Files.exists(Paths.get(dateiPfad))) {
	                // Die Datei existiert nicht, erstelle sie und fülle sie mit dem
	                // jsonContent
	       createAndWriteFile(dateiPfad);
	       }
	       else {
	                // Die Datei existiert, lese sie ein
	       String jsonContent = new String(Files.readAllBytes(Paths.get(dateiPfad)));
	       jsonNode = objectMapper.readTree(jsonContent);
	       }
	   }
	   catch (IOException e) {
	       e.printStackTrace();
	   }

	       return jsonNode;
	   }

	   private void createAndWriteFile(String dateiPfad) throws IOException {
	        // Hier wird die Datei erstellt und mit dem jsonContent befüllt
	        // Beispiel: Annahme, dass jsonContent bereits vorhanden ist

	       Files.write(Paths.get(dateiPfad), jsonContent.getBytes());
	       System.out.println("Datei wurde erstellt und mit Inhalt befüllt: " + dateiPfad);
	   }
	   
	   public List<Integer> getPrognoseIDs() {
		    jsonNode = baueResponseAuf();
	        List<Integer> entryNumbers = new ArrayList<>();
	        JsonNode entryNode = jsonNode.get("Entry");

	        if (entryNode != null && entryNode.isObject()) {
	            Iterator<Map.Entry<String, JsonNode>> entries = entryNode.fields();

	            while (entries.hasNext()) {
	                Map.Entry<String, JsonNode> entry = entries.next();
	                String entryNumber = entry.getKey();
	                entryNumbers.add(Integer.parseInt(entryNumber));
	            }
	        }

	        return entryNumbers;
	    }

	    public Integer parseHoechsteTeamID() {
	        jsonNode = baueResponseAuf();
	        List<Integer> entryNumbers = new ArrayList<>();
	        JsonNode entryNode = jsonNode.get("Entry");

	        if (entryNode != null && entryNode.isObject()) {
	            Iterator<Map.Entry<String, JsonNode>> entries = entryNode.fields();

	            while (entries.hasNext()) {
	                Map.Entry<String, JsonNode> entry = entries.next();
	                String entryNumber = entry.getKey();
	                entryNumbers.add(Integer.parseInt(entryNumber));
	            }
	        }
	        Integer hoechsteID = 0;
	        for (Integer id : entryNumbers) {
	            if (id > hoechsteID) {
	                hoechsteID = id;
	            }
	        }
	        System.out.println(hoechsteID);
	        return hoechsteID;
	    }
	    
	    public Map<String, Double> parseErgebnisseAusGespeichertemTeam(Integer prognoseID) {
	        try {
	        	jsonNode = baueResponseAuf();
	            Map<String, Double> attributErgebnisse = new HashMap<String, Double>();
	            List<Integer> attributIDs = new ArrayList<>();
	            JsonNode jsonwerteIDs = jsonNode.get("Entry").get(prognoseID.toString()).get("attribute");
	            String ergebnisIDs = jsonwerteIDs.toString();

	            String[] teamIDsArray = ergebnisIDs.split(",");
	            teamIDsArray[0] = teamIDsArray[0].replace('{', ' ');
	            teamIDsArray[teamIDsArray.length - 1] = teamIDsArray[teamIDsArray.length - 1].replace('}', ' ');
	            Map<String, String> teamIDsMap = new HashedMap<>();
	            for (int i = 0; i < teamIDsArray.length; i++) {
	                String[] zwischenSpeicherFuerIDs = teamIDsArray[i].split(":");
	                zwischenSpeicherFuerIDs[0] = zwischenSpeicherFuerIDs[0].replace('"', ' ');

	                String key = zwischenSpeicherFuerIDs[0];
	                String trimmedKey = key.replaceAll(" ", "");
	                
	                String value = zwischenSpeicherFuerIDs[1];
	                String trimmedValue = value.replaceAll(" ", "");
	                Double valueDouble = Double.valueOf(trimmedValue);
	                attributErgebnisse.put(trimmedKey, valueDouble);
	            }
	            return attributErgebnisse;
	        }
	        catch (Exception e) {
	            // e.printStackTrace();
	        }
	        System.out.println("fail");
	        return null;
	    }
	    
	    public String parseTeamNamenUeberID(Integer teamid) {
	    	return jsonNode.get("Entry").get(teamid.toString()).get("teamname").asText();
	    }
}
