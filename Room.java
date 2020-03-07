import java.util.ArrayList;
/**
 *Clasa Room contine datele despre fiecare camera: numele, numele 
 *device-ului, suprafata si poz_max reprezinte ultima ora la care
 *a fost adaugata temperatura.
 *Clasa Room contine si un vector de intervale(intervale de ore intr-o
 *zi, care la randul lui e format
 *din vector de temperaturi
 */
public class Room {
	public String name;
	public String device;
	public int supr;
	public int poz_max;
	ArrayList<Temperatura> obs= new ArrayList<Temperatura>(24);	

	Room (){}
	/**
	 * Constructorul initializeaza urmatorii parametri:
	 * @param name numele camerei 
	 * @param device numele device-ului
	 * @param supr suprafata camerei
	 * si populeaza vectorul de intervale cu temperaturi
	 */
	Room(String name, String device, int supr){
		this.name = name;
		this.device = device;
		this.supr = supr;
		this.poz_max = 0;
		for(int i = 0; i < 24; i++)
			obs.add(new Temperatura());
		
	}
	/**
	 * Functia returneaza numele camerei
	 * @return numele camerei
	 */
	String get_name() {
		return name;
	}
	/**
	 * Functia returneaza numele device-ului
	 * @return numele device-ului
	 */
	String get_dev() {
		return device;
	}

}
