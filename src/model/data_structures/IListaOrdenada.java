  
package model.data_structures;

import model.data_structures.ListaOrdenada.Nodo;

public interface IListaOrdenada<T> {
	
	int contarDatos();
	
	T darDatosPosicion(int pos);
	
	T darPrimerElemento();
	
	ListaOrdenada.Nodo darUltimoElemento();
	
	boolean hayAlgoEnLaLista();
	
	T borrar(T dato);

	void insertar(T dato);
	
	T buscar(T dato);

}