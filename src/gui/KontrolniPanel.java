package gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 
 * @author Kenan
 *
 */
public class KontrolniPanel extends JPanel {
	
	JButton novaIgra;
	JButton odustani;
	JLabel level;
	JLabel tezina;
	JTextField levelText;
	JTextField tezinaText;

	/**
	 * Konstruktor
	 */
	public KontrolniPanel() {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		this.setBackground(Color.WHITE);
		
		odustani = new JButton("Odustani");
		level = new JLabel("Level:  ");
		tezina = new JLabel("Tezina:  ");
		levelText = new JTextField("1");
		tezinaText = new JTextField("1");
		levelText.setEditable(false);
		tezinaText.setEditable(false);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		c.weighty = .005;
		
		c.gridx = 0;
		c.gridy = 0;
		add(level, c);
		
		c.gridx = 0;
		c.gridy = 1;
		add(tezina, c);
		
		c.gridy = 0;
		c.gridx = 1;
		add(levelText, c);
		
		c.gridy = 1;
		c.gridx = 1;
		add(tezinaText, c);
		
		c.gridy = 2;
		c.gridx = 0;
		c.weighty = 1;
		JPanel emptyPanel = new JPanel();
		emptyPanel.setBackground(Color.WHITE);
		add(emptyPanel, c);		
	}
}
