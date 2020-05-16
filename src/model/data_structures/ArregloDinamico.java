package model.data_structures;

import java.util.Iterator;


/**
 * 2019-01-23
 * Estructura de Datos Arreglo Dinamico de Strings.
 * El arreglo al llenarse (llegar a su maxima capacidad) debe aumentar su capacidad.
 * @author Fernando De la Rosa
 *
 */
public class ArregloDinamico<T> implements IArregloDinamico<T>, Iterable<T>{
		/**
		 * Capacidad maxima del arreglo
		 */
        private int tamanoMax;
		/**
		 * Numero de elementos presentes en el arreglo (de forma compacta desde la posicion 0)
		 */
        private int tamanoAct;
        /**
         * Arreglo de elementos de tamaNo maximo
         */
        private T elementos[ ];

        /**
         * Construir un arreglo con la capacidad maxima inicial.
         * @param max Capacidad maxima inicial
         */
		public ArregloDinamico( int max )
        {
               elementos = (T[]) new Object[max];
               tamanoMax = max;
               tamanoAct = 0;
        }
        
		public void agregar( T dato )
        {
               if ( tamanoAct == tamanoMax )
               {  // caso de arreglo lleno (aumentar tamaNo)
                    tamanoMax = 2 * tamanoMax;
                    T [ ] copia = elementos;
                    elementos = (T[]) new Comparable[tamanoMax];
                    for ( int i = 0; i < tamanoAct; i++)
                    {
                     	 elementos[i] = copia[i];
                    } 
            	    //System.out.println("Arreglo lleno: " + tamanoAct + " - Arreglo duplicado: " + tamanoMax);
               }	
               elementos[tamanoAct]=dato;
               tamanoAct++;
       }

		public int darCapacidad() {
			return tamanoMax;
		}

		public int darTamano() {
			return tamanoAct;
		}

		public T darElemento(int i) {
			// TODO implementar
			
			return elementos[i];
		}

		public T buscar(T dato) {	
			T devuelveme = null;
			boolean loEncontre = false;
			for ( int i = 0; i < tamanoAct && !loEncontre; i++)
			{
				if ( elementos[i].equals( dato ) )
				{
					loEncontre = true;
					devuelveme = elementos[i];
				}
			}
			return devuelveme;
		}

		public T eliminar(T dato) {
			int aquiEstoy = 0;
			T devuelveme = null;
			T[] temp = (T[]) new Object[tamanoMax];
			for(int i=0; i<tamanoMax; i++) {
				if(elementos[i]!= null) {
					if(elementos[i]!= null && !(elementos[i].equals(dato))) {
						temp[aquiEstoy] = elementos[i];
						aquiEstoy++;
					}		
					else if(elementos[i] != null && elementos[i].equals(dato)) {
						devuelveme = elementos[i];
						tamanoAct--;
					}
				}
			}
			elementos = temp.length==0? (T[])new Object[tamanoMax]:temp;
			return devuelveme;

		}
		
		
		public T[] getElementsArray() {
			return elementos;
		}
		
		public T[] toArray(T[] array){
			if(tamanoAct != array.length) {
				throw new IndexOutOfBoundsException("Array expected to be sized " + tamanoAct + " but instead got " + array.length);
			}
			for(int i = 0; i < tamanoAct; i++) {
				array[i] = elementos[i];
			}
			return array;
		}

		@Override
		public Iterator<T> iterator() {
			// TODO Auto-generated method stub
			return new ArregloDinamicoIterator<>(elementos, tamanoAct);
		}

}