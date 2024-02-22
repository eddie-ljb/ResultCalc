package de.ebader.resultcalc.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.nio.file.spi.FileSystemProvider;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.swing.JComboBox;

@SuppressWarnings("unused")
public class Homepage extends JFrame {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private JPanel contentPane;
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
		getContentPane().setBackground(new Color(255, 255, 255));
		this.calc = calc;
		this.jsonAbfrager = jsonAbfrager;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 600);
		getContentPane().setLayout(null);
		setResizable(false);
		Image logo = new ImageIcon(this.getClass().getResource("/resultcalclogo.png")).getImage();
		
		setIconImage(logo);
		
		JLabel lblHeader = new JLabel("ResultCalc by Etienne Bader");
		lblHeader.setBackground(new Color(192, 192, 192));
		lblHeader.setHorizontalAlignment(SwingConstants.CENTER);
		lblHeader.setFont(new Font("Tahoma", Font.BOLD, 32));
		lblHeader.setBounds(10, 10, 716, 39);
		getContentPane().add(lblHeader);
		
		JComboBox comboBoxTeam1 = new JComboBox();
		comboBoxTeam1.setBackground(new Color(255, 255, 255));
		comboBoxTeam1.setBounds(233, 291, 212, 21);
		getContentPane().add(comboBoxTeam1);
		
		JComboBox comboBoxTeam2 = new JComboBox();
		comboBoxTeam2.setBackground(new Color(255, 255, 255));
		comboBoxTeam2.setBounds(481, 291, 212, 21);
		getContentPane().add(comboBoxTeam2);
		
		List<Integer> teamIDs = jsonAbfrager.getTeamIDsVonGespeichertenTeams();
		Map<Integer, String> teamnamen = new HashMap<Integer, String>();
		
		for (Integer integer : teamIDs) {
			teamnamen.put(integer, jsonAbfrager.getTeamNamenUeberID(integer));
		}
		
		for (Integer integer : teamIDs) {
			comboBoxTeam1.addItem(teamnamen.get(integer));
			comboBoxTeam2.addItem(teamnamen.get(integer));
		}
		
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
				comboBoxTeam1.removeAllItems();
				comboBoxTeam2.removeAllItems();
				
				List<Integer> teamIDs = jsonAbfrager.getTeamIDsVonGespeichertenTeams();
				Map<Integer, String> teamnamen = new HashMap<Integer, String>();
				
				for (Integer integer : teamIDs) {
					teamnamen.put(integer, jsonAbfrager.getTeamNamenUeberID(integer));
				}
				
				for (Integer integer : teamIDs) {
					comboBoxTeam1.addItem(teamnamen.get(integer));
					comboBoxTeam2.addItem(teamnamen.get(integer));
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
				
				comboBoxTeam1.removeAllItems();
				comboBoxTeam2.removeAllItems();
				
				List<Integer> teamIDs = jsonAbfrager.getTeamIDsVonGespeichertenTeams();
				Map<Integer, String> teamnamen = new HashMap<Integer, String>();
				
				for (Integer integer : teamIDs) {
					teamnamen.put(integer, jsonAbfrager.getTeamNamenUeberID(integer));
				}
				
				for (Integer integer : teamIDs) {
					if(integer != id) {
						comboBoxTeam1.addItem(teamnamen.get(integer));
						comboBoxTeam2.addItem(teamnamen.get(integer));
					}
				}
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
				Map<String, Double> attribute = new HashMap<String, Double>();
				Map<Integer, Double> sortierListe = new HashMap<Integer, Double>();
				for (Integer integer : teamIDs) {
					attribute = jsonAbfrager.getAttributeVonGespeichertemTeam(integer);

					sortierListe.put(integer, attribute.get("teambewertung"));
				}
				
				sortierListe = sortByValue(sortierListe);
//
//				Set<Integer> keySet = sortierListe.keySet();
//				int zaehler = 0;
//				for (Integer integer : keySet) {
//					attribute = jsonAbfrager.getAttributeVonGespeichertemTeam(integer);
//					
//					if (zaehler == 0) {
//					teamliste += "ID: " + integer + ", Namen: " + jsonAbfrager.getTeamNamenUeberID(integer) + ", Bewertung: " + attribute.get("teambewertung") + ", Moral: " + attribute.get("moral") + ", Form: " + attribute.get("form") + ";	------";
//					zaehler++;
//					} else if (zaehler == 1) {
//						teamliste += "ID: " + integer + ", Namen: " + jsonAbfrager.getTeamNamenUeberID(integer) + ", Bewertung: " + attribute.get("teambewertung") + ", Moral: " + attribute.get("moral") + ", Form: " + attribute.get("form") + "; \n";
//						zaehler = 0;					}
//					
//				}
//				
//				int returnValue = JOptionPane.showConfirmDialog(null, teamliste);
				
				JTextArea textArea = new JTextArea();
		        textArea.setEditable(false); // Stelle die JTextArea als nicht editierbar ein

		        // Iteriere durch die sortierte Liste und füge die Informationen in die JTextArea ein
		        for (Map.Entry<Integer, ?> entry : sortierListe.entrySet()) {
		            int key = entry.getKey();
		            // Annahme: attribute ist der entsprechende Wert für den Schlüssel
		            attribute = jsonAbfrager.getAttributeVonGespeichertemTeam(key);
		            textArea.append("ID: " + key + ", Namen: " + jsonAbfrager.getTeamNamenUeberID(key) +
		                            ", Bewertung: " + attribute.get("teambewertung") +
		                            ", Moral: " + attribute.get("moral") +
		                            ", Form: " + attribute.get("form") + ";\n\n");
		        }
		        textArea.setFont(new Font("Tahoma", Font.PLAIN, 12));
		        
		        // Erstelle eine JScrollPane und füge die JTextArea hinzu
		        JScrollPane scrollPane = new JScrollPane(textArea);
		        scrollPane.setPreferredSize(new Dimension(500, 400)); // Setze die Größe des Scrollbereichs

		        // Zeige das JOptionPane mit der JScrollPane an
		        JOptionPane.showMessageDialog(null, scrollPane);
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
		
		
		JButton btnCalculate = new JButton("CALCULATE");
		btnCalculate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				String teamname1 = (String) comboBoxTeam1.getItemAt(comboBoxTeam1.getSelectedIndex());
				String teamname2 = (String) comboBoxTeam2.getItemAt(comboBoxTeam2.getSelectedIndex());
				
				System.out.println(teamname2);
				
				int team1ID = jsonAbfrager.getTeamIDVonTeamNamen(teamname1);
				int team2ID = jsonAbfrager.getTeamIDVonTeamNamen(teamname2);
				
				System.out.println(team1ID);
				System.out.println(team2ID);
				
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
		
		JLabel lblLogo = new JLabel();
		lblLogo.setIcon(new ImageIcon(this.getClass().getResource("/resultcalclogoKl.png")));
		lblLogo.setBounds(10, 362, 191, 191);
		getContentPane().add(lblLogo);
	}
	
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, Collections.reverseOrder(Entry.comparingByValue()));

        Map<K, V> result = new LinkedHashMap<>();
        for (Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }
}
