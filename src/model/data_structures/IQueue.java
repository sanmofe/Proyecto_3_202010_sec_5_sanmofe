package model.data_structures;

public interface IQueue<T> {
	
	public int darLongitud( );
	
	public void agregar(T dato );
	public boolean isEmpty();
	public T primerElemento();
	public T eliminarUltimo();
	public T ultimoElemento();
	public ListaOrdenada darLista();
}