package de.ebader.resultcalc.controller;

import java.util.List;

import de.ebader.resultcalc.model.Team;

public interface Calc {
	
	public List<Integer> ergebnisBerechnen(Team team1, Team team2);
	public List<Team> erstelleTeams(int moral1, int moral2, int form1, int form2, int bewertung1, int bewertung2, String name1, String name2);
	public void erstelleTeam(String name, int moral, int form, int bewertung);
	public List<Team> showTeams();
	public void loescheTeam(int id);
	public Team getTeam(int id);
	public void editTeam(int id, int form, int moral, int bewertung);

}
