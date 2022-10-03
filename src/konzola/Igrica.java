package konzola;

import java.awt.Point;
import java.util.ArrayList;

import gui.GlavniProzor;
import gui.Kraj;
import logika.PoljaZaIgricu;
import logika.Polje;

/**
 * 
 * @author Kenan
 *
 */
public class Igrica {

	private int level;
	private int tezina;
	private int igracNaPotezu = 0;
	private PoljaZaIgricu poljaZaIgricu;
	private Polje[] piratskiBrodovi;
	GlavniProzor glavniProzor;
	private ArrayList<ArrayList<Point>> putanjePiratskihBrodova;
	public static final int REDOVI = 11 + 2;
	public static final int KOLONE = 11 + 2;

	/**
	 * Konstruktor
	 */
	public Igrica() {

		novaIgrica();
		glavniProzor = new GlavniProzor(this);
	}

	/**
	 * Get metoda za level
	 * 
	 * @return
	 */
	public int dajLevel() {
		return level;
	}

	/**
	 * Get metoda za tezinu
	 * 
	 * @return
	 */
	public int dajTezinu() {
		return tezina;
	}

	/**
	 * Get metoda za polja za igricu
	 * 
	 * @return
	 */
	public PoljaZaIgricu dajPoljaZaIgricu() {
		return poljaZaIgricu;
	}

	/**
	 * Javna metoda koja prima informacije o kliknutom polju od glavnog prozora i
	 * iste proslijedi polju za igricu.
	 * 
	 * @param x
	 * @param y
	 */
	public void proslijediKlik(int x, int y) {
		poljaZaIgricu.primiKlik(x, y);
	}

	/**
	 * Javna metoda koja proslijeđuje izmjenjenu matricu polja
	 * 
	 * @param matricaPolja
	 */
	public void proslijediIzmjeneMatricePolja(Polje[][] matricaPolja) {
		glavniProzor.proslijediIzmjeneMatricePolja(matricaPolja);
	}

	/**
	 * Javna metoda koja provjerava da li je igrač na potezu
	 * 
	 * @return
	 */
	public boolean jeLiIgracNaPotezu() {
		igracKliknuoNaPodlogu();
		return igracNaPotezu % 2 == 0;
	}

	public void krajLevela(boolean igracPobjedio) {
		if (!igracPobjedio) {
			kraj();
		}
	}

	/**
	 * Pomoćna metoda koja detektuje klik na podlogu
	 */
	private void igracKliknuoNaPodlogu() {
		igracNaPotezu++;
		if (!(igracNaPotezu % 2 == 0))
			odigrajPotez();
	}

	/**
	 * Pomoćna metoda koja odigra potez i računa novu putanju za piratski brod
	 */
	private void odigrajPotez() {
		for (int i = 0; i < piratskiBrodovi.length; i++) {
			putanjePiratskihBrodova.set(i,
					poljaZaIgricu.putanjaPiratskiBrodDoIgrac(piratskiBrodovi[i].dajRedniBrojPiratskogBroda()));
		}
		pomjeriPiratskeBrodove();
	}

	/**
	 * Pomoćna metoda koja pomjera piratski brod
	 */
	private void pomjeriPiratskeBrodove() {
		poljaZaIgricu.pomjeriPiratskeBrodove(putanjePiratskihBrodova, piratskiBrodovi);
		postaviIgracNaPotezu();
	}

	/**
	 * Pomoćna metoda koja postavlja igrača na potez
	 */
	private void postaviIgracNaPotezu() {
		glavniProzor.postaviIgracNaPotezu();
	}

	/**
	 * Pomoćna metoda koja otvara prozor koji obavještava igrača o kraju igre
	 */
	private void kraj() {
		new Kraj("Kraj igrice", this);
	}

	/**
	 * Javna metoda koja postavlja igricu na prvi level i prvu težinu
	 */
	public void novaIgrica() {
		level = 1;
		tezina = 1;
		poljaZaIgricu = new PoljaZaIgricu(this);
		piratskiBrodovi = poljaZaIgricu.dajPiratskeBrodove();
		putanjePiratskihBrodova = new ArrayList<ArrayList<Point>>(piratskiBrodovi.length);
		for (int i = 0; i < piratskiBrodovi.length; i++) {
			putanjePiratskihBrodova.add(i,
					poljaZaIgricu.putanjaPiratskiBrodDoIgrac(piratskiBrodovi[i].dajRedniBrojPiratskogBroda()));
		}
	}

}
