package de.ebader.resultcalc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Result {
	
	int ergebnisTeamEins;
	int ergebnisTeamZwei;
	Team team1;
	Team team2;
	
	public Result(Team team1, Team team2) {
		this.team1 = team1;
		this.team2 = team2;
		this.ergebnisTeamEins = berechneErgebnis(team1, team2).get(0);
		this.ergebnisTeamZwei = berechneErgebnis(team1, team2).get(1);
	}
	
	public List<Integer> berechneErgebnis(Team team1, Team team2) {
		List<Integer> ergebnisse = new ArrayList<Integer>();
		double formdifferenz = team1.getForm() - team2.getForm();
		double bewertungsdifferenz = team1.getBewertung() - team2.getBewertung();
		double moraldifferenz = team1.getMoral() - team2.getMoral();
		double vorteil = berechneVorteil(formdifferenz, bewertungsdifferenz, moraldifferenz);
		Random r = new Random();
		int ergebnis1 = 0;
		int ergebnis2 = 0;
		int zufall = r.nextInt(0, 6);
		int random = 0;
		if (zufall == 0 || zufall == 1) {
			random = 0;
		} else if (zufall > 1 && zufall < 5) {
			random = 1;
		} else {
			random = 2;
		}
		
		if (vorteil > 0.0) {
			ergebnis2 = (int) (random * vorteil );
			ergebnis1 = gebeVerliererErgebnis(vorteil);
		} else if (vorteil < 0.0) {
			ergebnis1 = (int) (random * (vorteil * -1));
			ergebnis2 = gebeVerliererErgebnis(vorteil);
		}
		ergebnisse.add(ergebnis1);
		ergebnisse.add(ergebnis2);
		
		
		return ergebnisse;
	}
	
	private double berechneVorteil (double formdifferenz, double bewertungsdifferenz, double moraldifferenz) {
		double vorteil = 0.0;
		if (formdifferenz > -201 && formdifferenz < -100) {
			vorteil += 1.5;
		} else if (formdifferenz > -99 && formdifferenz < -4) {
			vorteil += 0.5;
		} else if (formdifferenz > -5 && formdifferenz < 5) {
			vorteil += 0;
		} else if (formdifferenz > 4 && formdifferenz < 101) {
			vorteil -= 0.5;
		} else if (formdifferenz > 100 && formdifferenz < 201) {
			vorteil -= 1.5;
		}
		
		if (bewertungsdifferenz > -201 && bewertungsdifferenz < -100) {
			vorteil += 1.5;
		} else if (bewertungsdifferenz > -99 && bewertungsdifferenz < -4) {
			vorteil += 0.5;
		} else if (bewertungsdifferenz > -5 && bewertungsdifferenz < 5) {
			vorteil += 0;
		} else if (bewertungsdifferenz > 4 && bewertungsdifferenz < 101) {
			vorteil -= 0.5;
		} else if (bewertungsdifferenz > 100 && bewertungsdifferenz < 201) {
			vorteil -= 1.5;
		}
		
		if (moraldifferenz > -201 && moraldifferenz < -100) {
			vorteil += 1.5;
		} else if (moraldifferenz > -99 && moraldifferenz < -4) {
			vorteil += 0.5;
		} else if (moraldifferenz > -5 && moraldifferenz < 5) {
			vorteil += 0;
		} else if (moraldifferenz > 4 && moraldifferenz < 101) {
			vorteil -= 0.5;
		} else if (moraldifferenz > 100 && moraldifferenz < 201) {
			vorteil -= 1.5;
		}
		
		return vorteil;
	}
	
	private int gebeVerliererErgebnis(double vorteil) {
		Random r = new Random();
		double vorteilBetrag = Double.valueOf(vorteil);
		vorteilBetrag = 4.5 - vorteilBetrag;
		int verrechnung = r.nextInt(-2, 6);
		if (verrechnung < 0) {
			return (int) Math.round(0.5 * vorteilBetrag) -1;
		} else if (verrechnung > -1 && verrechnung < 5) {
			return (int) Math.round(0.25 * vorteilBetrag) -1;
		} else {
			return (int) Math.round(0 * vorteilBetrag);
		}
	}

}
