package logika;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;


/**
 * 
 * @author Kenan
 *
 */
public class Udaljenost {

	private static class Celija {
		int x;
		int y;
		int udaljenost;
		Celija predhodni;

		Celija(int x, int y, int udaljenost, Celija predhodni) {
			this.x = x;
			this.y = y;
			this.udaljenost = udaljenost;
			this.predhodni = predhodni;
		}

		public Point parsiraj() {
			return new Point(x, y);
		}
	}

	/**
	 * Statična metoda koja za proslijeđenu matricu polja, piratski brod i
	 * koordinate igrača računa najkraću udaljenost između pirata i igrača. Metoda
	 * implementira BFS algoritam i radi u vremenu O(n^2).
	 * 
	 * @param matricaPolja
	 * @param start
	 * @param end
	 * @param piratRB
	 * @return
	 */
	public static ArrayList<Point> najkracaUdaljenost(Polje[][] matricaPolja, int[] start, int[] end, int piratRB) {
		int sx = start[0], sy = start[1];
		int dx = end[0], dy = end[1];

		int m = matricaPolja.length;
		int n = matricaPolja[0].length;
		Celija[][] cells = new Celija[m][n];
		for (int i = 1; i < m; i++) {
			for (int j = 1; j < n - 1; j++) {
				Polje polje = matricaPolja[i][j];
				if ((polje.jeLiSlobodnoPolje() || polje.dajTipPolja() == TipoviPolja.POLJE_BROD
						|| polje.dajRedniBrojPiratskogBroda() == piratRB)
						&& polje.dajTipPolja() != TipoviPolja.POLJE_VRTLOG) {
					cells[i][j] = new Celija(i, j, Integer.MAX_VALUE, null);
				}
			}
		}

		LinkedList<Celija> queue = new LinkedList<>();
		Celija src = cells[sx][sy];
		src.udaljenost = 0;
		queue.add(src);
		Celija destinacija = null;
		Celija p;
		while ((p = queue.poll()) != null) {
			if (p.x == dx && p.y == dy) {
				destinacija = p;
				break;
			}

			// gore
			posjeti(cells, queue, p.x - 1, p.y, p);
			// dole
			posjeti(cells, queue, p.x + 1, p.y, p);
			// lijevo
			posjeti(cells, queue, p.x, p.y - 1, p);
			// desno
			posjeti(cells, queue, p.x, p.y + 1, p);
		}

		if (destinacija == null) {
			System.out.println("there is no path.");
			return new ArrayList<Point>();
		} else {
			LinkedList<Celija> putanja = new LinkedList<>();
			p = destinacija;
			do {
				putanja.addFirst(p);
			} while ((p = p.predhodni) != null);
			LinkedList<Point> krajnjaPutanja = new LinkedList<Point>();
			putanja.forEach(kvadrat -> {
				krajnjaPutanja.add(kvadrat.parsiraj());
			});
			return new ArrayList<Point>(krajnjaPutanja);
		}
	}

	/**
	 * Pomoćna statična metoda koja ažurira status ćelije i označava je posjećenom.
	 * 
	 * @param celije
	 * @param queue
	 * @param x
	 * @param y
	 * @param roditelj
	 */
	private static void posjeti(Celija[][] celije, LinkedList<Celija> queue, int x, int y, Celija roditelj) {
		if (x < 0 || x >= celije.length || y < 0 || y >= celije[0].length || celije[x][y] == null) {
			return;
		}
		int udaljenost = roditelj.udaljenost + 1;
		Celija p = celije[x][y];
		if (udaljenost < p.udaljenost) {
			p.udaljenost = udaljenost;
			p.predhodni = roditelj;
			queue.add(p);
		}
	}

}