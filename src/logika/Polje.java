package logika;

import java.awt.Point;
import java.util.ArrayList;

import konzola.Igrica;

public class Polje {
	private int tipPolja;
	private int x;
	private int y;
	private boolean jeLiSlobodnoPolje;
	private ArrayList<Point> susjednaPolja;
	private int redniBrojVrtloga = 0;
	private int redniBrojPiratskogBroda = 0;

	public Polje(int tipPolja, boolean jeLiSlobodnoPolje, int x, int y) {
		this.tipPolja = tipPolja;
		this.x = x;
		this.y = y;
		this.jeLiSlobodnoPolje = jeLiSlobodnoPolje;
		this.susjednaPolja = new ArrayList<Point>(dajSusjednaPolja(x, y));
	}

	public Polje(int tipPolja, boolean jeLiSlobodnoPolje, int x, int y, int redniBrojVrtloga,
			int redniBrojPiratskogBroda) {
		this(tipPolja, jeLiSlobodnoPolje, x, y);
		this.susjednaPolja = new ArrayList<Point>(dajSusjednaPolja(x, y));
		this.redniBrojVrtloga = redniBrojVrtloga;
		this.redniBrojPiratskogBroda = redniBrojPiratskogBroda;
	}

	public Polje(Polje p) {
		this.tipPolja = p.dajTipPolja();
		this.x = p.dajX();
		this.y = p.dajY();
		this.jeLiSlobodnoPolje = p.jeLiSlobodnoPolje();
		this.susjednaPolja = kopirajListu(p.susjednaPolja);
		this.redniBrojVrtloga = p.dajRedniBrojVrtloga();
		this.redniBrojPiratskogBroda = p.dajRedniBrojPiratskogBroda();
	}

	public int dajTipPolja() {
		return tipPolja;
	}

	public boolean jeLiSlobodnoPolje() {
		return jeLiSlobodnoPolje;
	}

	public int dajX() {
		return x;
	}

	public int dajY() {
		return y;
	}

	public void postaviX(int x) {
		this.x = x;
	}

	public void postaviY(int y) {
		this.y = y;
	}

	public void postaviPolje(int tip) {
		this.tipPolja = tip;
	}

	public ArrayList<Point> dajSusjednaPolja() {
		return susjednaPolja;
	}

	public int dajRedniBrojVrtloga() {
		return redniBrojVrtloga;
	}

	public int dajRedniBrojPiratskogBroda() {
		return redniBrojPiratskogBroda;
	}

	public void postaviSusjednaPolja(ArrayList<Point> susjednaPolja) {

		ArrayList<Point> novaPolja = new ArrayList<Point>();
		susjednaPolja.forEach(susjed -> {
			novaPolja.add(susjed);
		});
		this.susjednaPolja = novaPolja;
	}

	public void ponovoRacunajSusjede() {
		susjednaPolja = dajSusjednaPolja(this.dajX(), this.dajY());
	}

	private ArrayList<Point> dajSusjednaPolja(int i, int j) {
		ArrayList<Point> zaVratit = new ArrayList<Point>();
		zaVratit.add(new Point(i - 1, j - 1));
		zaVratit.add(new Point(i, j - 1));
		zaVratit.add(new Point(i + 1, j - 1));
		zaVratit.add(new Point(i + 1, j));
		zaVratit.add(new Point(i + 1, j + 1));
		zaVratit.add(new Point(i, j + 1));
		zaVratit.add(new Point(i - 1, j + 1));
		zaVratit.add(new Point(i - 1, j));
		return zaVratit;
	}

	private boolean provjeriDiraLiKrajPloce(ArrayList<Point> susjednaPolja) {
		boolean zaVratit = false;
		for (int i = 0; i < susjednaPolja.size(); i++) {
			double x = susjednaPolja.get(i).getX();
			double y = susjednaPolja.get(i).getY();
			if (x < 0 || y < 0 || x > Igrica.REDOVI - 1 || y > Igrica.KOLONE - 1)
				zaVratit = true;
		}
		return zaVratit;
	}

	private ArrayList<Point> kopirajListu(ArrayList<Point> lista) {
		ArrayList<Point> zaVratit = new ArrayList<Point>();
		lista.forEach(element -> {
			zaVratit.add(new Point(element));
		});
		return zaVratit;
	}
}
