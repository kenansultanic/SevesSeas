package logika;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import konzola.Igrica;

/**
 * 
 * @author Kenan
 *
 */
public class PoljaZaIgricu {

	private final int brojElemenata = Igrica.KOLONE * Igrica.REDOVI;
	private Igrica igrica;
	private Polje[][] matricaPolja;
	private ArrayList<Integer> zauzetaPolja;
	private int tezina;
	private int brojOtoka;
	private int brojBrodova;
	private int brojacVrtloga = 0;
	private int brojacPiratskihBrodova = 0;
	private List<Integer> nizPolja;

	/**
	 * Konstruktor
	 * 
	 * @param igrica
	 */
	public PoljaZaIgricu(Igrica igrica) {
		this.igrica = igrica;
		this.tezina = igrica.dajTezinu();
		if (tezina == 1) {
			brojOtoka = 3;
			brojBrodova = 3;
		} else if (tezina == 2) {
			brojOtoka = 4;
			brojBrodova = 4;
		} else {
			brojOtoka = 5;
			brojBrodova = 6;
		}

		generisiNiz();
		generisiMatricuPolja();

	}

	/**
	 * Metoda za dohvaćanje matrice polja.
	 * 
	 * @return
	 */
	public Polje[][] dajMatricuPolja() {
		return matricaPolja;
	}

	/**
	 * Javna metoda koja daje koordinate igračevog broda unutar matrice polja.
	 * 
	 * @return
	 */
	public Point dajLokacijuIgraca() {
		int x = 0, y = 0;
		for (int i = 1; i < Igrica.REDOVI - 1; i++) {
			for (int j = 1; j < Igrica.KOLONE - 1; j++) {
				if (matricaPolja[i][j].dajTipPolja() == TipoviPolja.POLJE_BROD) {
					x = i;
					y = j;
					break;
				}
			}
		}
		return new Point(x, y);
	}

	/**
	 * Javna metoda koja pronalazi polje na kojem se nalazi igrač i vrati ga.
	 * 
	 * @return
	 */
	public Polje dajIgraca() {
		for (int i = 0; i < matricaPolja.length; i++) {
			for (int j = 0; j < matricaPolja[0].length; j++) {
				if (matricaPolja[i][j].dajTipPolja() == TipoviPolja.POLJE_BROD)
					return matricaPolja[i][j];
			}
		}
		return null;
	}

	/**
	 * Javna metoda koja pronalazi i vraća polje na osnovu njegove x i y koordinate
	 * u matrici polja.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public Polje dajPolje(int x, int y) {
		for (int i = 0; i < matricaPolja.length; i++) {
			for (int j = 0; j < matricaPolja[0].length; j++) {
				if (i == x && j == y)
					return matricaPolja[i][j];
			}
		}
		return null;
	}

	/**
	 * Javna metoda koja pronalazi i vraća polje iratskog broda na osnovu broja
	 * piratskog broja (svaki ima jedinstven broj u toku igrice).
	 * 
	 * @param brojPiratskogBroda
	 * @return
	 */
	private Polje dajPiratskiBrod(int brojPiratskogBroda) {
		for (int i = 0; i < matricaPolja.length; i++) {
			for (int j = 0; j < matricaPolja[0].length; j++) {
				if (matricaPolja[i][j].dajRedniBrojPiratskogBroda() == brojPiratskogBroda)
					return matricaPolja[i][j];
			}
		}
		return null;
	}

	/**
	 * Metoda koja računa najkraću udaljenost između piratskog i igračevog broda i
	 * vraća je.
	 * 
	 * @param brojPiratskogBroda
	 * @return
	 */
	public ArrayList<Point> putanjaPiratskiBrodDoIgrac(int brojPiratskogBroda) {
		Polje pirat = dajPiratskiBrod(brojPiratskogBroda);
		Polje igrac = dajIgraca();
		int[] start = new int[] { pirat.dajX(), pirat.dajY() };
		int[] cilj = new int[] { igrac.dajX(), igrac.dajY() };
		return Udaljenost.najkracaUdaljenost(matricaPolja, start, cilj, brojPiratskogBroda);
	}

	/**
	 * Metoda koja vraća sva polja piratskih brodova koja se nalaze u matrici polja.
	 * 
	 * @return
	 */
	public Polje[] dajPiratskeBrodove() {
		Polje[] pirati = new Polje[brojBrodova];
		int brojac = 0;
		for (int i = 0; i < matricaPolja.length; i++) {
			for (int j = 0; j < matricaPolja[0].length; j++) {
				if (matricaPolja[i][j].dajTipPolja() == TipoviPolja.POLJE_PROTIVNICKI_BROD)
					pirati[brojac++] = matricaPolja[i][j];
			}
		}
		return pirati;
	}

	/**
	 * Privatna metoda koja na osnovu niza polja generiše matricu polja, koja
	 * predstavlja reprezentaciju same igrice
	 * 
	 * @return
	 */
	private void generisiMatricuPolja() {

		matricaPolja = new Polje[Igrica.REDOVI][Igrica.KOLONE];

		for (int i = 0; i < Igrica.REDOVI; i++) {
			for (int j = 0; j < Igrica.KOLONE; j++) {
				Polje p = dajOdgovarajucePolje(nizPolja.get(i * Igrica.KOLONE + j), i, j);
				matricaPolja[i][j] = new Polje(p);
			}
		}
	}

	/**
	 * Pomoćna metoda za inicijalizaciju niza polja i umetanje slobodnih i pozicija
	 * vrtloga, kao i poziciju igračevog broda. Ista poziva dodatne 2 metode koje
	 * umeću pozicije za otoke i neprijateljske brodove.
	 */
	private void generisiNiz() {

		zauzetaPolja = new ArrayList<Integer>();
		nizPolja = new ArrayList<Integer>(brojElemenata);
		int brojac = -1;
		for (int i = 0; i < Igrica.REDOVI; i++) {
			for (int j = 0; j < Igrica.KOLONE; j++) {
				brojac++;
				if (i == 0 || i == Igrica.REDOVI - 1 || j == 0 || j == Igrica.KOLONE - 1) {
					nizPolja.add(TipoviPolja.GRANICA);
					zauzetaPolja.add(brojac);
				} else
					nizPolja.add(TipoviPolja.PRAZNO_POLJE);
			}
		}

		nizPolja.set(Igrica.KOLONE + 1, TipoviPolja.POLJE_VRTLOG);
		nizPolja.set(2 * Igrica.KOLONE - 2, TipoviPolja.POLJE_VRTLOG);
		nizPolja.set((Igrica.KOLONE - 1) * (Igrica.REDOVI - 1), TipoviPolja.POLJE_VRTLOG);
		nizPolja.set(brojElemenata - Igrica.KOLONE - 2, TipoviPolja.POLJE_VRTLOG);
		nizPolja.set(brojElemenata / 2, TipoviPolja.POLJE_BROD);

		zauzetaPolja.add(Igrica.KOLONE + 1);
		zauzetaPolja.add(2 * Igrica.KOLONE - 2);
		zauzetaPolja.add((Igrica.KOLONE - 1) * (Igrica.REDOVI - 1));
		zauzetaPolja.add(brojElemenata - Igrica.KOLONE - 2);
		zauzetaPolja.add(brojElemenata / 2);

		generisiPozicijeOtoka();
		generisiPozicijeBrodova();
	}

	/**
	 * Pomoćna metoda koja nam u niz polja dodaje pozicije za otoke na nasumičan
	 * način. Funkcija uzima u obzir da su pojedina polja već zauzeta.
	 */
	private void generisiPozicijeOtoka() {
		int brojacOtoka = 0;
		Random rand = new Random();
		while (brojacOtoka < brojOtoka) {
			int indeks = rand.nextInt(brojElemenata - 2) + 1;
			if (!zauzetaPolja.contains(indeks)) {
				nizPolja.set(indeks, TipoviPolja.POLJE_OTOK);
				zauzetaPolja.add(indeks);
				brojacOtoka++;
			}
		}
	}

	/**
	 * Pomoćna metoda koja nam u niz polja dodaje pozicije za protivničke brodove na
	 * nasumičan način. Funkcija uzima u obzir da su pojedina polja već zauzeta.
	 */
	private void generisiPozicijeBrodova() {
		int brojacBrodova = 0;
		Random rand = new Random();
		while (brojacBrodova < brojBrodova) {
			int indeks = rand.nextInt(brojElemenata - 2) + 1;
			if (!zauzetaPolja.contains(indeks)) {
				nizPolja.set(indeks, TipoviPolja.POLJE_PROTIVNICKI_BROD);
				zauzetaPolja.add(indeks);
				brojacBrodova++;
			}
		}
	}

	/**
	 * Metoda koja nam instancira i vrati objekat klase Polje.
	 * 
	 * @param tipPolja
	 * @param i
	 * @param j
	 * @return
	 */
	private Polje dajOdgovarajucePolje(int tipPolja, int i, int j) {
		Polje polje;
		switch (tipPolja) {
		case TipoviPolja.POLJE_OTOK:
			polje = new Polje(TipoviPolja.POLJE_OTOK, false, i, j);
			break;
		case TipoviPolja.POLJE_PROTIVNICKI_BROD:
			polje = new Polje(TipoviPolja.POLJE_PROTIVNICKI_BROD, false, i, j, 0, ++brojacPiratskihBrodova);
			break;
		case TipoviPolja.POLJE_BROD:
			polje = new Polje(TipoviPolja.POLJE_BROD, false, i, j);
			break;
		case TipoviPolja.POLJE_VRTLOG:
			polje = new Polje(TipoviPolja.POLJE_VRTLOG, true, i, j, ++brojacVrtloga, 0);
			break;
		case TipoviPolja.GRANICA:
			polje = new Polje(TipoviPolja.GRANICA, false, i, j);
			break;
		default:
			polje = new Polje(TipoviPolja.PRAZNO_POLJE, true, i, j);
		}
		return polje;
	}

	/**
	 * Pomoćna metoda koja prima x i y koordinatu kliknutog polja. Ista provjerava
	 * da li je igrač upao u vrtlog i izbaci ga na nasumičnu lokaciju ako jeste, a
	 * ako nije mijenja njegovu lokaciju sa poljem na koje je kliknuo, ukoliko se
	 * radi o praznpm slobodnom polju.
	 * 
	 * @param x
	 * @param y
	 */
	public void primiKlik(int x, int y) {
		Polje igrac = dajIgraca();

		if (Math.abs(igrac.dajX() - ++x) < 2 && Math.abs(igrac.dajY() - ++y) < 2) {
			Polje destinacija = dajPolje(x, y);

			if (destinacija.dajTipPolja() == TipoviPolja.POLJE_VRTLOG) {
				ArrayList<Integer> vrtlozi = new ArrayList<Integer>();
				for (int i = 1; i <= 4; i++) {
					if (i != destinacija.dajRedniBrojVrtloga())
						vrtlozi.add(i);
				}
				Collections.shuffle(vrtlozi);
				boolean poljeNadjeno = false;
				do {
					Polje vrtlog = dajTrazeniVrtlog(vrtlozi.get(0));
					Polje slobodnoPolje = nadjiSlobodnoPoljeDoVrtloga(vrtlog);
					if (slobodnoPolje != null) {
						zamjeniLokacijePoljima(igrac, slobodnoPolje);
						poljeNadjeno = true;
					} else
						vrtlozi.remove(0);
				} while (!poljeNadjeno);

			}

			else if (destinacija.jeLiSlobodnoPolje()) {
				zamjeniLokacijePoljima(igrac, destinacija);
			}
		}
	}

	/**
	 * Metoda koja pronalazi i vraća vtrlog na osnovu njegovog broja (1, 2, 3 ili
	 * 4).
	 * 
	 * @param brojVrtloga
	 * @return
	 */
	private Polje dajTrazeniVrtlog(int brojVrtloga) {
		for (int i = 0; i < matricaPolja.length; i++) {
			for (int j = 0; j < matricaPolja[0].length; j++) {
				if (matricaPolja[i][j].dajRedniBrojVrtloga() == brojVrtloga)
					return matricaPolja[i][j];
			}
		}
		return null;
	}

	/**
	 * Pomoćna metoda koja nalazi slobodno polje pored vrtloga u koje igrač može
	 * ući.
	 * 
	 * @param vrtlog
	 * @return
	 */
	private Polje nadjiSlobodnoPoljeDoVrtloga(Polje vrtlog) {
		for (int i = 0; i < vrtlog.dajSusjednaPolja().size(); i++) {
			Point susjedKoordinate = vrtlog.dajSusjednaPolja().get(i);
			Polje susjed = dajPolje((int) susjedKoordinate.getX(), (int) susjedKoordinate.getY());
			if (susjed.jeLiSlobodnoPolje()) {
				return new Polje(susjed);
			}
		}
		return null;
	}

	/**
	 * Pomoćna metoda koja mijenja pozicije poljima koji su joj proslijeđeni kao
	 * parametri.
	 * 
	 * @param polje1
	 * @param polje2
	 */
	private void zamjeniLokacijePoljima(Polje polje1, Polje polje2) {

		int tempX = polje1.dajX(), tempY = polje1.dajY();
		polje1.postaviX(polje2.dajX());
		polje1.postaviY(polje2.dajY());
		polje2.postaviX(tempX);
		polje2.postaviY(tempY);

		ArrayList<Point> susjedi1 = polje1.dajSusjednaPolja();
		ArrayList<Point> susjedi2 = polje2.dajSusjednaPolja();

		Polje novo1 = new Polje(polje2);
		Polje novo2 = new Polje(polje1);
		matricaPolja[polje2.dajX()][polje2.dajY()] = novo1;
		matricaPolja[polje1.dajX()][polje1.dajY()] = novo2;
		matricaPolja[polje2.dajX()][polje2.dajY()].postaviSusjednaPolja(susjedi1);
		matricaPolja[polje1.dajX()][polje1.dajY()].postaviSusjednaPolja(susjedi2);

		proslijediIzmjeneGamePanelu();
	}

	/**
	 * Javna metoda koja piratski brod pomjeri za jedno polje bliže igračevom brodu.
	 * 
	 * @param putanje
	 * @param piratskiBrodovi
	 */
	public void pomjeriPiratskeBrodove(ArrayList<ArrayList<Point>> putanje, Polje[] piratskiBrodovi) {
		for (int i = 0; i < piratskiBrodovi.length; i++) {
			Point lokacija = putanje.get(i).get(0);
			Point destinacija = putanje.get(i).get(1);
			Polje pirat = dajPolje((int) lokacija.getX(), (int) lokacija.getY());
			Polje polje = dajPolje((int) destinacija.getX(), (int) destinacija.getY());
			if (polje.dajTipPolja() == TipoviPolja.POLJE_BROD) {
				polje.postaviPolje(TipoviPolja.PRAZNO_POLJE);
				krajLevela(false);
			}
			zamjeniLokacijePoljima(pirat, polje);
		}
	}

	
	private void proslijediIzmjeneGamePanelu() {
		igrica.proslijediIzmjeneMatricePolja(matricaPolja);
	}

	private void krajLevela(boolean igracPobjedio) {
		igrica.krajLevela(igracPobjedio);
	}

}
