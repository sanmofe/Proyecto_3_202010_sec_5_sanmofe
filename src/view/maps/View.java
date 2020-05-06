package view.maps;

import model.logic.Modelo;

public class View 
{
	    /**
	     * Metodo constructor
	     */
	    public View()
	    {
	    	
	    }
	    
		public void printMenu()
		{
			System.out.println("1: Cargar la informacion.");
			System.out.println("2: Requerimiento 2: Consultar un comparendo por id.");
			System.out.println("3: Requerimiento 3: Consultar los comparendos con un id en un rango específico");
			System.out.println("");
			}

		public void printMessage(String mensaje) {

			System.out.println(mensaje);
		}		
		
		public void printModelo(Modelo modelo)
		{
			// TODO implentar, lo que sea que eso signifique
		}
}
