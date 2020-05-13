package model.data_structures;

import java.util.Comparator;
import java.util.Iterator;
/**
 * Esta clase se basó de la implementación del libro 
 */
public class MaxHeapCP<T extends Comparable<T>> {
	
	private T[] pq;
    private int n;
    private Comparator<T> comparator; 
	public MaxHeapCP(int capacidad) {
		pq = (T[]) new Comparable[capacidad + 1];
        n = 0;
	}
	
    public MaxHeapCP(int initCapacity, Comparator<T> comparator) {
        this.comparator = comparator;
        pq = (T[]) new Comparable[initCapacity + 1];
        n = 0;
    }
    
    public MaxHeapCP(Comparator<T> comparator) {
        this(1, comparator);
    }
    
	public MaxHeapCP() {
		this(1);
	}
	
	public int darNumElementos() {
		return n;
	}
	
	public void agregar(T elemento) {
		if (n == pq.length - 1) resize(2 * pq.length);
        pq[++n] = elemento;
        swim(n);
	}
	
	public T sacarMax() {
		if (esVacia()) return null;
        T max = pq[1];
        exch(1, n--);
        sink(1);
        pq[n+1] = null;     
        if ((n > 0) && (n == (pq.length - 1) / 4)) resize(pq.length / 2);
        return max;
	}
	
	private void swim(int k) {
        while (k > 1 && less(k/2, k)) {
            exch(k, k/2);
            k = k/2;
        }
    }
	
	private void sink(int k) {
        while (2*k <= n) {
            int j = 2*k;
            if (j < n && less(j, j+1)) j++;
            if (!less(k, j)) break;
            exch(k, j);
            k = j;
        }
    }
	
	private boolean less(int i, int j) {
        if (comparator == null) {
            return ((Comparable<T>) pq[i]).compareTo(pq[j]) < 0;
        }
        else {
            return comparator.compare(pq[i], pq[j]) < 0;
        }
    }
	
	private void exch(int i, int j) {
        T swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
    }
	public T darMax() {
		return pq[1];
	}
	
	public boolean esVacia() {
		return n == 0;
	}
	
	private void resize(int capacity) {
        assert capacity > n;
        T[] temp = (T[]) new Comparable[capacity];
        for (int i = 1; i <= n; i++) {
            temp[i] = pq[i];
        }
        pq = temp;
    }
	
	public Iterator<T> iterator() {
        return new HeapIterator();
    }

    private class HeapIterator implements Iterator<T> {
        private MaxHeapCP<T> copy;
        public HeapIterator() {
            if (comparator == null) copy = new MaxHeapCP<T>(darNumElementos());
            else                    copy = new MaxHeapCP<T>(darNumElementos(), comparator);
            for (int i = 1; i <= n; i++)
                copy.agregar(pq[i]);
        }
        public boolean hasNext()  { return !copy.esVacia();                     }
        public T next() {
            if (!hasNext()) { System.out.println("Brace yourselves. null is coming"); return null;}
            return copy.sacarMax();
        }
    }
}
