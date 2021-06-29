package it.polito.tdp.imdb.model;

public class DirectorAdiacenti implements Comparable<DirectorAdiacenti> {
	
	private Director d;
	private Double peso;
	public DirectorAdiacenti(Director d, Double peso) {
		super();
		this.d = d;
		this.peso = peso;
	}
	public Director getD() {
		return d;
	}
	public void setD(Director d) {
		this.d = d;
	}
	public Double getPeso() {
		return peso;
	}
	public void setPeso(Double peso) {
		this.peso = peso;
	}
	@Override
	public int compareTo(DirectorAdiacenti o) {
	
		return o.getPeso().compareTo(this.getPeso());
	}
	@Override
	public String toString() {
		return d.getId()+"  "+d.getLastName()+" "+d.getFirstName()+" #attori "+peso;
	}
	
	
	

}
