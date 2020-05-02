package model.data_structures;

import java.util.ArrayList;

public class GrafoNoDirigido<K> {

	public GrafoNoDirigido(int n){
		//TODO
	}
	
	public int V(){ //VÉRTICES
		return 0;
	}
	
	public int E(){ //ARCOS
		return 0;
	}
	
	public void addEdge(K idVertexIni, K idVertexFin, double cost){
		
	}
	
	public V getInfoVertex(K idVertex){
		return null;
	}
	
	public void setInfoVertex(K idVertex, V infoVertex){
		
	}
	
	public double getCostArc(K idVertexIni, K idVertexFin){
		return 0;
	}

	public void setCostArc(K idVertexIni,K idVertexFin, double cost){
		
	}
	
	public void addVertex(K idVertex, V infoVertex){
		
	}

	public Iterable <K> adj (K idVertex){
		return new ArrayList<K>(); //TODO Hay que cambiar esto o nos pegan xd 
	}
	
	public void uncheck(){
		
	}
	
	public void dfs(K s){
		
	}
	
	public int cc(){
		return (int) Math.round(Math.random()*1000); //B)
	}
	
	public Iterable<K> getCC(K idVertex){
		return new ArrayList<K>(); //TODO Hay que cambiar esto o nos pegan x2 xd
	}

}
