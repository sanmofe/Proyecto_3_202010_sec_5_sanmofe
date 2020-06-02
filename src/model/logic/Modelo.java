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
import model.data_structures.ArregloDinamico;
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
	public final static String COMPARENDOS = "./data/Comparendos_DEI_2018_Bogot�_D.C_small_50000_sorted.geojson.json";
	public final static String MINICOMPARENDOS = "./data/Comparendos_DEI_2018_Bogot�_D.C_small.geojson";
	public final static String LOSOTROSCOMPARENDOS = "./data/Comparendos_DEI_2018_Bogot�_D.C_50000_.geojson";
	/**
	 * El �rbol binario que contiene una copia de los v�rtices.
	 */
	private RedBlackBST<Double, Vertice> arbol;
	/**
	 * El grafo que va a contener... Uh... Todo?
	 */

	private ArregloDinamico<Infraccion> arreglo;

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
		return("Datos de v�rtices: " + cargarDatosVertices(VERTICES) +  "\nDatos de comparendos:\n" + cargarDatosComparendos(MINICOMPARENDOS) + "\nDatos de estaciones:\n" + cargarDatosEstaciones(ESTACIONES) + "\nDatos de arcos:\n" + cargarDatosArcos(ARCOS));
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
				//NO S� SI USAR LA LAT O LA LON AAAAAAAAAAAAAA
				arbol.put(lat, new Vertice(idVertex,lon,lat));
				ultimo = lineaActual;
				lineaActual = lector.readLine();
			}
			lector.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
		}
		return "V�rtice con el mayor ID: " + ultimo;
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
					double costoComp = grafo.darComparendosVertice(idUno) + grafo.darComparendosVertice(idDos);
					grafo.addEdge(idUno, idDos, costoHaversine, costoComp);
				}
				ultimo = linea;
				linea = br.readLine();
			}
			br.close();
		}
		catch (Exception e) 
		{
			System.out.println("EEEEEEEEEEEERRRRRRRRRRRRROOOOOOOOOOOOOOORRRRRRRRRR");
			System.out.println("En la l�nea: " + linea);
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
				//Vamos a hacer lo del �rbol AAAAAAAAAAAAA
				boolean asignado = false;
				double rango = 0.000001;
				while(!asignado) {
					double limMenor = x.getCoordenada().getLongitude() - rango;
					double limMayor = x.getCoordenada().getLongitude() + rango;
					Iterator<Vertice> verticesPosibles = arbol.valuesInRange(limMenor, limMayor ).iterator();
					Vertice v = verticesPosibles.next();
					if(v == null) {
						rango *= 2;
						continue;
					}
					double distanciaMinima = 1000000.761;
					Vertice vertActual = null;
					while(verticesPosibles.hasNext()) {
						double distancia = Haversine.distance(x.getCoordenada().getLatitude(), x.getCoordenada().getLongitude(), v.darLatitud(), v.darLongitud());
						if(distancia < distanciaMinima) {
							distanciaMinima = distancia;
							vertActual = v;
						}
						v = verticesPosibles.next();
					}
					grafo.agregarEstacionAVertex(v.darId(), x);
					asignado = true;
				}
				conteoEstaciones++;
				devuelveme += (x.getNombre() + "\n");
			}
		} catch (FileNotFoundException e) {
			System.out.println("Error. Arr�glalo, gei.");
		}
		devuelveme += "Estaciones cargadas: " + conteoEstaciones;
		return devuelveme;
	}

	public String cargarDatosComparendos(String pRutaArchivo){
		arreglo = new ArregloDinamico<Infraccion>(1);
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
				arreglo.agregar(x);

				if( mayor == null || x.getObjectId() > mayor.getObjectId()) {
					mayor = x;
				}
				conteoInfracciones++;
				//				boolean asignado = false;
				//				double rango = 0.000001;
				//				while(!asignado) {
				//					double limMenor = x.getCoordenada().getLongitude() - rango;
				//					double limMayor = x.getCoordenada().getLongitude() + rango;
				//					Iterator<Vertice> verticesPosibles = arbol.valuesInRange(limMenor, limMayor ).iterator();
				//					Vertice v = verticesPosibles.next();
				//					if(v == null) {
				//						rango *= 2;
				//						continue;
				//					}
				//					double distanciaMinima = 1000000.761;
				//					Vertice vertActual = null;
				//					while(verticesPosibles.hasNext()) {
				//						double distancia = Haversine.distance(x.getCoordenada().getLatitude(), x.getCoordenada().getLongitude(), v.darLatitud(), v.darLongitud());
				//						if(distancia < distanciaMinima) {
				//							distanciaMinima = distancia;
				//							vertActual = v;
				//						}
				//						v = verticesPosibles.next();
				//					}
				//					grafo.agregarInfraccionAVertex(v.darId(), x);
				//					asignado = true;
				//				}
				//			}
				//		}
			}
		}
		catch (Exception e) {
			System.out.println("Se gener� un error. F");
			e.printStackTrace();
		}
		asignarLosComparendos();
		return (mayor.toString() + "\nTotal comparendos: " + conteoInfracciones);
	}

	private void asignarLosComparendos() {
		for (Infraccion inf : arreglo) {
			boolean asignado = false;
			double rango = 0.000001;
			while(!asignado) {
				double limMenor = inf.getCoordenada().getLongitude() - rango;
				double limMayor = inf.getCoordenada().getLongitude() + rango;
				Iterator<Vertice> verticesPosibles = arbol.valuesInRange(limMenor, limMayor ).iterator();
				Vertice v = verticesPosibles.next();
				if(v == null) {
					rango *= 2;
					continue;
				}
				double distanciaMinima = 1000000.761;
				Vertice vertActual = null;
				while(verticesPosibles.hasNext()) {
					double distancia = Haversine.distance(inf.getCoordenada().getLatitude(), inf.getCoordenada().getLongitude(), v.darLatitud(), v.darLongitud());
					if(distancia < distanciaMinima) {
						distanciaMinima = distancia;
						vertActual = v;
					}
					v = verticesPosibles.next();
				}
				grafo.agregarInfraccionAVertex(v.darId(), inf);
				asignado = true;
			}
		}
	}

	public void hacerUnArchivoJSON(){
		//		try {
		//			Gson gson = new GsonBuilder().setPrettyPrinting().create();
		//			Writer writer = Files.newBufferedWriter(Paths.get(JSON));
		//			Vertice[] vertices = grafo.darTodos();
		//			for (Vertice v : vertices) {
		//				gson.toJson(v, writer);
		//				Arco[] arcos = v.darArcosD();
		//				for (Arco a : arcos) {
		//					gson.toJson(a);
		//				}
		//				Iterator<Estacion> estaciones = v.estaciones();
		//				Estacion e = estaciones.next();
		//				while(estaciones.hasNext()) {
		//					gson.toJson(e);
		//					e = estaciones.next();
		//				}
		//				Iterator<Infraccion> infracciones = v.infracciones();
		//				Infraccion i = infracciones.next();
		//				while (infracciones.hasNext()){
		//					gson.toJson(i);
		//					i = infracciones.next();
		//				}
		//			}
		//			writer.close();
		//		} catch (IOException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}
		try {
			Vertice[] vertices = grafo.darTodos();
			File json = new File(JSON);
			PrintWriter out;
			out = new PrintWriter( json );
			int vActual = 0;
			out.println( "{" );
			for (Vertice v : vertices) {
				System.out.println("Imprimiendo v�rtice: " + v.darId());
				out.println("    {");
				out.println("    \"id\":"+v.darId());
				out.println("    \"latitud\":"+v.darLatitud());
				out.println("    \"longitud\":"+v.darLongitud());
				Arco[] arcos = v.darArcosD();
				int aActual = 0;
				boolean imprimiAlgo = false;
				out.println("    \"arcos\": [");
				for (Arco a : arcos) {
					out.println("        {");
					out.println("        \"idOrigen\":"+a.darIdOrigen());
					out.println("        \"idDestino\":"+a.darIdDestino());
					out.println("        \"pesoHaversine\":"+a.darPesoHaversine());
					out.println("        \"pesoComparendos\":"+a.darPesoComparendos());
					aActual++;
					imprimiAlgo = true;
					if(aActual < arcos.length) {
						out.println("        },");
					}
					else {
						if(imprimiAlgo) {
							out.println("        }");
							imprimiAlgo = false;
						}
						out.println("    ]");
					}
				}
				Iterator<Estacion> estaciones = v.estaciones();
				Estacion e = estaciones.next();
				out.println("    \"estaciones\": [");
				while(estaciones.hasNext()) {
					out.println("        {");
					out.println("        \"objectId\":"+e.getObjectId());
					out.println("        \"descripcion\":"+e.getDescripcion());
					out.println("        \"direccion\":"+e.getDireccion());
					out.println("        \"nombre\":"+e.getNombre());
					imprimiAlgo = true;
					if(estaciones.hasNext()) {
						out.println("        },");
					}
					e = estaciones.next();
				}
				if(imprimiAlgo) {
					out.println("        }");
					imprimiAlgo = false;
				}
				out.println("    ]");

				Iterator<Infraccion> infracciones = v.infracciones();
				Infraccion i = infracciones.next();
				out.println("    \"infracciones\": [");
				while(infracciones.hasNext()) {
					out.println("        {");
					out.println("        \"objectId\":"+i.getObjectId());
					out.println("        \"fechaHora\":"+i.getFechaHora());
					out.println("        \"medioDetec\":"+i.getMedioDetec());
					out.println("        \"claseVehi\":"+i.getClaseVehi());
					out.println("        \"tipoServi\":"+i.getTipoServi());
					out.println("        \"infraccion\":"+i.getInfraccion());
					out.println("        \"descInfrac\":"+i.getDescInfrac());
					out.println("        \"localidad\":"+i.getLocalidad());
					imprimiAlgo = true;
					if(infracciones.hasNext()) {
						out.println("        },");
					}
					i = infracciones.next();
				}
				if(imprimiAlgo) {
					out.println("        }");
				}
				out.println("    ]");		

				vActual++;
				if(vActual < vertices.length) {
					out.println("    },");
				}
				else {
					out.println("    }");
				}
			}
			out.println("}");
			out.close( );
		}
		catch(Exception e) {
			System.out.println("Hubo un error al hacer el archivo. F");
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Retorna el id del nodo m�s cercano basado en su distancia Haversine
	 */
	public int inicio1(double lat, double lon) {
		Double rangoarr = 0.00001;
		Iterable<Vertice> vertices = null;
		while(true) {
			vertices = arbol.valuesInRange(lat - rangoarr, lat + rangoarr);
			if(!(vertices.iterator().hasNext())) {
				rangoarr*=2;
				continue;
			}
			else {
				break;
			}
		}
		RedBlackBST<Double, Vertice> arbol2 = new RedBlackBST<Double, Vertice>();
		for(Vertice v: vertices) {
			arbol2.put(v.darLongitud(), v);
		}
		Double rango = 0.000001;
		while(true) {
			Iterator<Vertice> noc = arbol2.valuesInRange(lon - rango, lon - rango).iterator();
			while(noc.hasNext()) {
				return noc.next().darId();
			}
			rango *= 2;
		}
	}
	/**
  	Obtener el camino de costo m�nimo entre dos ubicaciones geogr�ficas por distancia

  	Muestre en la consola de texto el camino a seguir informando:
	el total de v�rtices, sus v�rtices (Id, latitud, longitud), el costo m�nimo (menor distancia
	haversiana) y la distancia estimada (sumatoria de distancias harvesianas en Km).

	Muestre el camino resultante en Google Maps (incluyendo la
	ubicaci�n de inicio y la ubicaci�n de destino).
	 */
	public String a1(double latIni, double lonIni, double latFin, double lonFin) {
		return "";
	}

	/**
	 Determinar la red de comunicaciones que soporte la instalaci�n de c�maras de video
	en los M puntos donde se presentan los comparendos de mayor gravedad.

	Muestre en la consola de texto el tiempo que toma el algoritmo
	en encontrar la soluci�n, y la siguiente informaci�n de la red propuesta: los v�rtices
	(identificadores) y los arcos incluidos, y el costo (monetario) total.

	Muestre en un mapa en Google Maps la red de comunicaciones
	propuesta. Resalte las M ubicaciones de las c�maras y los arcos de la red que las unen. 
	 */
	public String a2(int m) {
		return "";
	}


}
