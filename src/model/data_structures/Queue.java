package model.data_structures;

public class Queue<T extends Comparable<T>> implements IQueue<T> {

	private int longitud;
	private ListaOrdenada<T> lista;
	
	public Queue()
	{
		longitud = 0;
		lista = new ListaOrdenada<T>();
	}
	@Override
	public int darLongitud() {
		return longitud;
	}
 public ListaOrdenada<T> darLista()
 {
	 return lista;
 }
	@Override
	public void agregar(T dato) {
		lista.insertar(dato);
		longitud++;
		//System.out.println("Agregando elemento en lista...");
	}

	@Override
	public T eliminarUltimo() {
		longitud--;
		return lista.borrar(lista.darUltimoElemento().darContenido());
	}

	@Override
	public boolean isEmpty() {
		return (longitud==0);
	}

	@Override
	public T primerElemento() {
		
		return lista.darPrimerElemento();
	}
	@Override
	public T ultimoElemento() {
		
		return lista.darDatosPosicion(longitud-1);
	}

}