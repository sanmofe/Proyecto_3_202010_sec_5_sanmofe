package model.logic;

import com.google.gson.JsonObject;


public class Estacion {

private Coordenada coordenada;	
private int objectId;
private String descripcion;
private String direccion;
public String nombre;

	public Estacion(JsonObject ob) {
		JsonObject obj = ob.getAsJsonObject("properties");
		this.objectId = Integer.parseInt(obj.get("OBJECTID").getAsString());
		this.descripcion = obj.get("EPODESCRIP").getAsString();
		this.direccion = obj.get("EPODIR_SITIO").getAsString();
		Double latitude = obj.get("EPOLATITUD").getAsDouble();
		Double longitude = obj.get("EPOLONGITU").getAsDouble();
		this.coordenada = new Coordenada(latitude, longitude);
		this.nombre = obj.get("EPONOMBRE").getAsString();
	}

}
