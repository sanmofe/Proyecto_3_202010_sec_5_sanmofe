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
			System.out.println("2: Crear una copia del grafo en un archivo .json");
			System.out.println("3: Abrir la ventana de Maps");
			System.out.println("4: Método inicial");
			System.out.println("5: a1");
			System.out.println("6: b1");
			}

		public void printMessage(String mensaje) {

			System.out.println(mensaje);
		}		
		
		public void printModelo(Modelo modelo)
		{
			// TODO implentar, lo que sea que eso signifique
		}
}
