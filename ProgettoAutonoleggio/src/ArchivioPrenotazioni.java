import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

//classe che gestisce le prenotazioni
public class ArchivioPrenotazioni {
	
	//variabile che tiene traccia delle modifiche
	private boolean modificato;
	//nome del file che verrà creato che contiene le prenotazioni
	private String nomefile;
	//vettore che contiene le prenotazioni
	private Vector<Prenotazione> listaPrenotazioni;
	
	//construttore
	public ArchivioPrenotazioni(String nomefile) {
		this.nomefile = nomefile;
		this.modificato = false;
		try {
			ObjectInputStream file_input = new ObjectInputStream(new BufferedInputStream(new FileInputStream(nomefile)));
			// legge l'intero vettore da file
			listaPrenotazioni = (Vector<Prenotazione>) file_input.readObject();
			file_input.close();
		} catch (FileNotFoundException e) {
			System.out.println();
			this.listaPrenotazioni = new Vector<Prenotazione>();
		} catch (ClassNotFoundException e) {
			// gestisce il caso in cui il file non contenga un oggetto
			System.out.println("ERRORE di lettura");
			System.out.println(e);
			this.listaPrenotazioni = new Vector<Prenotazione>();
		} catch (IOException e) {
			// gestisce altri errori di input/output
			System.out.println("ERRORE di I/O");
			System.out.println(e);
			this.listaPrenotazioni = new Vector<Prenotazione>();
		}
	}
	
	//serve per verificare se il mezzo è disponibile nella data richiesta prima di continuare con la prenotazione
	public boolean checkPrenotazione(Mezzo m, String data) {
		Prenotazione p = new Prenotazione(m, data, ""); //stringa vuota perchè l'equals, definito in Prenotazione, controlla solo data e targa
		if(!listaPrenotazioni.contains(p)) 
			return true;	//se la prenotazione non è contenuta nel vettore delle prenotazioni ritorna true
		else return false;
		
	}
	//metodo che permette di aggiungere una prenotazione, dopo aver fatto il check se quella prenotazione non sia già stata inserita
	public boolean aggiungiPrenotazione(Prenotazione p) {
		if(checkPrenotazione(p.getMezzo(), p.getDataPrenotazione())) {
			listaPrenotazioni.add(p);
			modificato = true; //booleano che tiene traccia se c'è necessità di salvare il file
			return true;
		}
		return false;
	}
	
	
	//metodo per la gestione dei mezzi (di una certa categoria) disponibili in una certa data
	//vm è il vettore di mezzi passato dall'archivio, attraverso il main
	public Vector<Mezzo> getCategoriaData(String data, String categoria, Vector<Mezzo> vm) { 
		Vector<Mezzo> lista = new Vector<Mezzo>(); //vettore di mezzi 
		for(int i=0; i<vm.size(); i++){
			boolean reserved=false;
			for(int j=0; j<listaPrenotazioni.size();j++){
				
				if(listaPrenotazioni.elementAt(j).getDataPrenotazione().equals(data) && listaPrenotazioni.elementAt(j).getMezzo().getTarga().equalsIgnoreCase(vm.elementAt(i).getTarga())){
					reserved = true;
				}
			}
		//check sulla categoria passata dall'utente
		if(categoria.equals("Autovettura")){
			//se l'elemento non è riservato ed è un'istanza di Autovettura allora viene aggiunto al vettore di mezzi
			if(!reserved && vm.elementAt(i) instanceof Autovettura){
				lista.addElement(vm.elementAt(i));
			}
			//se l'elemento non è riservato ed è un'istanza di Furgone allora viene aggiunto al vettore di mezzi
		} else if(categoria.equals("Furgone")){
			if(!reserved && vm.elementAt(i) instanceof Furgone){
				lista.addElement(vm.elementAt(i));
			}
		}
		}
		
		if(lista.isEmpty())
			return null;
		else
			return lista;
	}	
	
	//rimozione della prenotazione dalla lista delle prenotazioni
	public boolean rimuoviPrenotazione(Mezzo m, String data) {
		Prenotazione p = new Prenotazione(m, data, "");
		if(listaPrenotazioni.isEmpty()) return false;
		else {
			boolean rimosso = false;
			for(int i=0; i<listaPrenotazioni.size(); i++) {
				if(listaPrenotazioni.elementAt(i).equals(p)) {
					listaPrenotazioni.removeElementAt(i);
					rimosso = true;
					modificato = true;
				}
				
			}
			
			if(rimosso) return true;
			
		}
		return false;
		
	}
	
	//visualizzazione della prenotazione 
	public Prenotazione visualizzaPrenotazioneArchivio(Prenotazione p) {
		if(listaPrenotazioni.isEmpty()) return null;
		else {
			
			for(int i=0; i<listaPrenotazioni.size(); i++) {
				if(listaPrenotazioni.elementAt(i).equals(p))
					return p;
			}
		}
		return null;
		
	}
	
	//metodo che gestisce la visualizzazione di tutte le prenotazioni di un singolo mezzo
	public Vector<Prenotazione> cronologiaPrenotazioniMezzo(String t){
		//vettore di prenotazioni 
		Vector<Prenotazione> vp = new Vector<Prenotazione>(); 
		if(!listaPrenotazioni.isEmpty()) {
			for(int i=0; i<listaPrenotazioni.size(); i++) {
				//se la targa passata come paramentro corrisponde a una di un mezzo, presente come elemento prenotazione all'interno del vettore della classe
				//l'elemento viene aggiunto alla nuova lista di prenotazioni
				if(listaPrenotazioni.elementAt(i).getMezzo().getTarga().equalsIgnoreCase(t)) 
					vp.addElement(listaPrenotazioni.elementAt(i));
			}
		}
		
		return vp;
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
				file_output.writeObject(listaPrenotazioni);
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

		
	
}
