package controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;


import model.logic.Modelo;
import view.View;

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
				System.out.println("El mayor OBJECTID es:");
				modelo = new Modelo(); 
				break;
			case 2:
				view.printMessage("Ingrese el numero de comparendos con mayor gravedad:");
				lector.nextLine();
				int num = Integer.parseInt(lector.nextLine());
				view.printMessage(modelo.a1(num));
				break;
			case 3:
				view.printMessage("Ingrese el numero de mes:");
				lector.nextLine();
				num = Integer.parseInt(lector.nextLine());
				view.printMessage("Ingrese el  d�a de la semana (L, M, I, J, V, S, D):");
				String dia = lector.nextLine();
				view.printMessage(modelo.a2(dia, num));
				break;
			case 4:
				SimpleDateFormat aaa2 = new SimpleDateFormat(" YYYY/MM/DD-HH:MM:ss");
				Date min= null;
				Date may = null;
				view.printMessage("Ingrese la fecha-hora del limite INFERIOR en formato �YYYY/MM/DD-HH:MM:ss�:");
				String fech_men = lector.nextLine();
				
				view.printMessage("Ingrese la fecha-hora del limite SUPERIOR en formato �YYYY/MM/DD-HH:MM:ss�:");
				String fech_may = lector.nextLine();
				try {
					min = aaa2.parse(fech_men);
					may = aaa2.parse(fech_may);
				} catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}
				view.printMessage("Ingrese una localidad:");
				String loca = lector.nextLine();
				view.printMessage(modelo.a3(min, may, loca));
				
				break;
			case 5:
				view.printMessage("Ingrese el número de infracciones a leer.");
				int consultaEstos = lector.nextInt();
				view.printMessage(modelo.b1(consultaEstos));
				break;
			case 6:
				lector.nextLine();
				view.printMessage("Ingrese medio de detección:");
				String medDetec = lector.nextLine();
				view.printMessage("Ingrese clase de vehículo: ");
				String clasVehi = lector.nextLine();
				view.printMessage("Ingrese tipo de servicio:");
				String tipoServi = lector.nextLine();
				view.printMessage("Ingrese localización:");
				String loc = lector.nextLine();
				view.printMessage("Ingrese número de comparendos a consultar:");
				int n = lector.nextInt();
				view.printMessage(modelo.b2(medDetec, clasVehi, tipoServi, loc, n));
				break;
			case 7:
				view.printMessage("Ingrese la latitud inicial: (NO SE INCLUIR� EN LOS RESULTADOS ESTA LATITUD EXACTA)");
				Double latIni = lector.nextDouble();
				view.printMessage("Ingrese la latitud final : (NO SE INCLUIR� EN LOS RESULTADOS ESTA LATITUD EXACTA)");
				Double latFin = lector.nextDouble();
				lector.nextLine();
				view.printMessage("Ingrese clase de vehículo: ");
				String pClase = lector.nextLine();
				view.printMessage("Ingrese número de comparendos a consultar:");
				int numro = lector.nextInt();
				view.printMessage(modelo.b3(latIni, latFin, pClase, numro));
				break;
			case 8:
				view.printMessage("Ingresar un n�mero de d�as D: ");
				lector.nextLine();
				String D=lector.nextLine();
				view.printMessage(modelo.c1(Integer.parseInt(D)));
				break;
			case 9:
				
				view.printMessage(modelo.c2());
				
				
				break;
			case 10:
				view.printMessage(modelo.c3());
				break;
				default: 
				view.printMessage("--------- \n Opcion Invalida !! \n---------");
				break;
			}
		}

	}	
}
