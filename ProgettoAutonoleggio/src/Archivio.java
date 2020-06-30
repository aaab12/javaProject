import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

//classe Archivio dei mezzi presenti nell'autonoleggio
public class Archivio {
	
	private boolean modificato;
	private String nomefile;
	//vettore di mezzi che vengono gestiti dall'autoneggio
	private Vector <Mezzo> mezziGestiti;
	
	
	public Archivio(String nomefile) {
		//variabile d'istanza che tiene traccia delle modifiche da salvare
		 this.modificato = false;
		 //definisce il nome del file su cui salvare i mezzi
		 this.nomefile = nomefile;
			try {
				ObjectInputStream file_input = new ObjectInputStream(new BufferedInputStream(new FileInputStream(nomefile)));
				// legge l'intero vettore da file
				mezziGestiti = (Vector<Mezzo>) file_input.readObject();
				file_input.close();
			} catch (FileNotFoundException e) {
				// gestisce il caso in cui il file non sia presente (creato in seguito)
				System.out.println("ATTENZIONE: Il file " + nomefile + " non esiste");
				System.out.println("Sara' creato al primo salvataggio");
				System.out.println();
				this.mezziGestiti = new Vector<Mezzo>();
			} catch (ClassNotFoundException e) {
				// gestisce il caso in cui il file non contenga un oggetto
				System.out.println("ERRORE di lettura");
				System.out.println(e);
				this.mezziGestiti = new Vector<Mezzo>();
			} catch (IOException e) {
				// gestisce altri errori di input/output
				System.out.println("ERRORE di I/O");
				System.out.println(e);
				this.mezziGestiti = new Vector<Mezzo>();
			}	 
		 
	}
	
	//aggiungere un nuovo mezzo all'archivio
	public boolean aggiungiNuovoMezzo(Mezzo m){
		boolean giapresente = false;
		//controllo se il mezzo è già presente nel vettore di mezzi gestiti
		for(int i=0; i<mezziGestiti.size(); i++) {
			if(mezziGestiti.elementAt(i).equals(m)) 
				giapresente = true;
		}
		//se non lo è allora aggiungo il mezzo inizializzato
		if(!giapresente) { 
			mezziGestiti.add(m);
			modificato = true;
			return true;
		} 
		return false;
		
	}
	
	//metodo per la gestione della rimozione di un mezzo
	public boolean rimuoviMezzo(String targa) {
		//tengo traccia della posizione dell'elento da rimuovere all'interno del vettore dei mezzi gestiti
		int n = 0;
		boolean found = false;
		
		//cerco il mezzo nel vettore dei mezzi dell'archivio, attraverso il confronto tra targhe, se trovato memorizzo la posizione
		for(Mezzo m : mezziGestiti) {
			if(m.getTarga().equalsIgnoreCase(targa)) {
				n = mezziGestiti.indexOf(m);
				found = true;
			}
		}
		//se il mezzo viene trovato allora lo elimino dal vettore
		if(found) {
			mezziGestiti.remove(mezziGestiti.elementAt(n));
			modificato = true;
			return true;
				
		} 
		return false;
	}
	
	
	//metodo per trovare un mezzo all'interno del vettore di mezzi gestiti dell'archivio
	public Mezzo selezionaElemento(String t) {
		Mezzo m = null;
		
		if(mezziGestiti.isEmpty()) m = null;
		else {
			for(int i=0; i<mezziGestiti.size(); i++) {
				if(mezziGestiti.elementAt(i).getTarga().equalsIgnoreCase(t))
						m = mezziGestiti.elementAt(i);
			} 
		}
		return m;			
	}
	
	//getter dei mezzi presenti nell'archivio
	public Vector<Mezzo> elencoMezziDisposizione() {
		return mezziGestiti;
	}
	
	//ritorna una lista con tutti i mezzi disponibili di una categoria
	public Vector<Mezzo> selezionaPerCategoria(String c) {
		Vector<Mezzo> lista = new Vector<Mezzo>();
		for(int i=0; i<mezziGestiti.size(); i++) {
			if(c.equals("Autovettura")) {
				//controllo sul tipo del mezzo
				if(mezziGestiti.elementAt(i) instanceof Autovettura) {
					lista.addElement(mezziGestiti.elementAt(i));
				}
			}
			else if(c.equals("Furgone")) {
				if(mezziGestiti.elementAt(i) instanceof Furgone) {
				 lista.addElement(mezziGestiti.elementAt(i));
				}
			}
		}
		if(lista.isEmpty()) {
			return null;
		}
		else return lista;
	}
		
	
	// verifica se ci sono modifiche non salvate
		public boolean daSalvare() {
			return modificato;
		}
		
		// salva il registro nel file
		// restituisce true se il salvataggio è andato a buon fine
		public boolean salva() {
			if (daSalvare()) { // salva solo se necessario (se ci sono modifiche)
				try {
					ObjectOutputStream file_output = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(nomefile)));
					// salva l'intero oggetto (vettore) nel file
					file_output.writeObject(mezziGestiti);
					file_output.close();
					modificato = false; // le modifiche sono state salvate
					return true;
				} catch (IOException e) {
					System.out.println("ERRORE di I/O");
					System.out.println(e);
					return false;
				}		
			} else return true;
		}
	
	
	
	
	public String toString() {
		
		return mezziGestiti.toString();
	}
		
	
	
}
