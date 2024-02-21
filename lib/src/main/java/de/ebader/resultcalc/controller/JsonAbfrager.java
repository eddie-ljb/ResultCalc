package de.ebader.resultcalc.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import de.ebader.resultcalc.filePersistence.JsonParserEigene;
import de.ebader.resultcalc.util.Util;

public class JsonAbfrager implements JsonAbfragen {
	
	Util util = new Util();
	final URL url = util.getURL("erstellteTeams.json");
	
	private static final ObjectMapper objectmapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
	JsonParserEigene jsonParserEigene = new JsonParserEigene();
	
	public void getSpeichereInJson(Map<String, Double> attribute, Set<String> keysErgebnisse, String teamName) {
		try {
			File datei = new File(System.getProperty("user.home") + File.separator + "erstellteTeams.json");
			Map<String, Object> existierendeDaten;
			
			if(datei.exists() && datei.length() > 0) {
				existierendeDaten = objectmapper.readValue(datei, Map.class);
			} else {
				existierendeDaten = new HashMap<String, Object>();
			}
			
			int neuerEintragNummer = getPassendeTeamID();
			System.out.println(neuerEintragNummer + "ID");
			
			Map<String, Object> neuerEintrag = new HashMap<String, Object>();
			neuerEintrag.put("teamname", teamName);
			neuerEintrag.put("attribute", attribute);
			
			Map<String, Object> entryMap = (Map<String, Object>) existierendeDaten.computeIfAbsent("Entry", k -> new HashMap<String, Object>());
			entryMap.put(String.valueOf(neuerEintragNummer), neuerEintrag);
			
			objectmapper.writeValue(datei, existierendeDaten);
			}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void getLoescheEintragUnterEntry(int eintragNummer) {
		try {
			File datei = new File(System.getProperty("user.home") + File.separator + "erstellteTeams.json");
			Map<String, Object> existierendeDaten;
			
			if(datei.exists() && datei.length() > 0) {
				existierendeDaten = objectmapper.readValue(datei, Map.class);
			} else {
				existierendeDaten = new HashMap<String, Object>();
			}
			System.out.println(existierendeDaten.get(eintragNummer));
			
			Map<String, Object> eintraege = (Map<String, Object>) existierendeDaten.get("Entry");
			eintraege.remove(String.valueOf(eintragNummer));
			existierendeDaten = new HashMap<String, Object>();
			existierendeDaten.put("Entry", eintraege);
			
			
			Map<String, Object> entryMap = (Map<String, Object>) existierendeDaten.computeIfAbsent("Entry", k -> new HashMap<String, Object>());
			objectmapper.writeValue(datei, existierendeDaten);
//			if (datei.exists()) {
//				Map<String, Object> existierendeDaten = objectmapper.readValue(datei, Map.class);
//				
//				if (existierendeDaten.containsKey("Entry")) {
//					Map<String, Object> entryMap = (Map<String, Object>) existierendeDaten.get("Entry");
//					
//					String eintragNummerKey = String.valueOf(eintragNummer);
//					if (entryMap.containsKey(eintragNummerKey)) {
//						entryMap.remove(eintragNummerKey);
//						Map<String, Object> entries = (Map<String, Object>) entryMap.computeIfAbsent("Entry", k -> new HashMap<String, Object>());
//						objectmapper.writeValue(datei, entryMap);
//					}
//					System.out.println("Eintrag unter Entry mit der Nummer " + eintragNummer + " erfolreich gelöscht.");
//				}
//				
//			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void getBearbeiteEntryVonID(Integer teamid, double bewertung, double moral, double form) {
		try {
			File datei = new File(System.getProperty("user.home") + File.separator + "erstellteTeams.json");
			Map<String, Double> attribute = new HashMap<String, Double>();
			attribute.put("teambewertung", bewertung);
			attribute.put("moral", moral);
			attribute.put("form", form);
			Map<String, Object> neuerEintrag = new HashMap<String, Object>();
			neuerEintrag.put("teamname", jsonParserEigene.parseTeamNamenUeberID(teamid));
			neuerEintrag.put("attribute", attribute);
			if (datei.exists()) {
				Map<String, Object> existierendeDaten = objectmapper.readValue(datei, Map.class);
				
				if (existierendeDaten.containsKey("Entry")) {
					Map<String, Object> entryMap = (Map<String, Object>) existierendeDaten.get("Entry");
					
					String eintragNummerKey = String.valueOf(teamid);
					if (entryMap.containsKey(eintragNummerKey)) {
						entryMap.remove(eintragNummerKey);
						entryMap.put(eintragNummerKey, neuerEintrag);
						objectmapper.writeValue(datei, entryMap);
					}
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
					
	}
	
	public List<Integer> getTeamIDsVonGespeichertenTeams() {
		return jsonParserEigene.getPrognoseIDs();
	}
	
	public Map<String, Double> getAttributeVonGespeichertemTeam(Integer teamID) {
		return jsonParserEigene.parseErgebnisseAusGespeichertemTeam(teamID);
	}
	
	 public Integer getHoechsteTeamIDVonGespeicherten() {
	        return jsonParserEigene.parseHoechsteTeamID();
	 }
	 
	 public String getTeamNamenUeberID (Integer teamid) {
		 return jsonParserEigene.parseTeamNamenUeberID(teamid);
	 }

	    public Integer getPassendeTeamID() {
	        jsonParserEigene.refresheJsonNode();
	        try {
	            File datei = new File(System.getProperty("user.home") + File.separator + "erstellteTeams.json");
	            Map<String, Object> existierendeDaten = null;

	            // Überprüfe, ob die Datei existiert und nicht leer ist
	            if (datei.exists() && datei.length() > 0) {
	                // Lese die vorhandenen Einträge aus der Datei
	                existierendeDaten = objectmapper.readValue(datei, Map.class);
	            }

	            else {
	                // Falls die Datei leer oder nicht vorhanden ist, initialisiere
	                // eine neue Map
	                existierendeDaten = new HashMap<>();
	            }
	            int neuerEintragNummer = 0;
	            List<Integer> ids = getTeamIDsVonGespeichertenTeams();
	            Integer maxValue = getHoechsteTeamIDVonGespeicherten();
	            for (Integer i = 0; i < maxValue + 1; i++) {
	                if (!ids.contains(i)) {
	                    neuerEintragNummer = i;
	                    break;
	                }
	                else {
	                    neuerEintragNummer = maxValue + 1;

	                }
	            }

	            return neuerEintragNummer;
	        }
	        catch (IOException e) {
	            return null;
	        }
	    }
	    
	    
	    public Set<String> erstelleAttributStringSetFuerErgebnisse() {
	    	Set<String> attributnamen = new HashSet<String>();
	    	
	    	attributnamen.add("teambewertung");
	    	attributnamen.add("moral");
	    	attributnamen.add("form");
	    	
	    	return attributnamen;
	    } 

}
