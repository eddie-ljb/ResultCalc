package de.ebader.resultcalc.controller;

import java.util.ArrayList;
import java.util.List;

import de.ebader.resultcalc.model.Result;
import de.ebader.resultcalc.model.Team;

public class ErgebnisBerechner implements Calc {
	
	List<Team> teams = new ArrayList<Team>();
	int idzaehler = 0;
	
	public List<Integer> ergebnisBerechnen(Team team1, Team team2) {
		Result result = new Result(team1, team2);
		List<Integer> ergebnisse = result.berechneErgebnis(team1, team2);
		return ergebnisse;
	}

	@Override
	public List<Team> erstelleTeams(int moral1, int moral2, int form1, int form2, int bewertung1, int bewertung2, String name1, String name2) {
		List<Team> teams = new ArrayList<Team>();
		teams.add(new Team(name1, 0, moral1, form1, bewertung1));
		teams.add(new Team(name2, 1, moral2, form2, bewertung2));
		return teams;
	}

	@Override
	public void erstelleTeam(String name, int moral, int form, int bewertung) {
		teams.add(new Team(name, idzaehler, moral, form, bewertung));
		idzaehler++;
	}

	@Override
	public List<Team> showTeams() {
		return this.teams;
	}

	@Override
	public void loescheTeam(int id) {
		this.teams.remove(id);
	}

	@Override
	public Team getTeam(int id) {
		return teams.get(id);
	}

	@Override
	public void editTeam(int id, int form, int moral, int bewertung) {
		teams.get(id).setBewertung(bewertung);
		teams.get(id).setForm(form);
		teams.get(id).setMoral(moral);
	}
	

}
