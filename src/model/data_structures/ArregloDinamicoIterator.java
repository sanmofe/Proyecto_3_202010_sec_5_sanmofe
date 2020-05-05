package model.data_structures;

import java.util.Iterator;

public class ArregloDinamicoIterator<T> implements Iterator<T> {
	private int actual;
	
	private int maxSize;
	
	private T[] elem;
	
	public ArregloDinamicoIterator(T[] elem, int maxSize) {
		// TODO Auto-generated constructor stub
		this.elem = elem;
		this.actual = -1;
		this.maxSize = maxSize;
	}

	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return actual < maxSize-1;
	}

	@Override
	public T next() {
		// TODO Auto-generated method stub
		actual += 1;
		return elem[actual];
	}
}