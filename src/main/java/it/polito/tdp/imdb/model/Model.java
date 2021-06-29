package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.imdb.db.Adiacenza;
import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {

	
	private ImdbDAO dao;
	private SimpleWeightedGraph<Director, DefaultWeightedEdge> grafo;
	private Map<Integer,Director> idMap;
	
	public Model() {
		dao = new  ImdbDAO();
		idMap = new HashMap<Integer,Director>();
		dao.listAllDirectors(idMap);
	}
	
	public void creaGrafo(int anno) {
		grafo = new SimpleWeightedGraph<Director, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		//vertici
		Graphs.addAllVertices(grafo, dao.getVertici(anno, idMap));
		
		//archi
		for(Adiacenza a: dao.getAdiacenze(anno, idMap)) {
			if(this.grafo.containsVertex(a.getD1()) && this.grafo.containsVertex(a.getD2())) {
				DefaultWeightedEdge e = this.grafo.getEdge(a.getD1(), a.getD2());
				if(e==null) {
					Graphs.addEdgeWithVertices(grafo, a.getD1(), a.getD2(), a.getPeso());
				}
			}
		}
	}
	
	
	//LISTA DI VICINI AL DIRECTOR SELEZIONATO, IN ORDINE DECRESC DI PESO 
	//---> LISTA DIRECTOR + PESO CON COMPARABLE
	public List<DirectorAdiacenti> getDirectorAdiacenti(Director tendina){
		List<DirectorAdiacenti> result = new ArrayList<DirectorAdiacenti>();
		double peso=0.0;
		for(Director d: Graphs.neighborListOf(grafo, tendina)) {
			DefaultWeightedEdge e= this.grafo.getEdge(tendina, d);
			peso= this.grafo.getEdgeWeight(e);
			
			DirectorAdiacenti da = new DirectorAdiacenti(d, peso);
			result.add(da);
		}
		return result;
	}
	
	public Set<Director> getVerticiTendina(){
		return this.grafo.vertexSet();
	}
	
	public int numVertici() {
		if(this.grafo!=null)
			return this.grafo.vertexSet().size();
		return 0;
	}
	public int numArchi() {
		if(this.grafo!=null)
			return this.grafo.edgeSet().size();
		return 0;
	}
}
