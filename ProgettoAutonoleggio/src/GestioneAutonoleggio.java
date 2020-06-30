import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
public class GestioneAutonoleggio {
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		//prima viene creato il file di input (per l'archivio dei mezzi)
		System.out.println("Inserisci il nome del file di registro:");
		String nomefile = input.next();
		input.nextLine();
		Archivio a = new Archivio(nomefile);
		//autonomamente si crea il file di input per l'archivio delle prenotazioni
		nomefile = nomefile+"_prenotazioni";
		ArchivioPrenotazioni ap = new ArchivioPrenotazioni(nomefile);
		
		//menù
		char scelta;
		do {
			System.out.println(); //riga bianca
			System.out.println("OPERAZIONI DISPONIBILI");
			System.out.println("   [A]ggiungi un mezzo all'archivio");
			System.out.println("   [R]imuovi un mezzo dall'archivio");
			System.out.println("   [V]isualizza le informazioni di tutti i mezzi a disposizione");
			System.out.println("   [P]renota un mezzo");
			System.out.println("   [M]ostra mezzi, per categoria, disponibili in una data a scelta");
			System.out.println("   [C]ancellazione della prenotazione");
			System.out.println("   [I]ndicare il mezzo per visualizzare tutte le relative prenotazioni");
			System.out.println("   [S]alvataggio su file");
			System.out.println("   [U]scita");
			System.out.print("scelta: ");
			scelta = input.next().charAt(0);
			input.nextLine(); //gestisce "a capo"

			System.out.println(); //riga bianca
			
			Mezzo m;
			String data;
			switch (scelta) {

				//aggiungere mezzo all'archivio
				case 'A': case 'a' :
					
					System.out.println("[A]uto");
					System.out.println("[F]urgone");
					char tipo = input.next().charAt(0);
					input.nextLine();
					if(tipo != 'A' && tipo != 'F') {
						System.out.println("Comando non disponibile");
						break;
					}
					//controllo se la targa sia nell'archivio
	
					System.out.println("Inserisci la Targa:");
					
					String t = input.nextLine();
					
					
					//cntrollo se il mezzo non è già nell'archivio, se non è presente si può continuare con la registrazione
					
					if(a.selezionaElemento(t) == null) {
						
						System.out.println("Inserisci il Modello:");
						String mo = input.nextLine();
						
						switch (tipo){
						//se si vuole inizializzare l'autovettura allora il programma permette di aggiungere i parametri del costruttore dell'autovettura
						case 'A' : case 'a':
							
							Mezzo auto = null;
							
							System.out.println("Inserisci la tipologia di carburante (diesel, benzina)");
							String al = input.nextLine();
							
							System.out.println("Inserisci la tipologia dell'autovettura (utilitaria, media o berlina)");
							String tip = input.nextLine();
							
							System.out.println("Inserisci il numero di posti (2, 4, 5)");
							String p = input.nextLine();
							
							//viene gestita l'eccezione lanciata da costruttore dell'autovettura 
							//all'interno di un do-while fintanto che i parametri non vanno tutti bene quindi è possibile inizializzare l'autovettura
							do {
								
								try {
									
									auto = new Autovettura(t, mo, p, al, tip);
									
									//vengono "catturate" le eccezioni e viene richiesto il parametro
								} catch(IllegalArgumentException e) {
									if(e.getMessage().equals("Targa necessaria.")) {
										System.out.println(e.getMessage());
										System.out.println("Inserisci la targa");
										t = input.nextLine();
										
										
									} else if(e.getMessage().equals("Tipologia di modello necessario.")) {
										System.out.println(e.getMessage());
										System.out.println("Inserisci il modello");
										mo = input.nextLine();
										
									} else if(e.getMessage().equals("Numeri posti non valido")){
										System.out.println(e.getMessage());
										System.out.println("Inserisci un numero posti corretto(2, 4, 5)");
										p = input.nextLine();
										System.out.println(p);
										
									} else if (e.getMessage().equals("Tipologia d'alimentazione non valida")){
										System.out.println(e.getMessage());
										System.out.println("Inserisci la tipologia di carburante (diesel, benzina)");
										al = input.nextLine();
										
									} else if (e.getMessage().equals("Tipologia dell'autovettura non valida")) {
										System.out.println("Inserisci la tipologia dell'autovettura (utilitaria, media o berlina)");
										tip = input.nextLine();
										
									}
								} 
							}while(auto == null);
							
							//metodo che aggiunge l'autovettura e restituisce un valore positivo se l'operazione è andata a buon fine
							if(a.aggiungiNuovoMezzo(auto)) System.out.println("Il mezzo è stato aggiunto con successo");
							else System.out.println("Errore nell'aggiunta del mezzo");
						break;
						
					case 'F' : case 'f':
						
						Mezzo camion = null;
						
						System.out.println("Inserisci la tipologia di patente necessaria (B o C)");
						String pat = input.nextLine();
						
						
						System.out.println("Inserisci la dimensione del furgone (piccolo, medio o grande)");
						String d = input.nextLine();
						
						do {
							//viene gestita l'eccezione lanciata da costruttore dell'autovettura 
							//all'interno di un do-while fintanto che i parametri non vanno tutti bene quindi è possibile inizializzare l'autovettura
							try {
								
								
								camion = new Furgone(t, mo, pat, d);
								
								//vengono "catturate" le eccezioni e viene richiesto il parametro
							}catch(IllegalArgumentException e) {
								if(e.getMessage().equals("Targa necessaria.")) {
									System.out.println(e.getMessage());
									System.out.println("Inserisci la targa");
									t = input.nextLine();
									
									
								} else if(e.getMessage().equals("Tipologia di modello necessario.")) {
									System.out.println(e.getMessage());
									System.out.println("Inserisci il modello");
									mo = input.nextLine();
									
								} else if(e.getMessage().equals("Patente non valida")) {
									System.out.println(e.getMessage());
									System.out.println("Inserisci la tipologia di patente necessaria (B o C)");
									pat = input.nextLine();
									
								} else if (e.getMessage().equals("Dimensione del furgone non valida")){
									System.out.println("Inserisci la dimensione del furgone (piccolo, medio o grande)");
									d = input.nextLine();
		
								} 
							}
						}while(camion == null);
						
						
						//metodo che aggiunge l'autovettura e restituisce un valore positivo se l'operazione è andata a buon fine
						if(a.aggiungiNuovoMezzo(camion)) System.out.println("Il mezzo è stato aggiunto con successo");
						else System.out.println("Errore nell'aggiunta del mezzo");
						break;
						
					default: 
						System.out.println("Comando non disponibile");
						break;
					
						}	
					}	
					
					break;
				
						
	
				// caso "Rimozione di un mezzo"
				case 'R' : case 'r': 
					
					System.out.println("Inserisci la targa del veicolo da rimuvere dall'archivio");
					t = input.nextLine();
					//controllo che la stringa non sia vuota
					if(!controlloStringa(t)) {
						//controllo se ci sono delle prenotazioni per un mezzo
						if(!ap.cronologiaPrenotazioniMezzo(t).isEmpty())
							System.out.println("Operazione non possibile. Risultano esserci delle prenotazioni per questo mezzo.");
							//in caso non ce ne siano rimuovo il mezzo
						else {
							if(a.rimuoviMezzo(t)) System.out.println("Il mezzo è stato rimosso con successo");
							else System.out.println("Mezzo impossibile da rimuovere perchè non presente nell'archivio");
							
						}
						
					}
					else System.out.println("Stringa vuota non ammessa.");
					
					break;
	
				//caso "Visualizzazione di tutti i mezzi a disposizione"
				case 'V' : case 'v': 
					if(a.elencoMezziDisposizione().isEmpty()) System.out.println("Non sono presenti mezzi a disposizione.");
					for(Mezzo mezzo : a.elencoMezziDisposizione()) {
						System.out.println(mezzo);
					}
						
					break;
	
				// caso "Prenotazione di un mezzo"
				case 'P' : case 'p':
	
					//viene richiesta la data finchè non è nel formato richiesto ed è una data selezionabile (ossia a partire dalla data corrente) e valida
					do {
						System.out.println("Inserire la data della prenotazione nel formato dd/mm/AAAA");
						data = input.next();
						input.nextLine();
					}while(!controlloData(data));
	
					System.out.println("Inserire la targa");
					String targa = input.nextLine();
					//se la stringa è vuota o la targa non è presente nell'archivio la prenotazione si interrompe
					if(controlloStringa(targa) || a.selezionaElemento(targa) == null) {
						System.out.println("Errore nell'inserimento della targa. La targa non esiste");
						break;
					} 
					
					//check che il mezzo sia disponbile nella data inserita. 
					if(ap.checkPrenotazione(a.selezionaElemento(targa), data)) {
						System.out.println("Prenotazione a nome di:");
						
						String prenotatore;
						do { //dato che la referenza è strettamente necessario per la prenotazione, il programma non procede fin quando il campo non viene riempito
							prenotatore = input.nextLine();
							if(controlloStringa(prenotatore)) {
								
								System.out.println("Inserire la referenza della prenotazione del mezzo "+targa+" per la data: "+data);
								
							}
						}while(controlloStringa(prenotatore));
						
						Prenotazione p = new Prenotazione(a.selezionaElemento(targa), data, prenotatore);
						if(ap.aggiungiPrenotazione(p)) {
							System.out.println("La prenotazione è stata aggiunta");
						} else System.out.println("La prenotazione non è stata effettuata.");
						
					} else System.out.println("Operazione non disponibile. Il mezzo risulta essere già stato prenotato.");
					break;
				
						// caso "mostra mezzi per categoria e data"
				case 'M' : case 'm':
					do {
						System.out.println("Inserire una data nel formato dd/mm/yyyy");
						data = input.next();
						input.nextLine();
					
					}while(!controlloData(data)); //controllo che la data sia valida e che sia nel giusto formato
					System.out.println("Scegliere i mezzi da visualizzare");
					System.out.println("[A]uto");
					System.out.println("[F]urgone");
					scelta = input.next().charAt(0);
					
					switch(scelta) {
					case 'A' : case 'a': 
						//controllo che nell'archivio siano presenti dei mezzi della categoria richiesta
						if(a.selezionaPerCategoria("Autovettura")!= null) {
							
							//al metodo vengono passati la data, la tipologia del mezzo in formato stringa e i vettore di mezzi a disposizione di quella categoria dell'archivio
							if(ap.getCategoriaData(data, "Autovettura", a.selezionaPerCategoria("Autovettura"))!=null){
								for(Mezzo mezzo : ap.getCategoriaData(data, "Autovettura", a.selezionaPerCategoria("Autovettura"))) {
									System.out.println(mezzo); //ne vengono stampati gli elementi
								}
							} else System.out.println("Non sono presenti elementi per questa categoria in questa data");
							
						}else System.out.println("Non sono presenti elementi per questa categoria");
						
						break;
					
					case 'F' : case 'f': 
						//controllo che nell'archivio siano presenti dei mezzi della categoria richiesta
						if(a.selezionaPerCategoria("Furgone")!= null) {
							
							//al metodo vengono passati la data, la tipologia del mezzo in formato stringa e i vettore di mezzi a disposizione di quella categoria dell'archivio
							if(ap.getCategoriaData(data, "Furgone", a.selezionaPerCategoria("Furgone"))!=null){
								for(Mezzo mezzo : ap.getCategoriaData(data, "Furgone", a.selezionaPerCategoria("Furgone"))) {
									System.out.println(mezzo); //ne vengono stampati gli elementi
								}
							} else System.out.println("Non sono presenti elementi per questa categoria in questa data");
							
						}else System.out.println("Non sono presenti elementi per questa categoria");
						
						break;
					
					default: 
						System.out.println("Caso non disponibile");
						break;
					
					}
					
					break;
				
				// caso "cancella prenotazione"
				case 'C' : case'c':
					boolean dataOk = false;
					System.out.println("Inserire la targa");
					t = input.nextLine();
					if(a.selezionaElemento(t) == null) {
						System.out.println("Mezzo non presente in archivio");
						break;
					}
					else m = a.selezionaElemento(t); 
					do{
						System.out.println("Inserire la data");
						data = input.nextLine();
						dataOk = controlloData(data);
					}while(!dataOk); 
	
					if(ap.rimuoviPrenotazione(m, data)) System.out.println("Prenotazione rimossa");
					else System.out.println("Prenotazione non presente");
					
					break;
	
				// caso "Indicare il mezzo per visualizzare tutte le relative prenotazioni"
				case 'I' : case 'i':
					System.out.println("Inserisci la targa del veicolo");
					t = input.nextLine();
					//controllo che esista il mezzo nell'archivio
					if(a.selezionaElemento(t) != null) {
						//controllo che ci siano delle prenotazioni per quel mezzo
						 if(!ap.cronologiaPrenotazioniMezzo(t).isEmpty()) {
							 for (Prenotazione p : ap.cronologiaPrenotazioniMezzo(t)) {
								 System.out.println(p); //se queste ci sono vengono stampate
							 } 
						 }else System.out.println("Non sono presenti prenotazioni per\n" + a.selezionaElemento(t));
					} else System.out.println("Mezzo non presente nell'archivio");
					
					break;     
				
				case 'S' : case 's' : // salva il registro
					boolean tuttoOk = a.salva(); //booleano che ritorna true se le modifiche apportate al vettore dei mezzi sono state salvate
					boolean allesOk = ap.salva(); //booleano che ritorna true se le modifiche apportate al vettore delle prenotazioni sono state salvate
					if (tuttoOk && allesOk)
						System.out.println("Dati Salvati");
					else
						System.out.println("Problema durante il salvataggio");
					break;
					
				case 'U' : case 'u' :// esce dal programma
					if (a.daSalvare() || ap.daSalvare()) {
						char risposta;
						do {
							System.out.println("Vuoi salvare le modifiche effettuate? \n[S]i\n[N]o");
							risposta = input.next().charAt(0);
							input.nextLine();
							if (risposta=='S') {
								boolean tuttoOk2 = a.salva();
								boolean allesOk2 = ap.salva();
								if (tuttoOk2 && allesOk2)
									System.out.println("Dati Salvati");
								else
									System.out.println("Problema durante il salvataggio");
							}
						} while (risposta!='S' && risposta!='N');
					}
			}

		System.out.println(); // riga vuota tra una operazione e l'altra

		} while (scelta!='U');

	}
	
	

	//metodo statico che controlla se l'input dato dall'utente non sia vuoto
	private static boolean controlloStringa(String t) {
		if(t.isEmpty()) { 
			return true;
		}else return false;
		
	}
	
	//metodo statico che controlla la data inserita in input (in formato stringa)
	public static boolean controlloData(String data)throws DateTimeException {
		
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		df.setLenient(false);
		LocalDate localDate = null;
		
		try {
			//controlla il formato
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	        localDate = LocalDate.parse(data, formatter);
	        LocalDate today = LocalDate.now();
	        //controlla se è una data valida per la prenotazione
			if(localDate.isBefore(today)) 
				throw new DateTimeException(data);
			else return true;
			
		} catch (DateTimeException e) {
			System.out.println("Data non corretta, ritenta");
			return false;
		} 
		
	}
	
}
	
	
	

