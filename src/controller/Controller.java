package controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;


import model.logic.Modelo;
import view.maps.Maps;
import view.maps.View;

public class Controller {

	/* Instancia del Modelo*/
	private Modelo modelo;

	/* Instancia de la Vista*/
	private View view;

	/**
	 * Crear la vista y el modelo del proyecto
	 * @param capacidad tamaNo inicial del arreglo
	 */
	public Controller ()
	{
		view = new View();
	}

	public void run() 
	{
		Scanner lector = new Scanner(System.in);
		boolean fin = false;
		String dato = "";
		String respuesta = "";

		while( !fin ){
			view.printMenu();

			int option = lector.nextInt();
			switch(option){
			case 1:
				modelo = new Modelo(); 
				view.printMessage(modelo.cargarTodosLosDatos());
				break;
			case 2:
				modelo.hacerUnArchivoJSON();
				view.printMessage("El archivo se ha generado.");
				break;
			case 3:
				Maps mapa = new Maps("idk man");
				mapa.initFrame();
				break;
			case 4:
				
				break;
				default: 
				view.printMessage("--------- \n Opcion Invalida !! \n---------");
				break;
			}
		}

	}	
}