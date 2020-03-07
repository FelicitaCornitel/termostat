import java.util.ArrayList;
/**
 *Clasa Temperatura contine un vector de temperaturi, temperaturile
 *au dimensiune double
 */
public class Temperatura {
	double temperatura; 
	ArrayList<Double> temp = new ArrayList<Double>();

	public Temperatura() {}
	/**
	 * Functia returneaza valoarea temperaturii
	 * @return  valoarea temperaturii
	 */
	double get_temp() {
		return temperatura;
	}
}
