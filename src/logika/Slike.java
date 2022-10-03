package logika;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;

public class Slike {
	public static final String PIRATSKI_BROD = "images\\pirate.png";
	public static final String BROD_IGRACA = "images\\ship.png";
	public static final String OTOK = "images\\island-3.png";
	public static final String VRTLOG = "images\\whirlpool.png";
	public static final String STRIJELA_E = "images\\arrow-E.png";
	public static final String STRIJELA_N = "images\\arrow-N.png";
	public static final String STRIJELA_S = "images\\arrow-S.png";
	public static final String STRIJELA_W = "images\\arrow-W.png";
	public static final String STRIJELA_NE = "images\\arrow-NE.png";
	public static final String STRIJELA_NW = "images\\arrow-NW.png";
	public static final String STRIJELA_SE = "images\\arrow-SE.png";
	public static final String STRIJELA_SW = "images\\arrow-SW.png";

	/**
	 * Javna statična metoda za vraćanje slike koja će se iscrtavati na panelu
	 * igrice. Tip slike se određuje na osnovu proslijeđenog parametra.
	 * 
	 * @param tip
	 * @return
	 */
	public static Image dajSliku(int tip) {
		Image slika = null;
		String lokacija = null;

		switch (tip) {
		case TipoviPolja.POLJE_BROD:
			lokacija = BROD_IGRACA;
			break;
		case TipoviPolja.POLJE_PROTIVNICKI_BROD:
			lokacija = PIRATSKI_BROD;
			break;
		case TipoviPolja.POLJE_VRTLOG:
			lokacija = VRTLOG;
			break;
		case TipoviPolja.POLJE_OTOK:
			lokacija = OTOK;
			break;
		}

		try {
			slika = ImageIO.read(new File(lokacija));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return slika;
	}

	/**
	 * Javna statična metoda za vraćanje slike strijelice koja se prikazuje na samoj
	 * igrici, rezultujuća slika se određuje na osnovu proslijeđene geografske
	 * orijentacije (S, W, N, E, SE, SW, NW, NE).
	 * 
	 * @param orijentacija
	 * @return
	 */
	public static Image dajStrijelu(String orijentacija) {
		Image slika = null;
		String lokacija = null;

		switch (orijentacija) {
		case "E":
			lokacija = STRIJELA_E;
			break;
		case "N":
			lokacija = STRIJELA_N;
			break;
		case "S":
			lokacija = STRIJELA_S;
			break;
		case "W":
			lokacija = STRIJELA_W;
			break;
		case "SW":
			lokacija = STRIJELA_SW;
			break;
		case "NW":
			lokacija = STRIJELA_NW;
			break;
		case "SE":
			lokacija = STRIJELA_SE;
			break;
		default:
			lokacija = STRIJELA_NE;
			break;
		}

		try {
			slika = ImageIO.read(new File(lokacija));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return slika;
	}
}
