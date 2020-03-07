import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
/**
 *In clasa House implementez metodele Observe, Trigger heat, Temperature
 *si list
 */
public class House {
	// c este vectorul de camere
	ArrayList<Room> c;
    FileWriter wr;
    int count;
    /**
     * Constructorul creeaza o instanta a vectorului 
     */
	House() throws IOException{
		c = new ArrayList<Room>();
		//Fisierul de iesire
		this.wr = new FileWriter("therm.out");
	}
	/**
	 * Metoda adauga in vectorul de camera fiecare camera, creand
	 * o instanta a clasei Room
	 * @param name numele camerei
	 * @param device numele device-ului
	 * @param surf suprafata camerei
	 */
	void add_room(String name, String device, int surf) {
		c.add(new Room(name, device, surf));
	}
	/**
	 * Metoda calculeaza de la inceput pozitia unde trebuie inserata
	 * temperatura, in functie de timestamp, dupa verific daca a mai 
	 * fost adaugata aceeasi temperatura.
	 * Dupa ce am adaugat temperaturile, sortez vectorul
	 * @param dev numele device-ului
	 * @param t_ref timpul de referinta a intregii case
	 * @param t timestamp pentru fiecare observe
	 * @param temp1 temperatura care trebuie inserata
	 */
	void observe(String dev, int t_ref, int t, double temp1 ) {
		int poz = 0;
		for (int i = 0; i < c.size(); i ++) {
			if(c.get(i).get_dev().equals(dev)) {
				poz = (t_ref -t) / 3600;
				poz = 23 - poz;
				count = 0;
				//daca am mai gasit aceeasi temperatura
				for(int j = 0; j < c.get(i).obs.get(poz).temp.size(); j++) {
					if (c.get(i).obs.get(poz).temp.get(j).doubleValue() == temp1 )
						count++;	
				}				
				if (count != 0)
					continue;
				/*intervalul trebuie sa fie [0,24] si
				 * timestamp-ul observ-ului nu trebuie sa depaseasca
				 * temp de referinta a casei
				 */
				if( (poz >= 0 && poz < 24) && t >= t_ref)
					continue;
				//adaug temperatura
				c.get(i).obs.get(poz).temp.add(temp1);
				//caut ultima ora la care am adaugat temperatura
				if (poz > c.get(i).poz_max)
					c.get(i).poz_max = poz;
				//sortez vectorul de temperaturi
				Collections.sort(c.get(i).obs.get(poz).temp);
				break;
			}
		}	
	}
	/**
	 * Metoda verifica daca centrala termica trebuie pornita sau nu
	 * 
	 * @param t_glob temperatura globala
	 */
	void trigger( double t_glob) throws IOException {
		double suma = 0, suma_supr = 0;
		for(int i = 0; i < c.size(); i ++) {
			/*daca nu am adaugat nici o temperatura in camera,
			 * trec peste ea
			 */
			if(c.get(i).obs.get(c.get(i).poz_max).temp.isEmpty())
				continue;
			suma += c.get(i).obs.get(c.get(i).poz_max).temp.get(0) * c.get(i).supr;
			suma_supr += c.get(i).supr;	
		}
		//media ponderata
		suma = suma / suma_supr;
		/*suma reprezinta temperatura medie
		 * daca e mai mica ca temperatura medie atunci se pornesye centrala
		 */
		if ( suma < t_glob) {
			this.wr.write("YES");
			this.wr.write("\n");
		}
		else {
			this.wr.write("NO");
			this.wr.write("\n");}
	}
	/**
	 * Metoda afiseaza valorile temperaturilor intre cele doua intervale
	 * @param cam numele camerei
	 * @param t_ref timpul de referinta
	 * @param start intervalul de start
	 * @param stop intervalul de stop
	 */
	void list(String cam, int t_ref, int start, int stop) throws IOException {
		int p_start = 0, p_stop = 0;
		int poz = 0;
		//parcurg vectorul de camere
		for(int i = 0; i < c.size(); i++) {
			if(c.get(i).get_name().equals(cam)) {
				//calculez pozitia(intervalele)
				p_start = (t_ref -start) / 3600;
				p_start = 23 - p_start;
				p_stop = (t_ref -stop) / 3600;
				p_stop = 23 - p_stop;
				poz = i;
				this.wr.write(c.get(poz).get_name()+ " ");
				break;
			}
		}
		for(int j = p_stop; j >= p_start + 1; j--) {
			//parcurg vectorul si afisez temperaturile
			for(int k = 0; k < c.get(poz).obs.get(j).temp.size(); k++) {
				this.wr.write(String.format("%.2f",c.get(poz).obs.get(j).temp.get(k))  + " ");
			}
		}
		this.wr.write("\n");
	}
}
