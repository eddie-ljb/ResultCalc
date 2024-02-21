package de.ebader.resultcalc;

import java.awt.EventQueue;
import de.ebader.resultcalc.controller.Calc;
import de.ebader.resultcalc.controller.ErgebnisBerechner;
import de.ebader.resultcalc.controller.JsonAbfragen;
import de.ebader.resultcalc.controller.JsonAbfrager;
import de.ebader.resultcalc.view.Homepage;


public class Startpunkt {
	
	public static void main(String[] args) {
		Calc calc = new ErgebnisBerechner();
		JsonAbfragen jsonAbfrager = new JsonAbfrager();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Homepage frame = new Homepage(calc, jsonAbfrager);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
