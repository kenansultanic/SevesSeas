package logika;

public class Strijela {
	String orijentacija;
	double x;
	double y;

	public Strijela(String orijentacija, int x, int y) {
		this.orijentacija = orijentacija;
		this.x = x;
		this.y = y;
	}

	public Strijela(String orijentacija, double x, double y) {
		this.orijentacija = orijentacija;
		this.x = x;
		this.y = y;
	}


	public Strijela() {
		this.orijentacija = null;
		this.x = 0;
		this.y = 0;
	}

	public String getOrijentacija() {
		return orijentacija;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
}
