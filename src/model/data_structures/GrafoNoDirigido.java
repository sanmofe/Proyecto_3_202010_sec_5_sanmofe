package model.data_structures;

import java.util.ArrayList;
import java.util.Iterator;

import model.data_structures.HTLPGraphs;
import model.data_structures.Vertice;
import model.logic.Estacion;
import model.logic.Infraccion;

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

	public int V(){ //VÉRTICES
		return numVertices;
	}

	public int E(){ //ARCOS
		return numArcos;
	}

	public void addEdge(int idVertexIni, int idVertexFin, double costHav, double costComp){
		Vertice V1 = (Vertice) vertices.get(idVertexIni);
		Vertice V2 = (Vertice) vertices.get(idVertexFin);
		if(V1!=null&&V2!=null)
		{
			if(V1.buscarArcoA(idVertexFin)==null&&V2.buscarArcoA(idVertexIni)==null)
			{
				V1.anadirArco(idVertexFin, costHav, costComp);
				V2.anadirArco(idVertexIni, costHav, costComp);
				numArcos++;
			}
		}
	}

	
	public int darComparendosVertice(int idVertex) {
		int respuesta = 0;
		Vertice v = (Vertice) vertices.get(idVertex);
		Iterator<Infraccion> it = v.infracciones();
		Infraccion i = it.next();
		while(it.hasNext()) {
			respuesta++;
			i = it.next();
		}
		return respuesta;
	}
	
	
	public void agregarInfraccionAVertex(int idVertex, Infraccion inf) {
		Vertice v = (Vertice) vertices.get(idVertex);
		v.agregarInfraccion(inf);
	}
	
	public void agregarEstacionAVertex(int idVertex, Estacion est) {
		Vertice v = (Vertice) vertices.get(idVertex);
		v.agregarEstacion(est);
	}
	
	public double[] getInfoVertex(int idVertex){
		double aRetornar[] = new double[3];
		aRetornar[0] = ((Vertice) vertices.get(idVertex)).darId();
		aRetornar[1] = ((Vertice) vertices.get(idVertex)).darLongitud();
		aRetornar[2] = ((Vertice) vertices.get(idVertex)).darLatitud();
		return aRetornar;
	}

	public void setInfoVertex(K idVertex, Double plon, Double plat){
		((Vertice) vertices.get((int)idVertex)).setInfo((int)idVertex, plon, plat);
	}

	public double getCostHavArc(int idVertexIni, int idVertexFin){
		Vertice ini = (Vertice) vertices.get(idVertexIni);
		return ini.darPesoHaversine(idVertexFin);
	}

	public double getCostComArc(int idVertexIni, int idVertexFin){
		Vertice ini = (Vertice) vertices.get(idVertexIni);
		return ini.darPesoComparendos(idVertexFin);
	}
	
	public void setCostHavArc(int idVertexIni, int idVertexFin, double cost){
		Vertice ini = (Vertice) vertices.get(idVertexIni);
		Vertice fin =  (Vertice) vertices.get(idVertexFin);
		ini.setPesoHaversineArco(idVertexFin, cost);
		fin.setPesoHaversineArco(idVertexIni, cost);
	}

	public void setCostCompArc(int idVertexIni, int idVertexFin, double cost){
		Vertice ini = (Vertice) vertices.get(idVertexIni);
		Vertice fin =  (Vertice) vertices.get(idVertexFin);
		ini.setPesoComparendosArco(idVertexFin, cost);
		fin.setPesoComparendosArco(idVertexIni, cost);
	}
	
	public void addVertex(int idVertex, double lat, double lon){
		if(!contains(idVertex))
		{
			vertices.put((int)idVertex, new Vertice((int) idVertex, lon, lat));
		}
		numVertices++;
	}

	public void addVertex(int idVertex, Vertice V)
	{
		if(!contains(idVertex)&&V!=null)
		{
			vertices.put(idVertex, V);
		}
		numVertices++;

	}

	public boolean contains(int pId)
	{
		if(getVertex(pId)!=null)
		{
			return true;
		}
		return false;
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

	public Vertice[] darTodos() {
		return vertices.darData();
	}
	
	public void uncheck(){
		Vertice[] todos = vertices.darData();
		for(Vertice vertice : todos)
		{
			if(vertice!=null)
			{
				vertice.desmarcar();
				vertice.setComponenteConectada(-1);
			}
		}
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

