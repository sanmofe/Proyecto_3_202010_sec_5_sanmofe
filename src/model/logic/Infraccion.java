package model.logic;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Infraccion implements Comparable<Infraccion> {
	private int objectId;
	private Date fechaHora;
	private String medioDetec;
	private String claseVehi;
	private String tipoServi;
	private String infraccion;
	private String descInfrac;
	private Coordenada coordenada;
	private String localidad;

	public Infraccion() {
		// TODO Auto-generated constructor stub
	}

	public Infraccion(int objectId, String fechaHora, String claseVehi, String tipoServi, String infraccion,
			String descInfrac, Coordenada coordenada, String localidad) {
		super();
		this.objectId = objectId;
		SimpleDateFormat date =new SimpleDateFormat ("yyyy/MM/dd'T'HH:mm:ss.SSS'Z'");
		try {
			this.fechaHora = date.parse(fechaHora);
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			// TODO: handle exception
		}
		this.claseVehi = claseVehi;
		this.tipoServi = tipoServi;
		this.infraccion = infraccion;
		this.descInfrac = descInfrac;
		this.coordenada = coordenada;
		this.localidad = localidad;
	}

	public Infraccion(JsonObject ob) {
		JsonObject obj = ob.getAsJsonObject("properties");
		this.objectId = Integer.parseInt(obj.get("OBJECTID").getAsString());
		String x = obj.get("FECHA_HORA").getAsString();
		DateFormat date =new SimpleDateFormat ("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		try {
			this.fechaHora = date.parse(x);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			// TODO: handle exception
		}
		this.setMedioDetec(obj.get("MEDIO_DETECCION").getAsString());
		this.claseVehi = obj.get("CLASE_VEHICULO").getAsString();
		this.tipoServi = obj.get("TIPO_SERVICIO").getAsString();
		this.infraccion = obj.get("INFRACCION").getAsString();
		this.descInfrac = obj.get("DES_INFRACCION").getAsString();
		this.localidad = obj.get("LOCALIDAD").getAsString();
		JsonObject je = ob.getAsJsonObject("geometry");
		JsonArray ja = je.getAsJsonArray("coordinates");
		this.coordenada = new Coordenada(ja.get(0).getAsDouble(), ja.get(1).getAsDouble());
		//this.localidad = obj.get("OBJECTID").getAsString();
	}

	public int getObjectId() {
		return objectId;
	}

	public Date getFechaHora() {
		return fechaHora;
	}

	public String getClaseVehi() {
		return claseVehi;
	}

	public String getTipoServi() {
		return tipoServi;
	}

	public String getInfraccion() {
		return infraccion;
	}

	public String getDescInfrac() {
		return descInfrac;
	}

	public Coordenada getCoordenada() {
		return coordenada;
	}

	public String getLocalidad() {
		return this.localidad;
	}

	public void setObjectId(String objectId) {
		this.objectId = Integer.parseInt(objectId);
	}

	public void setFechaHora(String fechaHora) {
		SimpleDateFormat date =new SimpleDateFormat ("yyyy/MM/dd'T'HH:mm:ss.SSS'Z'");
		try {
			this.fechaHora = date.parse(fechaHora);
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			// TODO: handle exception
		}
	}

	public void setClaseVehi(String claseVehi) {
		this.claseVehi = claseVehi;
	}

	public void setTipoServi(String tipoServi) {
		this.tipoServi = tipoServi;
	}

	public void setInfraccion(String infraccion) {
		this.infraccion = infraccion;
	}

	public void setDescInfrac(String descInfrac) {
		this.descInfrac = descInfrac;
	}

	public void setCoordenada(Coordenada coordenada) {
		this.coordenada = coordenada;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	@Override
	public String toString() {
		String info = "\n Infraccion: "+ infraccion
				+ "\n OBJECTID: " + objectId
				+ "\n Fecha  y Hora: " + fechaHora
				+ "\n Clase de Vehículo: "+ claseVehi
				+ "\n Tipo servicio: " + tipoServi
				//+"\n Descripcion infraccion: "+ descInfrac
				//+"\n Localidad"+
				+"\n Localidad: "+localidad;

		// +"\n Coordenadas: "+coordenada.getLatitude()+","+coordenada.getLongitude();

		// TODO Auto-generated method stub
		return info;
	}

	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		return ((arg0 instanceof Infraccion) && ((Infraccion)arg0).objectId == this.objectId); 
	}

	@Override
	public int compareTo(Infraccion arg0) {
		// TODO Auto-generated method stub
		//Latitud mayor => Más al norte                   Creo
		return (int) Math.round(coordenada.getLatitude() - arg0.getCoordenada().getLatitude());
		//En teoría retorna positivo si esta infracción ocurrió más al norte...
		//...En teoría
	}

	public String getMedioDetec() {
		return medioDetec;
	}

	public void setMedioDetec(String medioDetec) {
		this.medioDetec = medioDetec;
	}


}
