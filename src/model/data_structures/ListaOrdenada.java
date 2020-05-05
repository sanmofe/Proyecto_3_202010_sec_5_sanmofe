package model.data_structures;

public class ListaOrdenada<T extends Comparable<T>> implements IListaOrdenada<T>{
	/**
	 * El primer nodo de la lista
	 */
	private Nodo primero;
	/**
	 * Constructor
	 */
	public ListaOrdenada(){
		primero = null;
	}
	/**
	 * Cuenta el número de datos en la lista
	 * @return 0 si la lista está vacía, el número de datos en caso contrario
	 */
	public Nodo darPrimero()
	{
		return primero;
	}
	public int contarDatos() {
		if(!hayAlgoEnLaLista()) {
			return 0;
		}
		Nodo actual = primero;
		int total = 0;
		boolean termineAqui = false;
		while(!termineAqui)
		{
			total++;

			if(actual.darSiguiente() == null)
			{
				termineAqui = true;
			}
			else
			{
				actual = actual.darSiguiente();	
			}
		}
		return total;

	}
	/**
	 * Añade un nodo al final de la lista, en la primera posición si no hay nodos
	 * @param dato El dato que se necesita para crear el Nodo
	 */
	public void insertar(T dato) {
		if(dato != null) {
//			System.out.println("Insertando...");
//			System.out.println(dato);
			boolean hiceAlgo = false;
			if(!hayAlgoEnLaLista()) {
//				System.out.println("Ejecutando if");
				primero = new Nodo(dato);
				hiceAlgo = true;
//				System.out.println("Insertado al inicio.");
			}
			else if(dato.compareTo(primero.darContenido()) < 0) { //TODO ESTO ANTES APUNTABA AL OTRO LADO AAAAAA
//				System.out.println("Ejecuntando else if");
				Nodo agregame = new Nodo(dato);
				agregame.cambiarSiguiente(primero);
				primero = agregame;
				hiceAlgo = true;
//				System.out.println("Insertado antes del inicio.");
			}
			else {
//				System.out.println("Ejecutando else");
				Nodo actual = primero.darSiguiente();
				Nodo actualMenosUno = primero;
				while(actual != null) {
//					System.out.println("Si ves esto en toda la pantalla, esto está en un bucle.");
//					System.out.println(actual.darContenido());
//					System.out.println(dato);
					if(dato.compareTo(actual.darContenido()) <= 0) { //TODO ESTO ANTES APUNTABA AL OTRO LADO AAAAAA
						Nodo agregame = new Nodo(dato);
						agregame.cambiarSiguiente(actual);
						actualMenosUno.cambiarSiguiente(agregame);
						hiceAlgo=true;
//						System.out.println("Insertado en otro lado");
//						System.out.println(actual);
						break;
					}
					else {
						actual = actual.darSiguiente();
						actualMenosUno = actualMenosUno.darSiguiente();
					}
				}
			}
			if(!hiceAlgo) {
				darUltimoElemento().cambiarSiguiente(new Nodo(dato));
//				System.out.println("Insertado al final");
			}
		}
	}
	/**
	 * Devuelve los datos del nodo que está en la posición dada
	 * @param posicion la posición del nodo a consultar
	 * @return null si la lista está vacía o si el nodo no existe, los datos del nodo que está en la posición dada en caso contrario
	 */
	public T darDatosPosicion(int posicion) { //Realmente devuelve un T?
		Nodo devuelveMisDatos = null;
		boolean termine = false;
		Nodo actual = primero;
		int posActual = 0;
		if(!hayAlgoEnLaLista()) {
			return null;
		}
		else {
			while(!termine && posActual<contarDatos()) {
				if(posActual == posicion) {
					devuelveMisDatos = actual;
					termine = true;
				}
				posActual++;
				actual = actual.darSiguiente();
			}
		}
		return devuelveMisDatos == null? null:devuelveMisDatos.darContenido();
	}
	/**
	 * Devuelve si hay algo en la lista
	 * @return false si la lista está vacia, true en caso contrario
	 */
	public boolean hayAlgoEnLaLista() {
		return primero == null? false:true ;
	}

	public void anhadirAlPrincipio(T dato) {
		Nodo nodo = new Nodo(dato);
		if(primero==null)
		{
			primero = nodo;
		}
		else
		{
			nodo.cambiarSiguiente(primero);
			primero = nodo;
		}
	}
	/**
	 * Borra un nodo de la lista
	 * @param dato El dato que contiene el nodo a eliminar
	 * @return El dato eliminado
	 */
	public T borrar(T dato) {
		boolean termine = false;
		if(dato.equals(primero.darContenido())) {
			primero = primero.darSiguiente();
		}
		else {
			Nodo anterior = primero;
			Nodo actual = primero.darSiguiente();
			while(!termine && actual != null) {
				if(actual.darContenido().equals(dato)) {
					anterior.cambiarSiguiente(actual.darSiguiente());
					termine = true;
				}
				actual = actual.darSiguiente();
				anterior = anterior.darSiguiente();

			}
		}
		return dato;
	}


	/**
	 * Borra un dato de la lista, pero basado en su posición, no sus datos. 
	 * @param pos La posición en la que está el dato a borrar.
	 * @return El dato borrado.
	 */
	public T borrarPorPosicion(int pos) {
		T borrame = darDatosPosicion(pos);
		return borrar(borrame);
	}

	/**
	 * Devuelve el primer dato de la lista
	 * @return Null si la lista está vacía, el contenido del primer nodo en caso contrario.
	 */
	public T darPrimerElemento() {
		return hayAlgoEnLaLista()?primero.darContenido():null;
	}
	/**
	 * Devuelve el último dato de la lista
	 * @return Null si la lista está vacía, los datos del último nodo en caso contrario.
	 */
	public Nodo darUltimoElemento() {

		if(!hayAlgoEnLaLista()) {
			return null;
		}
		else {
			Nodo devuelveme = primero;
			while(devuelveme.darSiguiente() != null) {
				devuelveme = devuelveme.darSiguiente();
			}
			return devuelveme;
		}
	}

	/**
	 * No tengo ni idea de qué hace este método
	 * @param dato El dato a buscar?
	 * @return El dato que recibió por parámetro?
	 */
	public T buscar(T dato) {
		Nodo pos = primero;
		if(pos.darContenido().equals(dato)) return pos.darContenido();
		while(pos.darSiguiente() != null) {
			pos = pos.darSiguiente();
			if(pos.darContenido().equals(dato)) {
				return pos.darContenido();
			}
		}
		return dato;

	}
	/**
	 * La clase que representa un nodo de la lista.
	 */
	public class Nodo{
		/**
		 * Lo que contiene este nodo
		 */
		private T contenido;
		/**
		 * El nodo que le sigue a este
		 */
		private Nodo siguiente = null;
		/**
		 * Crea un nuevo nodo.
		 * @param informacion Lo que va a contener el nodo.
		 */
		public Nodo(T informacion) {
			contenido = informacion;
		}
		/**
		 * Devuelve el nodo siguiente a este nodo.
		 * @return el nodo siguiente a este nodo
		 */
		public Nodo darSiguiente() {
			return siguiente;
		}
		/**
		 * Cambia el nodo siguiente a este
		 * @param nuevo el nuevo nodo que va a ser el siguiente a este.
		 */
		public void cambiarSiguiente(Nodo nuevo) {
			siguiente = nuevo;
		}
		/**
		 * Devuelve el contenido de este nodo.
		 * @return el contenido de este nodo
		 */
		public T darContenido() {
			return contenido;
		}

	}


}