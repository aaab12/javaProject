public class Furgone extends Mezzo{
	
	private static final long serialVersionUID = 1L;
	private String patente;
	private String dimensioni;
	
	public Furgone(String targa, String modello, String pat, String d) throws IllegalArgumentException{
		super(targa, modello); 
		
		if(pat.equalsIgnoreCase("b"))
			this.patente = "B";
		else if(pat.equalsIgnoreCase("c"))
			this.patente = "C";
		else throw new IllegalArgumentException("Patente non valida");
	
		if(d.equalsIgnoreCase("Piccolo") || d.equalsIgnoreCase("Medio") || d.equalsIgnoreCase("grande")) 
			this.dimensioni = d.toLowerCase();
		else throw new IllegalArgumentException("Dimensione del furgone non valida");
		
		
		
	}
	
	

	//getter 
	public String getPatente() {
		return patente;
	}
	public String getDimensioni() {
		return dimensioni;
	}
	public String toString() {
		return("[Furgone]: "+targa+"\nModello: "+modello+"\nPatente: "+patente+"\nDimensioni: "+dimensioni+"\n");
	}
}
