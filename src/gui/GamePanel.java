/**
 * 
 */
package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import konzola.Igrica;
import logika.PoljaZaIgricu;
import logika.Polje;
import logika.Udaljenost;
import logika.Slike;
import logika.Strijela;
import logika.TipoviPolja;

public class GamePanel extends JPanel {

	private GlavniProzor glavniProzor;
	private PoljaZaIgricu poljaZaIgricu;
	private Polje[][] matricaPolja;
	private Polje igrac;
	private ArrayList<Point> igraceviSusjedi;
	private boolean igracNaPotezu = true;
	private int tezina;
	private int level;
	private Strijela strijela;
	private int stranicaKvadrata = 50;

	public GamePanel(GlavniProzor glavniProzor, PoljaZaIgricu poljaZaIgricu) {

		this.glavniProzor = glavniProzor;
		this.poljaZaIgricu = poljaZaIgricu;
		igrac = poljaZaIgricu.dajIgraca();
		igraceviSusjedi = igrac.dajSusjednaPolja();
		tezina = glavniProzor.dajTezinu();
		level = glavniProzor.dajLevel();
		matricaPolja = poljaZaIgricu.dajMatricuPolja();
		strijela = new Strijela();

		repaint();

		this.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
			}

			@Override
			public void mouseMoved(MouseEvent e) {

				igrac = poljaZaIgricu.dajIgraca();
				Point lokacijaIgraca = new Point(igrac.dajX(), igrac.dajY());
				igraceviSusjedi = igrac.dajSusjednaPolja();

				strijela = dajTipStrijele(lokacijaIgraca, e.getX(), e.getY(), igraceviSusjedi);

				repaint();
			}
		});

		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				if (jeLiIgracNaPotezu())
					proslijediKlik(dajPolozajKliknutogPolja(me.getX(), me.getY()));
			}
		});

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		for (int i = 1; i < Igrica.REDOVI - 1; i++) {
			int iLocal = i - 1;
			for (int j = 1; j < Igrica.KOLONE - 1; j++) {

				int jLocal = j - 1;

				Polje p = matricaPolja[i][j];
				g.setColor(Color.BLUE);

				if (p.dajTipPolja() == TipoviPolja.PRAZNO_POLJE)
					g.fillRect(iLocal * stranicaKvadrata, jLocal * stranicaKvadrata, stranicaKvadrata,
							stranicaKvadrata);
				else if (p.dajTipPolja() == TipoviPolja.GRANICA) {
					g.setColor(Color.DARK_GRAY);
					g.fillRect(iLocal * stranicaKvadrata, jLocal * stranicaKvadrata, stranicaKvadrata,
							stranicaKvadrata);
				} else
					g.drawImage(Slike.dajSliku(p.dajTipPolja()), iLocal * stranicaKvadrata, jLocal * stranicaKvadrata,
							stranicaKvadrata, stranicaKvadrata, Color.BLUE, getFocusCycleRootAncestor());

				if (strijela.getOrijentacija() != null && strijela.getX() == i && strijela.getY() == j)
					g.drawImage(Slike.dajStrijelu(strijela.getOrijentacija()), iLocal * stranicaKvadrata,
							jLocal * stranicaKvadrata, stranicaKvadrata, stranicaKvadrata, Color.BLUE,
							getFocusCycleRootAncestor());
			}
		}
	}

	private Strijela dajTipStrijele(Point lokacijaIgraca, int x, int y, ArrayList<Point> igraceviSusjedi) {
		double igracPoX = (lokacijaIgraca.getX() - 1) * stranicaKvadrata;
		double igracPoY = (lokacijaIgraca.getY() - 1) * stranicaKvadrata;
		Strijela praznaStrijela = new Strijela();

		if (jeLiTackaUKvadratu(igracPoX - stranicaKvadrata, igracPoY - stranicaKvadrata, x, y)) {
			Point susjed = igraceviSusjedi.get(0);
			if (poljaZaIgricu.dajPolje((int) susjed.getX(), (int) susjed.getY()).jeLiSlobodnoPolje())
				return new Strijela("NW", susjed.getX(), susjed.getY());
			return praznaStrijela;
		}
		if (jeLiTackaUKvadratu(igracPoX, igracPoY - stranicaKvadrata, x, y)) {
			Point susjed = igraceviSusjedi.get(1);
			if (poljaZaIgricu.dajPolje((int) susjed.getX(), (int) susjed.getY()).jeLiSlobodnoPolje())
				return new Strijela("N", susjed.getX(), susjed.getY());
			return praznaStrijela;
		}
		if (jeLiTackaUKvadratu(igracPoX + stranicaKvadrata, igracPoY - stranicaKvadrata, x, y)) {
			Point susjed = igraceviSusjedi.get(2);
			if (poljaZaIgricu.dajPolje((int) susjed.getX(), (int) susjed.getY()).jeLiSlobodnoPolje())
				return new Strijela("NE", susjed.getX(), susjed.getY());
			return praznaStrijela;
		}
		if (jeLiTackaUKvadratu(igracPoX + stranicaKvadrata, igracPoY, x, y)) {
			Point susjed = igraceviSusjedi.get(3);
			if (poljaZaIgricu.dajPolje((int) susjed.getX(), (int) susjed.getY()).jeLiSlobodnoPolje())
				return new Strijela("E", susjed.getX(), susjed.getY());
			return praznaStrijela;
		}
		if (jeLiTackaUKvadratu(igracPoX + stranicaKvadrata, igracPoY + stranicaKvadrata, x, y)) {
			Point susjed = igraceviSusjedi.get(4);
			if (poljaZaIgricu.dajPolje((int) susjed.getX(), (int) susjed.getY()).jeLiSlobodnoPolje())
				return new Strijela("SE", susjed.getX(), susjed.getY());
			return praznaStrijela;
		}
		if (jeLiTackaUKvadratu(igracPoX, igracPoY + stranicaKvadrata, x, y)) {
			Point susjed = igraceviSusjedi.get(5);
			if (poljaZaIgricu.dajPolje((int) susjed.getX(), (int) susjed.getY()).jeLiSlobodnoPolje())
				return new Strijela("S", susjed.getX(), susjed.getY());
			return praznaStrijela;
		}
		if (jeLiTackaUKvadratu(igracPoX - stranicaKvadrata, igracPoY + stranicaKvadrata, x, y)) {
			Point susjed = igraceviSusjedi.get(6);
			if (poljaZaIgricu.dajPolje((int) susjed.getX(), (int) susjed.getY()).jeLiSlobodnoPolje())
				return new Strijela("SW", susjed.getX(), susjed.getY());
			return praznaStrijela;
		}
		if (jeLiTackaUKvadratu(igracPoX - stranicaKvadrata, igracPoY, x, y)) {
			Point susjed = igraceviSusjedi.get(7);
			if (poljaZaIgricu.dajPolje((int) susjed.getX(), (int) susjed.getY()).jeLiSlobodnoPolje())
				return new Strijela("W", susjed.getX(), susjed.getY());
			return praznaStrijela;
		}
		return praznaStrijela;
	}

	private boolean jeLiTackaUKvadratu(double gornjeLijevoX, double gornjeLijevoY, double x, double y) {
		double donjeDesnoX = gornjeLijevoX + stranicaKvadrata;
		double donjeDesnoY = gornjeLijevoY + stranicaKvadrata;

		if (x > gornjeLijevoX && x < donjeDesnoX && y > gornjeLijevoY && y < donjeDesnoY)
			return true;
		return false;
	}

	private Point dajPolozajKliknutogPolja(int i, int j) {
		int x = i - (i % stranicaKvadrata);
		int y = j - (j % stranicaKvadrata);
		return new Point((x / stranicaKvadrata), (y / stranicaKvadrata));
	}

	private void proslijediKlik(Point polozaj) {
		if (igracNaPotezu) {
			glavniProzor.proslijediKlik((int) polozaj.getX(), (int) polozaj.getY());
			igracNaPotezu = false;
		}
	}

	public void izmjeniMatricuPolja(Polje[][] matricaPolja) {
		this.matricaPolja = matricaPolja;
		igrac = poljaZaIgricu.dajIgraca();
		igraceviSusjedi = igrac.dajSusjednaPolja();
		repaint();
	}

	private boolean jeLiIgracNaPotezu() {
		return glavniProzor.jeLiIgracNaPotezu();
	}

	public void postaviIgracNaPotezu() {
		igracNaPotezu = true;
	}
}
