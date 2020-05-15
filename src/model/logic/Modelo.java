package model.logic;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.SimpleFormatter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.data_structures.Arco;
import model.data_structures.GrafoNoDirigido;
import model.data_structures.RedBlackBST;
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
import java.io.Writer;
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
	public final static String JSON = "./data/grafo.json";
	public final static String COMPARENDOS = "./data/Comparendos_DEI_2018_Bogotá_D.C_small_50000_sorted.geojson.json";

	
	/**
	 * El árbol binario que contiene una copia de los vértices.
	 */
	private RedBlackBST<Double, Vertice> arbol;
	/**
	 * El grafo que va a contener... Uh... Todo?
	 */
	private GrafoNoDirigido<Integer, Vertice> grafo;
	//	private RedBlackBST<String, Infraccion> arbol;
	/**
	 * Constructor del modelo del mundo con capacidad predefinida
	 */
	public Modelo()
	{
		grafo = new GrafoNoDirigido<>(750000);
		arbol = new RedBlackBST<Double, Vertice>();
	}


	public String cargarTodosLosDatos() {
		return("Datos de vértices: " + cargarDatosVertices(VERTICES) + "\nDatos de estaciones:\n" + cargarDatosEstaciones(ESTACIONES) + "\nDatos de comparendos:\n" + cargarDatosComparendos(COMPARENDOS) + "\nDatos de arcos:\n" + cargarDatosArcos(ARCOS));
	}
	
	/**
	 * 
	 * Servicio de consulta de numero de elementos presentes en el modelo 
	 * @return numero de elementos presentes en el modelo
	 */
	public String cargarDatosVertices(String pRutaArchivo)
	{
		String ultimo = null;
		String lineaActual = "";
		try {
			BufferedReader lector = new BufferedReader( new FileReader( VERTICES ) );
			lineaActual = lector.readLine();
			while(lineaActual != null) {
				String[] partes = lineaActual.split(",");
				int idVertex = Integer.parseInt(partes[0]);
//				if(idVertex != 0 && (idVertex % 37501 == 0 || idVertex == 150003)) {
//					System.out.println("It's rewind time.");
//				}
				double lat = Double.parseDouble(partes[1]);
				double lon = Double.parseDouble(partes[2]);
				grafo.addVertex(idVertex, lat, lon);
				//NO SÉ SI USAR LA LAT O LA LON AAAAAAAAAAAAAA
				arbol.put(lat, new Vertice(idVertex,lon,lat));
				ultimo = lineaActual;
				lineaActual = lector.readLine();
			}
			lector.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
		}
		return "Vértice con el mayor ID: " + ultimo;
	}
	public String cargarDatosArcos(String pRutaArchivo)
	{
		String ultimo = "";
		String linea = "";
		try{
			BufferedReader br = new BufferedReader (new FileReader(new File(pRutaArchivo)));
			linea = br.readLine();
			while (linea!=null)
			{
				String[] datosR = linea.split(" ");
				int idUno = Integer.parseInt(datosR[0]);
//				while(idUno  % 37501 == 0 || idUno == 150003) {
//					linea = br.readLine();
//					datosR = linea.split(" ");
//					idUno = Integer.parseInt(datosR[0]);
//				}
				double latUno = grafo.getInfoVertex(idUno)[2];
				double lonUno = grafo.getInfoVertex(idUno)[1];
				for(int i = 1; i < datosR.length; i++) {
					int idDos = Integer.parseInt(datosR[i]);
//					if(idDos % 37501 == 0 || idDos == 150003) {
//						double[] debug = grafo.getInfoVertex(idDos);
//						int debug2 = 1;
////					continue;
//					}
					double lonDos = grafo.getInfoVertex(idDos)[1];
					double latDos = grafo.getInfoVertex(idDos)[2];
					double costoHaversine = Haversine.distance(latUno, lonUno, latDos, lonDos);
					grafo.addEdge(idUno, idDos, costoHaversine);
				}
				ultimo = linea;
				linea = br.readLine();
			}
			br.close();
		}
		catch (Exception e) 
		{
			System.out.println("EEEEEEEEEEEERRRRRRRRRRRRROOOOOOOOOOOOOOORRRRRRRRRR");
			System.out.println("En la línea: " + linea);
			System.out.println(e.getCause());
			System.out.println(e.getMessage());			
		}		
		
		return "Arco con el mayor ID: " + ultimo;
	}

	public String cargarDatosEstaciones(String pRutaArchivo) {
		String devuelveme = "";
		JsonParser parser = new JsonParser();
		InputStreamReader inputStreamReader;
		int conteoEstaciones = 0;
		try {
			inputStreamReader = new InputStreamReader(new FileInputStream(new File(pRutaArchivo)), StandardCharsets.UTF_8);
			JsonObject object = parser.parse( inputStreamReader).getAsJsonObject();
			JsonArray features = object.get("features").getAsJsonArray();

			for(JsonElement jo : features) {
				JsonObject elem = jo.getAsJsonObject();
				Estacion x = new Estacion(elem);
				//AGREGA EN LAS ESTRUCTURAS DE DATOS
				conteoEstaciones++;
				devuelveme += (x.getNombre() + "\n");
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		devuelveme += "Estaciones cargadas: " + conteoEstaciones;
		return devuelveme;
	}

	public String cargarDatosComparendos(String pRutaArchivo){
		Infraccion mayor = null;
		int conteoInfracciones = 0;
		try {
			JsonParser parser = new JsonParser();
			InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(new File(pRutaArchivo)), StandardCharsets.UTF_8);
			JsonObject object = parser.parse( inputStreamReader).getAsJsonObject();
			JsonArray features = object.get("features").getAsJsonArray();
			for(JsonElement jo : features) {
				JsonObject elem = jo.getAsJsonObject();
				Infraccion x = new Infraccion(elem);
				//AGREGA EN LAS ESTRUCTURAS DE DATOS
				if( mayor == null || x.getObjectId() > mayor.getObjectId()) {
					mayor = x;
				}
				conteoInfracciones++;
			}
		}
		catch (Exception e) {
			System.out.println("Se generï¿½ un error. F");
			e.printStackTrace();
		}
		
		return (mayor.toString() + "\nTotal comparendos: " + conteoInfracciones);
	}
	
	public void hacerUnArchivoJSON(){
		try {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			Writer writer = Files.newBufferedWriter(Paths.get(JSON));
			Vertice[] vertices = grafo.darTodos();
			for (Vertice v : vertices) {
				gson.toJson(v, writer);
				writer.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		try {
//			Vertice[] vertices = grafo.darTodos();
//			File json = new File(JSON);
//			if(!json.exists()) json.mkdirs();
//			PrintWriter out;
//			out = new PrintWriter( json );
//			int vActual = 0;
//			out.println( "{" );
//			for (Vertice v : vertices) {
//				out.println("    {");
//				out.println("    id:"+v.darId());
//				out.println("    latitud:"+v.darLatitud());
//				out.println("    longitud:"+v.darLongitud());
//				Arco[] arcos = v.darArcosD();
//				int aActual = 0;
//				out.println("    arcos: {");
//				for (Arco a : arcos) {
//					out.println("        {");
//					out.println("        idOrigen:"+a.darIdOrigen());
//					out.println("        idDestino:"+a.darIdDestino());
//					out.println("        pesoHaversine:"+a.darPeso());
//					aActual++;
//					if(aActual < arcos.length) {
//						out.println("        },");
//					}
//					else {
//						out.println("        }");
//					}
//				}
//				Iterator<Estacion> estaciones = v.estaciones();
//				Estacion actual = estaciones.next();
//				out.println("    estaciones: {");
//				while(estaciones.hasNext()) {
//					out.
//				}
//				vActual++;
//				if(vActual < vertices.length) {
//					out.println("    },");
//				}
//				else {
//					out.println("    }");
//				}
//			}
//			out.println("}");
//			out.close( );
//		}
//		catch(Exception e) {
//			System.out.println("Hubo un error al hacer el archivo. F");
//			System.out.println(e.getMessage());
//		}
	}

	/**
	 * Retorna el id del nodo más cercano basado en su distancia Haversine
	 */
	public int inicio1(double lat, double lon) {
		return 0;
	}
	/**
  	Obtener el camino de costo mínimo entre dos ubicaciones geográficas por distancia

  	Muestre en la consola de texto el camino a seguir informando:
	el total de vértices, sus vértices (Id, latitud, longitud), el costo mínimo (menor distancia
	haversiana) y la distancia estimada (sumatoria de distancias harvesianas en Km).

	Muestre el camino resultante en Google Maps (incluyendo la
	ubicación de inicio y la ubicación de destino).
	 */
	public String a1(double latIni, double lonIni, double latFin, double lonFin) {
		return "";
	}

	/**
	 Determinar la red de comunicaciones que soporte la instalación de cámaras de video
	en los M puntos donde se presentan los comparendos de mayor gravedad.

	Muestre en la consola de texto el tiempo que toma el algoritmo
	en encontrar la solución, y la siguiente información de la red propuesta: los vértices
	(identificadores) y los arcos incluidos, y el costo (monetario) total.

	Muestre en un mapa en Google Maps la red de comunicaciones
	propuesta. Resalte las M ubicaciones de las cámaras y los arcos de la red que las unen. 
	 */
	public String a2(int m) {
		return "";
	}


}
