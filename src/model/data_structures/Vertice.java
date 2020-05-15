package model.data_structures;

import java.util.Iterator;
import java.util.LinkedList;
import model.logic.Estacion;
import model.logic.Infraccion;

public class Vertice implements Comparable<Vertice>
{
	private ArregloDinamico<Infraccion> infracciones;
	private ArregloDinamico<Estacion> estaciones;
	private LinkedList<Arco> edgeTo;
	private int id;
	private double lon;
	private double lat;
	private boolean marked;
	private int componenteConectada;
	

	public Vertice(int pId, double plon, double plat)
	{
		infracciones = new ArregloDinamico<Infraccion>(50);
		estaciones = new ArregloDinamico<Estacion>(20);
		lon = plon;
		lat = plat;
		marked = false;
		id = pId;
		edgeTo = new LinkedList<>();
		componenteConectada = -1;
	}
	
	
//	public Vertice(int pId, double plon, double plat, boolean pMarked, LinkedList pEdge)
//	{
//		lon = plon;
//		lat = plat;
//		marked = pMarked;
//		id = pId;
//		edgeTo = pEdge;
//		
//	}
	
	public void cleanArcos()
	{
		edgeTo = new LinkedList<>();
	}
	public void setComponenteConectada(int n)
	{
		componenteConectada = n;
	}
	public int darComponenteConectada()
	{
		return componenteConectada;
	}
	
	public void anadirArco(int pVId, double pPesoHaversine, double pPesoComparendos)
	{
		Arco arco = new Arco(id, pVId, pPesoHaversine, pPesoComparendos, false);
		edgeTo.add(arco);
	}
	public void eliminarArco(int pVId)
	{
		edgeTo.remove(buscarArcoA(pVId));
	}
	public int darId()
	{
		return id;
	}
	public double darLongitud()
	{
		return lon;
	}
	public double darLatitud()
	{
		return lat;
	}

	public double darPesoHaversine(int pVId)
	{
		return (double) buscarArcoA(pVId).darPesoHaversine();
	}
	
	public double darPesoComparendos(int pVId) {
		return (double) buscarArcoA(pVId).darPesoComparendos();
	}
	
	public void setInfo(int pId, double plon, double plat)
	{
		id = pId;
		lon = plon;
		lat = plat;
	}
	
	public void setPesoHaversineArco(int pIDV, double pPeso)
	{
		buscarArcoA(pIDV).setPesoHaversine(pPeso);
	}
	
	public void setPesoComparendosArco(int pIDV, double pPeso)
	{
		buscarArcoA(pIDV).setPesoComparendos(pPeso);
	}
	
	public Arco buscarArcoA(int pIDV)
	{
		boolean seEncontro = false;
		Arco aRetornar = null;
		for(int i = 0; i < edgeTo.size()&&!seEncontro; i++)
		{
			if(edgeTo.get(i).darIdDestino()==pIDV)
			{
				aRetornar = edgeTo.get(i);
			}
		}
		return aRetornar;
	}

	public void desmarcar()
	{
		marked = false;
	}
	public void marcar()
	{
		marked = true;
	}
	public boolean isMarked()
	{
		return marked;
	}
	/**
	 * Retorna la lista de IDs a los que es adyacente el vertice.
	 * @return
	 */
	public int[] adj()
	{
		int[] listaAdyacentes = new int[edgeTo.size()];
		Iterator it = edgeTo.iterator();
		for(int i = 0; i<edgeTo.size()&&it.hasNext(); i++)
		{
			Arco a = (Arco) it.next();
			if(a!=null)
			listaAdyacentes[i] = a.darIdDestino();			
		}
		return listaAdyacentes;
	}
	
	public LinkedList darArcos()
	{
		return edgeTo;
	}
	
	public Arco[] darArcosD()
	{
		Arco[] arcos = new Arco[edgeTo.size()];
		Iterator it = edgeTo.iterator();
		int i = 0;
		while(it.hasNext())
		{
			Arco a = (Arco) it.next();
			arcos[i] = a;
			i++;
		}
		return arcos;
	}
	
	
	public void desmarcarTodosArcos()
	{
		Iterator it = edgeTo.iterator();
		
		
		while(it.hasNext())
		{
			Arco actual =  (Arco) it.next();
			actual.desmarcar();
		}
	}

	@Override
	public int compareTo(Vertice arg0) {
		return id - arg0.darId();
	}
	
	
	public Iterator<Infraccion> infracciones(){
		return infracciones.iterator();
	}
	
	public Iterator<Estacion> estaciones(){
		return estaciones.iterator();
	}
	
	public int numInfracciones() {
		return infracciones.darTamano();
	}
	
	public int numEstaciones() {
		return estaciones.darTamano();
	}

	public void agregarInfraccion(Infraccion dato) {
		infracciones.agregar(dato);
	}
	
	public void agregarEstacion(Estacion dato) {
		estaciones.agregar( (Estacion)dato);
	}
}

