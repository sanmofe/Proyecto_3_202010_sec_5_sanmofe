package model.data_structures;

import java.util.ArrayList;

import model.data_structures.HTLPGraphs;
import model.data_structures.Vertice;

public class GrafoNoDirigido<K, V> {

	private int numVertices;
	
	private int numArcos;
	
	private HTLPGraphs vertices;
	
	private int lol;
	
	public GrafoNoDirigido(int n) {
		numVertices = n;
		vertices = new HTLPGraphs(n);
		lol = 0;
	}
	
	public int V(){ //V�RTICES
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
	
	public double getCostArc(int idVertexIni, int idVertexFin){
		Vertice ini = (Vertice) vertices.get(idVertexIni);
		return ini.darPeso(idVertexFin);
	}

	public void setCostArc(int idVertexIni, int idVertexFin, double cost){
		Vertice ini = (Vertice) vertices.get(idVertexIni);
		Vertice fin =  (Vertice) vertices.get(idVertexFin);
		ini.setPesoArco(idVertexFin, cost);
		fin.setPesoArco(idVertexIni, cost);
	}
	
	public void addVertex(K idVertex, Double lat, Double lon){
		
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
		Vertice[] vARetornar = new Vertice[cola.darLongitud()];
		for(int i = 0; i<cola.darLongitud(); i++)
		{	
			vARetornar[i] = (Vertice) cola.eliminarUltimo();
		}
		ArregloDinamico<Vertice> arreglo = new ArregloDinamico<>(3);
		for (Vertice vertice : vARetornar) {
			arreglo.agregar(vertice);
		}
		return (Iterable<K>) arreglo;
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
