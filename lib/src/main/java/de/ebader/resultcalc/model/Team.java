package de.ebader.resultcalc.model;

public class Team {
	
	double moral;
	double form;
	double bewertung;
	String name;
	int id;
	
	public Team(String name, int id, double moral, double form, double bewertung) {
		this.bewertung = bewertung;
		this.form = form;
		this.id = id;
		this.moral = moral;
		this.name = name;
	}

	public double getMoral() {
		return moral;
	}

	public void setMoral(int moral) {
		this.moral = moral;
	}

	public double getForm() {
		return form;
	}

	public void setForm(int form) {
		this.form = form;
	}

	public double getBewertung() {
		return bewertung;
	}

	public void setBewertung(int bewertung) {
		this.bewertung = bewertung;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}
	
	

}
