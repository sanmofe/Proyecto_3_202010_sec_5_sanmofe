package model.logic;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.SimpleFormatter;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.data_structures.GrafoNoDirigido;
import model.data_structures.Vertice;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Definicion del modelo del mundo
 *
 */
public class Modelo {
	/**
	 * Atributos del modelo del mundo
	 */
	//private IListaOrdenada<Infraccion> datos;
	public final static String  ARCOS= "./data/Acrods.txt";
	public final static String  VERTICES= "./data/Vertices.txt";
	public final static String ESTACIONES= "./data/estacionpolicia.geojson.json";	

	private GrafoNoDirigido grafo;
	//	private RedBlackBST<String, Infraccion> arbol;
	/**
	 * Constructor del modelo del mundo con capacidad predefinida
	 */
	public Modelo()
	{

		cargarDatosEstaciones(ESTACIONES);
		cargarDatosVertices(VERTICES);
		cargarDatosArcos(ARCOS);
		
		grafo = new GrafoNoDirigido<>(50000);

	}



	/**
	 * 
	 * Servicio de consulta de numero de elementos presentes en el modelo 
	 * @return numero de elementos presentes en el modelo
	 */
	public void cargarDatosVertices(String pRutaArchivo)
	{
			try{
				BufferedReader br = new BufferedReader (new FileReader(new File(pRutaArchivo)));
				String linea = br.readLine();
				//Esto es para tratar de evitar que el error suceda
				while(linea.startsWith("#")) {
					linea = br.readLine();
				}
				while (linea!=null)
				{
					String[] datosR = linea.split(",");
					Vertice x = new Vertice(Integer.parseInt(datosR[0]), Double.parseDouble(datosR[1]),Double.parseDouble(datosR[2]));
					System.out.println("Esto se ejecuta");
					grafo.addVertex(x.darId(), x.darLatitud(),x.darLongitud());
					System.out.println("Esto NO se ejecuta");
					linea = br.readLine();
				}
				br.close();
			}
			catch (Exception e) 
			{
				System.out.println("ERROR");
				System.out.println(e.getMessage());	
				System.out.println(e.getCause());
				System.out.println(e.getStackTrace());
			}
	}
	public void cargarDatosArcos(String pRutaArchivo)
	{
		try{
			BufferedReader br = new BufferedReader (new FileReader(new File(pRutaArchivo)));
			String linea = br.readLine();
			while (linea!=null)
			{
				String[] datosR = linea.split(" ");
				int idActual = Integer.parseInt(datosR[0]);
				for (int i=1; i<=datosR.length; i++)
				{

					int idAdyacente = Integer.parseInt(datosR[i]);
				}
				//AQUI SE AGREGAN los cosos al otro.
			}
			br.close();
		}
		//TODO Parte 4 PuntoA : Complete el método según la documentación dada.
		catch (Exception e) 
		{
			System.out.println(e.getMessage());			
		}		
	}

	public void cargarDatosEstaciones(String pRutaArchivo) {

		JsonParser parser = new JsonParser();
		InputStreamReader inputStreamReader;
		try {
			inputStreamReader = new InputStreamReader(new FileInputStream(new File(pRutaArchivo)), StandardCharsets.UTF_8);
			JsonObject object = parser.parse( inputStreamReader).getAsJsonObject();
			JsonArray features = object.get("features").getAsJsonArray();
			for(JsonElement jo : features) {
				JsonObject elem = jo.getAsJsonObject();
				Estacion x = new Estacion(elem);
				//AGREGA EN LAS ESTRUCTURAS DE DATOS

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}