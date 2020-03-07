import java.io.File;
import java.io.IOException;
import java.util.Scanner;
/**In  main am citit datele din fisier si in functie de ele,
 * am apelat metodele folosite in clasa House
 */
public class main {
	public static void main(String[] args) throws IOException  {
		File in = new File("therm.in");
		if(in.exists() == false) {
			System.out.println("fisierul nu exista");
			return;
		}
		Scanner sc = new Scanner(in);
		String n_room;
		String n_dev;
		int surf;
		/*citesc datele din fisier, N este numarul de camere,
		 * t este temperatura globala si t_ref timpul de referinta a 
		 * intregii casei
		 */
		int N = sc.nextInt();
		double t = sc.nextDouble();
		int t_ref = sc.nextInt();
		House h = new House();
		/*Pentru fiecare camera, citesc numele fisierului,
		 * numele device-ului si suprafata camerei si dupa adaug fiecare
		 * camera in vectorul de camere(adica in casa)
		 */
		for (int i = 0; i < N; i++) {
			n_room = sc.next();
			n_dev = sc.next();
			surf = sc.nextInt();
			h.add_room(n_room, n_dev, surf);
		}
	
		while(sc.hasNext() == true) {
			int ref;
			double temp;
			String comanda = sc.next();
			//Daca am comanda observe
			if(comanda.equals("OBSERVE")) {
				comanda = sc.next();
				//ref este timpul de referinta a fiecarui observe
				ref = Integer.parseInt(sc.next());
				temp = Double.parseDouble(sc.next());
				h.observe(comanda, t_ref, ref, temp);	
			}
			//daca comanda este trigger heat
			if(comanda.equals("TRIGGER")) {
				h.trigger(t);
			}
			//daca comanda este temperature
			if(comanda.equals("TEMPERATURE")) {
				comanda = sc.next();
				double temp2_g = Double.parseDouble(comanda);
				t = temp2_g;
			}
			//daca comanda este list
			if(comanda.equals("LIST")) {
				comanda = sc.next();
				int t_start = Integer.parseInt(sc.next());
				int t_stop = Integer.parseInt(sc.next());
				h.list(comanda, t_ref, t_start, t_stop);
				
			}
		}
		h.wr.close();
		sc.close();
	}
}
