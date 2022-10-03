package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import konzola.Igrica;

/**
 * 
 * @author Kenan
 *
 */
public class Kraj extends JFrame {

	Igrica igrica;

	/**
	 * Konstruktor
	 * 
	 * @param poruka
	 * @param igrica
	 */
	public Kraj(String poruka, Igrica igrica) {
		super("Kraj igre");
		this.igrica = igrica;

		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = .5;
		c.weighty = 1;

		c.gridx = 0;
		c.gridy = 0;
		add(new JLabel(poruka), c);
		c.gridy = 1;
		JButton b = new JButton("Nova igra");

		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				igrica.novaIgrica();
			}
		});

		add(new JButton("Nova igra"));
		setSize(400, 400);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
