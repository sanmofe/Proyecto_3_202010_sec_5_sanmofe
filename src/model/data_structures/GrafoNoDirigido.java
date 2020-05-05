package model.data_structures;

import java.util.ArrayList;

import model.data_structures.HTLPGraphs;
import model.data_structures.Vertice;
import model.data_structures.Grafos.Queue;

public class GrafoNoDirigido<K> {

	private int numVertices;
	
	private int numArcos;
	
	private HTLPGraphs vertices;
	
	private int lol;
	
	public GrafoNoDirigido(int n) {
		numVertices = n;
		vertices = new HTLPGraphs(n);
		lol = 0;
	}
	
	public int V(){ //VÉRTICES
		return numVertices;
	}
	
	public int E(){ //ARCOS
		return numArcos;
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
		Queue cola = new Queue<>();
		for(Vertice vertice : vertices.darData())
		{
			if(vertice!=null)
			{
				cola.agregar(vertice);
			}
		}
		Vertice[] vARetornar = new Vertice[cola.size()];
		for(int i = 0; i<cola.size(); i++)
		{
			
			vARetornar[i] = (Vertice) cola.dequeue();
		}
		return vARetornar;
	}
	
	public void uncheck(){
		
	}
	
	
	public int cc(){
		Vertice todos[] = vertices.darData();
		int cantidad = 0;
		int cc = 1;
		for(Vertice v : todos)
		{
			if(v!=null)
			{
				if(!v.isMarked())
				{
					cantidad++;
					DepthFirstSearch(v.darId(),cc);
					cc++;
				}
			}
			
			
		}
		uncheck();
		return cantidad;
	}
	
	public Iterable<K> getCC(K idVertex){
		return new ArrayList<K>(); //TODO Hay que cambiar esto o nos pegan x2 xd
	}

	public void DepthFirstSearch(int vID, int cc)   
	{        
		
		int i = 0, b = 0;
		
		i = dfs(vID, cc, b);
	}   

	private Vertice getVertex(int id)
	{
		return (Vertice) vertices.get((int) id);
	}
	
	private int dfs(int vID, int cc, int pCantMarcada)   
	{      
		int cantMarcada = pCantMarcada;
		Vertice actual = getVertex(vID);
		actual.marcar(); 
		actual.setComponenteConectada(cc);
		cantMarcada++;   
		
		int[] aExplorar = actual.adj();
		for (int w : aExplorar)
		{
			
			Vertice adyActual = (Vertice) vertices.get(w);
			if (!adyActual.isMarked())
			{
				cantMarcada=dfs(w, cc, cantMarcada); 
				
			}
		}
		return cantMarcada;

	}
}
