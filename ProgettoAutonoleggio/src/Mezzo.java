import java.io.Serializable;

public class Mezzo implements Serializable {
	
	// variabile statica richiesta da Serializable
	static final long serialVersionUID = 1;

	protected  String targa;
	protected  String modello;
	
	
	//costruttore 
	public Mezzo(String t, String m) throws IllegalArgumentException{
		//vengono inizializzati gli oggetti solo se i campi non sono vuoti 
			if(!t.isEmpty())
				this.targa = t.toUpperCase(); 
			else throw new IllegalArgumentException("Targa necessaria.");
			
			if(!m.isEmpty())
				this.modello = m.toUpperCase();
			else throw new IllegalArgumentException("Tipologia di modello necessario.");
	}

	
	
	//getter
	public String getTarga() {
		return targa;
	}
	public String getModello() {
		return modello;
	}
	
	public String toString() {
		return "Mezzo identificato da:\n[Targa]: " + targa + "\n[Modello]:" + modello;
	}
	
	//la funzione è stata ridefinita: il controllo viene fatto su targa e modello
	public boolean equals(Object o) {
		if(o instanceof Mezzo) {
			Mezzo m = (Mezzo)o;
			return (targa.equals(m.targa));
		} else return false;
	}
	
	
	
}
