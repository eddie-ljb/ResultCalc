package de.ebader.resultcalc.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import de.ebader.resultcalc.controller.Calc;
import de.ebader.resultcalc.controller.ErgebnisBerechner;
import de.ebader.resultcalc.controller.JsonAbfragen;
import de.ebader.resultcalc.controller.JsonAbfrager;
import de.ebader.resultcalc.model.Team;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JTextArea;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.nio.file.spi.FileSystemProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unused")
public class Homepage extends JFrame {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private JPanel contentPane;
	private JTextField textFieldFirstTeam;
	private JTextField textFieldSecondTeam;
	Calc calc;
	JsonAbfragen jsonAbfrager;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Calc calc = new ErgebnisBerechner();
					JsonAbfragen jsonAbfrager = new JsonAbfrager();
					Homepage frame = new Homepage(calc, jsonAbfrager);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Homepage(Calc calc, JsonAbfragen jsonAbfrager) {
		this.calc = calc;
		this.jsonAbfrager = jsonAbfrager;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 600);
		getContentPane().setLayout(null);
		setResizable(false);
		
		
		JLabel lblHeader = new JLabel("ResultCalc by Etienne Bader");
		lblHeader.setBackground(new Color(192, 192, 192));
		lblHeader.setHorizontalAlignment(SwingConstants.CENTER);
		lblHeader.setFont(new Font("Tahoma", Font.BOLD, 32));
		lblHeader.setBounds(10, 10, 716, 39);
		getContentPane().add(lblHeader);
		
		JButton btnNewTeam = new JButton("NEW TEAM");
		btnNewTeam.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String name = JOptionPane.showInputDialog("Name des Teams?");
				double bewertung = Double.valueOf(JOptionPane.showInputDialog("Team-Bewertung? (-100 bis +100)"));
				double form = Double.valueOf(JOptionPane.showInputDialog("Team-Form? (-100 bis +100)"));
				double moral = Double.valueOf(JOptionPane.showInputDialog("Team-Moral? (-100 bis +100)"));
				Map<String, Double> attribute = new HashMap<String, Double>();
				Set<String> attributNamenSet = jsonAbfrager.erstelleAttributStringSetFuerErgebnisse();
				attribute.put("teambewertung", bewertung);
				attribute.put("moral", moral);
				attribute.put("form", form);
				int returnValue = JOptionPane.showConfirmDialog(null, "Team erstellt mit folgenden Werten: Name " + name + ", Bewertung " + bewertung + ", Form " + form + ", Moral " + moral);
				if (returnValue == 0) {
					jsonAbfrager.getSpeichereInJson(attribute, attributNamenSet, name);
				}
			}
		});
		
		btnNewTeam.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnNewTeam.setBounds(10, 212, 160, 40);
		getContentPane().add(btnNewTeam);
		
		JButton btnDeleteTeam = new JButton("DELETE TEAM");
		btnDeleteTeam.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int id = Integer.valueOf(JOptionPane.showInputDialog("Das Team mit welcher ID soll ich löschen?"));
				jsonAbfrager.getLoescheEintragUnterEntry(id);
			}
		});
		
		btnDeleteTeam.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnDeleteTeam.setBounds(10, 262, 160, 40);
		getContentPane().add(btnDeleteTeam);
		
		JButton btnShowTeams = new JButton("SHOW TEAMS");
		btnShowTeams.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				String teamliste = "Folgende Teams sind bereits erstellt: \n";
				List<Integer> teamIDs = jsonAbfrager.getTeamIDsVonGespeichertenTeams();
				for (Integer integer : teamIDs) {
					System.out.println(integer);
				}
				for (Integer integer : teamIDs) {
					Map<String, Double> attribute = new HashMap<String, Double>();
					attribute = jsonAbfrager.getAttributeVonGespeichertemTeam(integer);
					teamliste += "Team-ID: " + integer + ", Namen: " + jsonAbfrager.getTeamNamenUeberID(integer) + ", Bewertung: " + attribute.get("teambewertung") + ", Moral: " + attribute.get("moral") + ", Form: " + attribute.get("form") + "; \n";
				}
				
				int returnValue = JOptionPane.showConfirmDialog(null, teamliste);
			}
		});
		
		btnShowTeams.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnShowTeams.setBounds(10, 312, 160, 40);
		getContentPane().add(btnShowTeams);
		
		
		JLabel lblFirstTeam = new JLabel("FIRST TEAM:");
		lblFirstTeam.setHorizontalAlignment(SwingConstants.CENTER);
		lblFirstTeam.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblFirstTeam.setBounds(278, 211, 140, 40);
		getContentPane().add(lblFirstTeam);
		
		JLabel lblSecondTeam = new JLabel("SECOND TEAM:");
		lblSecondTeam.setHorizontalAlignment(SwingConstants.CENTER);
		lblSecondTeam.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblSecondTeam.setBounds(497, 211, 152, 40);
		getContentPane().add(lblSecondTeam);
		
		textFieldFirstTeam = new JTextField();
		textFieldFirstTeam.setBounds(288, 262, 130, 19);
		getContentPane().add(textFieldFirstTeam);
		textFieldFirstTeam.setColumns(10);
		
		textFieldSecondTeam = new JTextField();
		textFieldSecondTeam.setBounds(507, 262, 140, 19);
		getContentPane().add(textFieldSecondTeam);
		textFieldSecondTeam.setColumns(10);
		
		JButton btnCalculate = new JButton("CALCULATE");
		btnCalculate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int team1ID = Integer.valueOf(textFieldFirstTeam.getText());
				int team2ID = Integer.valueOf(textFieldSecondTeam.getText());
				String teamname1 = jsonAbfrager.getTeamNamenUeberID(team1ID);
				String teamname2 = jsonAbfrager.getTeamNamenUeberID(team2ID);
				
				Map<String, Double> attributeTeam1 = jsonAbfrager.getAttributeVonGespeichertemTeam(team1ID);
				Map<String, Double> attributeTeam2 = jsonAbfrager.getAttributeVonGespeichertemTeam(team2ID);
				Set<String> attributSet = jsonAbfrager.erstelleAttributStringSetFuerErgebnisse();
				
				Team team1 = new Team(teamname1, team1ID, attributeTeam1.get("moral"), attributeTeam1.get("form"), attributeTeam1.get("teambewertung"));
				Team team2 = new Team(teamname2, team2ID, attributeTeam2.get("moral"), attributeTeam2.get("form"), attributeTeam2.get("teambewertung"));
				
				
				List<Integer> ergebnisse = calc.ergebnisBerechnen(team1, team2);
				@SuppressWarnings("unused")
				int id = Integer.valueOf(JOptionPane.showConfirmDialog(null, "Das sind die Ergebnisse: \n" + team1.getName() + " " + ergebnisse.get(0) + " vs. " + ergebnisse.get(1) + " " + team2.getName()));
			}
		});
		
		btnCalculate.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnCalculate.setBounds(566, 513, 160, 40);
		getContentPane().add(btnCalculate);
		
		
		
		JButton btnEditTeam = new JButton("EDIT TEAM");
		btnEditTeam.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int id = Integer.valueOf(JOptionPane.showInputDialog("Das Team mit welcher Team-ID soll bearbeitet werden?"));
				int bewertung = Integer.valueOf(JOptionPane.showInputDialog("Team-Bewertung? (-100 bis +100)"));
				int form = Integer.valueOf(JOptionPane.showInputDialog("Team-Form? (-100 bis +100)"));
				int moral = Integer.valueOf(JOptionPane.showInputDialog("Team-Moral? (-100 bis +100)"));
				int returnValue = JOptionPane.showConfirmDialog(null, "Folgendes Team wird bearbeitet: \n ID: " + id + ", Bewertung " + bewertung + ", Form " + form + ", Moral " + moral);
				if (returnValue == 0) {
					jsonAbfrager.getBearbeiteEntryVonID(id, bewertung, moral, form);
				}
			}
		});
		
		btnEditTeam.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnEditTeam.setBounds(10, 162, 160, 40);
		getContentPane().add(btnEditTeam);

		
	}
	
	
	
	
}