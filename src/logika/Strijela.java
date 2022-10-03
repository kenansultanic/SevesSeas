package logika;

/**
 * 
 * @author Kenan
 *
 */
public class Strijela {
	String orijentacija;
	double x;
	double y;

	/**
	 * Konstruktor
	 * 
	 * @param orijentacija
	 * @param x
	 * @param y
	 */
	public Strijela(String orijentacija, int x, int y) {
		this.orijentacija = orijentacija;
		this.x = x;
		this.y = y;
	}

	/**
	 * Konstruktor
	 * 
	 * @param orijentacija
	 * @param x
	 * @param y
	 */
	public Strijela(String orijentacija, double x, double y) {
		this.orijentacija = orijentacija;
		this.x = x;
		this.y = y;
	}

	/**
	 * Konstruktor
	 */
	public Strijela() {
		this.orijentacija = null;
		this.x = 0;
		this.y = 0;
	}

	/**
	 * Get metoda za orijentaciju strijele
	 * 
	 * @return
	 */
	public String getOrijentacija() {
		return orijentacija;
	}

	/**
	 * Get metoda za X koordinatu strijele
	 * 
	 * @return
	 */
	public double getX() {
		return x;
	}

	/**
	 * Get metoda za Y koordinatu strijele
	 * 
	 * @return
	 */
	public double getY() {
		return y;
	}
}
