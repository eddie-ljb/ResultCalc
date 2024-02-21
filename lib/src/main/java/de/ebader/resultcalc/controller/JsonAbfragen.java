package de.ebader.resultcalc.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface JsonAbfragen {
	
	public void getSpeichereInJson(Map<String, Double> attribute, Set<String> keysErgebnisse, String teamName);
	public void getLoescheEintragUnterEntry(int eintragNummer);
	public List<Integer> getTeamIDsVonGespeichertenTeams();
	public Map<String, Double> getAttributeVonGespeichertemTeam(Integer teamID);
	public Integer getHoechsteTeamIDVonGespeicherten();
	public Integer getPassendeTeamID();
	public Set<String> erstelleAttributStringSetFuerErgebnisse();
	public String getTeamNamenUeberID (Integer teamid);
	public void getBearbeiteEntryVonID(Integer teamid, double bewertung, double moral, double form);
	public int getTeamIDVonTeamNamen(String namen);

}
