public class Autovettura extends Mezzo{

	private static final long serialVersionUID = 1L;
	
	private String numPosti; 
	private String tipologiaAlimentazione;
	private String tipologiaAutovettura;
	
	public Autovettura(String t, String m, String posti, String alimentazione, String tipologia) throws IllegalArgumentException{
		super(t,m);
		 		//tutti i controlli sulle specifiche della composizione degli oggetti vengono fatti dal costruttore che lancia l'eccezione se questi non corrispondono
		if (!(posti.equals("2") || posti.equals("4") || posti.equals("5"))) throw new IllegalArgumentException("Numeri posti non valido");
		else this.numPosti = posti;

		if(alimentazione.equalsIgnoreCase("diesel") || alimentazione.equalsIgnoreCase("benzina"))
			this.tipologiaAlimentazione = alimentazione.toLowerCase();
		else throw new IllegalArgumentException("Tipologia d'alimentazione non valida");
		
		if(tipologia.equalsIgnoreCase("utilitaria") || tipologia.equalsIgnoreCase("media") || tipologia.equalsIgnoreCase("berlina"))
			this.tipologiaAutovettura = tipologia.toLowerCase();
		else throw new IllegalArgumentException("Tipologia dell'autovettura non valida");
		
	}
	

	//getter
	public String getNumPosti() {
		return numPosti;
	}
	
	public String getAlimentazione() {
		return tipologiaAlimentazione;
	}
	
	public String getTipologiaAuto() {
		return tipologiaAutovettura;
	}
		
	public String toString() {
		return ("[Autovettura]:" +targa+"\nModello: "+modello+"\nTipologia: "+tipologiaAutovettura+"\nPosti: " +numPosti+"\nAlimentazione: "+tipologiaAlimentazione+"\n");
	}
	
	
}
