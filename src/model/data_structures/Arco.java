package model.data_structures;

public class Arco<K> {

	private int idV1;
	private int idV2;
	private double peso;
	private boolean marked;
	
	public Arco(int pIDOrigen, int pIDDestino, double pPeso, boolean pMarked) {
		idV1 = pIDOrigen;
		idV2 = pIDDestino;
		peso = pPeso;
		marked = pMarked;
	}
	
	public int other(int idV)
	{
		int other = -1;
		if(idV==idV1)
		{
			other= idV2;
		}
		else {
			other= idV1;
		}
		return other;
	}

	public void marcar()
	{
		marked = true;
	}
	public void desmarcar()
	{
		marked = false;
	}
	
	public boolean isMarked()
	{
		return marked;
	}
	public double darPeso() 
	{
		return peso;
	}

	public void setPeso(double peso) 
	{
		this.peso = peso;
	}

	public int darIdOrigen() 
	{
		return idV1;
	}
	public int darIdDestino()
	{
		return idV2;
	}
	
	
}