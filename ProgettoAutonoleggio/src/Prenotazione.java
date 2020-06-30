import java.io.Serializable;
//definisce la struttura della prenotazione
public class Prenotazione implements Serializable {
	
	static final long serialVersionUID = 1;
	//variabili d'istanza della prenotazione
	private Mezzo mezzo;
	private String data;
	private String nome;
	//costruttore di prenotazione
	public Prenotazione(Mezzo m, String d, String n){
		this.mezzo = m;
		this.data = d;
		this.nome = n;
	}
	//getter
	public Mezzo getMezzo() {
		return mezzo;
	}
	public String getDataPrenotazione() {
		return data;
	}
	public String getNomePrenotazione() {
		return nome;
	}
	
	//equals ridefinito sulla base del mezzo e della data
	public boolean equals(Object o) {
		if(o instanceof Prenotazione) {
			Prenotazione pr = (Prenotazione) o;
			return ((mezzo.equals(pr.mezzo)) && (data.equals(pr.data)));
		} else return false;
	}
	
	//toString metodo
	public String toString() {
		return "Prenotazione del mezzo " + mezzo + "a nome: " + nome + "\nin data: " + data+"\n";
	}
	
}

