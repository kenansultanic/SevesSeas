package logika;

import java.awt.Point;
import java.util.ArrayList;

import konzola.Igrica;

/**
 * 
 * @author Kenan
 *
 */
public class Polje {
	private int tipPolja;
	private int x;
	private int y;
	private boolean jeLiSlobodnoPolje;
	private ArrayList<Point> susjednaPolja;
	private int redniBrojVrtloga = 0;
	private int redniBrojPiratskogBroda = 0;

	/**
	 * Konstruktor
	 * 
	 * @param jeLiBrod
	 * @param jeLiOtok
	 * @param i
	 * @param j
	 */
	public Polje(int tipPolja, boolean jeLiSlobodnoPolje, int x, int y) {
		this.tipPolja = tipPolja;
		this.x = x;
		this.y = y;
		this.jeLiSlobodnoPolje = jeLiSlobodnoPolje;
		this.susjednaPolja = new ArrayList<Point>(dajSusjednaPolja(x, y));
	}

	/**
	 * Konstruktor
	 * 
	 * @param tipPolja
	 * @param jeLiSlobodnoPolje
	 * @param x
	 * @param y
	 * @param redniBrojVrtloga
	 * @param redniBrojPiratskogBroda
	 */
	public Polje(int tipPolja, boolean jeLiSlobodnoPolje, int x, int y, int redniBrojVrtloga,
			int redniBrojPiratskogBroda) {
		this(tipPolja, jeLiSlobodnoPolje, x, y);
		this.susjednaPolja = new ArrayList<Point>(dajSusjednaPolja(x, y));
		this.redniBrojVrtloga = redniBrojVrtloga;
		this.redniBrojPiratskogBroda = redniBrojPiratskogBroda;
	}

	/**
	 * Konstruktor kopije
	 * 
	 * @param p
	 */
	public Polje(Polje p) {
		this.tipPolja = p.dajTipPolja();
		this.x = p.dajX();
		this.y = p.dajY();
		this.jeLiSlobodnoPolje = p.jeLiSlobodnoPolje();
		this.susjednaPolja = kopirajListu(p.susjednaPolja);
		this.redniBrojVrtloga = p.dajRedniBrojVrtloga();
		this.redniBrojPiratskogBroda = p.dajRedniBrojPiratskogBroda();
	}

	/**
	 * Get metoda za tip polja
	 * 
	 * @return
	 */
	public int dajTipPolja() {
		return tipPolja;
	}

	/**
	 * Metoda koja nam govori da li se radi o slobodnom polju ili ne.
	 * 
	 * @return
	 */
	public boolean jeLiSlobodnoPolje() {
		return jeLiSlobodnoPolje;
	}

	/**
	 * Get metoda za X koordinatu.
	 * 
	 * @return
	 */
	public int dajX() {
		return x;
	}

	/**
	 * Get metoda za Y koordinatu
	 * 
	 * @return
	 */
	public int dajY() {
		return y;
	}

	/**
	 * Set metoda za X koordinatu
	 * 
	 * @param x
	 */
	public void postaviX(int x) {
		this.x = x;
	}

	/**
	 * Set metoda za Y koordinatu
	 * 
	 * @param y
	 */
	public void postaviY(int y) {
		this.y = y;
	}

	/**
	 * Set metoda za tip polja
	 * 
	 * @param tip
	 */
	public void postaviPolje(int tip) {
		this.tipPolja = tip;
	}

	/**
	 * Get metoda za susjedna polja
	 * 
	 * @return
	 */
	public ArrayList<Point> dajSusjednaPolja() {
		return susjednaPolja;
	}

	/**
	 * Get metoda za redni broj vrtloga
	 * 
	 * @return
	 */
	public int dajRedniBrojVrtloga() {
		return redniBrojVrtloga;
	}

	/**
	 * Get metoda za redni broj piratskog broda
	 * 
	 * @return
	 */
	public int dajRedniBrojPiratskogBroda() {
		return redniBrojPiratskogBroda;
	}

	/**
	 * Set metoda za susjedna polja
	 * 
	 * @param susjednaPolja
	 */
	public void postaviSusjednaPolja(ArrayList<Point> susjednaPolja) {

		ArrayList<Point> novaPolja = new ArrayList<Point>();
		susjednaPolja.forEach(susjed -> {
			novaPolja.add(susjed);
		});
		this.susjednaPolja = novaPolja;
	}

	/**
	 * Pomoćna metoda koja se koristi za ponovno računanje susjednih polja svakog
	 * polja nakon što se ono pomjeri.
	 */
	public void ponovoRacunajSusjede() {
		susjednaPolja = dajSusjednaPolja(this.dajX(), this.dajY());
	}

	/**
	 * Privatna metoda za generisanje niza od kojeg svaki element predstavlja polje
	 * sa koordinatama i i j, ili njemu susjedna polja, počevši od gornjeg lijevog
	 * susjeda i krećući se u smjeru kazaljke na satu.
	 * 
	 * @param i X koordinata
	 * @param j Y koordinata
	 * @return Polje sa svojim susjedima
	 */
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

	/**
	 * Privatna metoda koja provjerava da li se radi o rubnom polju ili ne
	 * 
	 * @param susjednaPolja
	 * @return
	 */
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

	/**
	 * Privatna metoda koja pravi duboku kopiju liste tačaka.
	 * 
	 * @param lista
	 * @return
	 */
	private ArrayList<Point> kopirajListu(ArrayList<Point> lista) {
		ArrayList<Point> zaVratit = new ArrayList<Point>();
		lista.forEach(element -> {
			zaVratit.add(new Point(element));
		});
		return zaVratit;
	}
}
