package konzola;

import java.awt.Point;
import java.util.ArrayList;

import gui.GlavniProzor;
import gui.Kraj;
import logika.PoljaZaIgricu;
import logika.Polje;

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

	public Igrica() {

		novaIgrica();
		glavniProzor = new GlavniProzor(this);
	}

	public int dajLevel() {
		return level;
	}

	public int dajTezinu() {
		return tezina;
	}

	public PoljaZaIgricu dajPoljaZaIgricu() {
		return poljaZaIgricu;
	}

	public void proslijediKlik(int x, int y) {
		poljaZaIgricu.primiKlik(x, y);
	}

	public void proslijediIzmjeneMatricePolja(Polje[][] matricaPolja) {
		glavniProzor.proslijediIzmjeneMatricePolja(matricaPolja);
	}

	public boolean jeLiIgracNaPotezu() {
		igracKliknuoNaPodlogu();
		return igracNaPotezu % 2 == 0;
	}

	public void krajLevela(boolean igracPobjedio) {
		if (!igracPobjedio) {
			kraj();
		}
	}

	private void igracKliknuoNaPodlogu() {
		igracNaPotezu++;
		if (!(igracNaPotezu % 2 == 0))
			odigrajPotez();
	}

	private void odigrajPotez() {
		for (int i = 0; i < piratskiBrodovi.length; i++) {
			putanjePiratskihBrodova.set(i,
					poljaZaIgricu.putanjaPiratskiBrodDoIgrac(piratskiBrodovi[i].dajRedniBrojPiratskogBroda()));
		}
		pomjeriPiratskeBrodove();
	}

	private void pomjeriPiratskeBrodove() {
		poljaZaIgricu.pomjeriPiratskeBrodove(putanjePiratskihBrodova, piratskiBrodovi);
		postaviIgracNaPotezu();
	}

	private void postaviIgracNaPotezu() {
		glavniProzor.postaviIgracNaPotezu();
	}

	private void kraj() {
		new Kraj("Kraj igrice", this);
	}

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
