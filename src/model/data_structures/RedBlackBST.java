package model.data_structures;

import java.util.Arrays;
import java.util.Iterator;



/**
 *    Este código está basado en otras implementaciones, incluída la del libro 
 *                               del curso.
 *                                 Salu2. 
 */
public class RedBlackBST<K extends Comparable<K>, V> {

	private static final boolean ROJO = true;
	private static final boolean NEGRO = false;

	private Nodo raiz;
	private class Nodo{

		protected K key;
		protected V val;
		protected Nodo izq;
		protected Nodo der;
		protected boolean color;
		protected int size;

		public Nodo(K k, V v, boolean c, int s) {
			key = k;
			val = v;
			color = c;
			size = s;
		}
	}
	public RedBlackBST() {
		//Aparentemente esto se puede dejar vacío xd
	}

	private boolean isRed(Nodo x) {
		if (x != null) {
			return x.color == ROJO;
		}
		return false;
	}

	private int size(Nodo x) {
		if(x != null) {
			return x.size;
		}
		return 0;	
	}

	public int size() {
		return size(raiz);
	}

	public boolean isEmpty() {
		return (raiz == null);
	}

	public V get(K key) {
		return key == null? null : get(raiz, key) ;
	}

	private V get(Nodo n, K k) {
		if(n == null) {
			return null;
		}
		int comparados = k.compareTo(n.key);
		if(comparados < 0) {
			return get(n.izq, k);
		}
		else if(comparados > 0) {
			return get(n.der, k);
		}
		else {
			return n.val;
		}
	}

	public int getHeight(K key) {
		return heightBetween(raiz.key, key);		
	}

	private int rank(K key) {
		return rank(raiz, key);
	}

	private int rank(Nodo n, K key) {
		if (n == null) {
			return 0;
		}
		int compare = key.compareTo(n.key);
		if (compare < 0) {
			return rank(n.izq, key);
		} else if (compare > 0) {
			return size(n.izq) + 1 + rank(n.der, key);
		} else {
			return size(n.izq);
		}
	}

	private int heightBetween(K lo,K hi) {
		if (lo == null) throw new IllegalArgumentException("first argument to size() is null");
		if (hi == null) throw new IllegalArgumentException("second argument to size() is null");

		if (lo.compareTo(hi) > 0) return 0;
		if (contains(hi)) return rank(hi) - rank(lo) + 1;
		else              return rank(hi) - rank(lo);
	}

	private int height(Nodo nodo) {
		if(nodo != null) {
			return 1 + Math.max(height(nodo.izq), height(nodo.der));
		}
		return -1;
	}

	private int blackHeight(Nodo nodo)
	{	if(nodo != null) {
		if(nodo.color==NEGRO)
		{
		return 1 + Math.max(blackHeight(nodo.izq), blackHeight(nodo.der));
		}
		else
		{
			return Math.max(blackHeight(nodo.izq), blackHeight(nodo.der));
		}
	}
	return -1;
	}
	public boolean contains(K key) {
		return get(key) != null;
	}

	public void put(K key, V val) {
		if (key == null) throw new IllegalArgumentException("first argument to put() is null");
		if (val == null) {
			delete(key);
			return;
		}

		raiz = put(raiz, key, val);
		raiz.color = NEGRO;
	}

	public void delete(K key) { 
		if (key == null) throw new IllegalArgumentException("argument to delete() is null");
		if (!contains(key)) return;

		// if both children of root are black, set root to red
		if (!isRed(raiz.izq) && !isRed(raiz.der))
			raiz.color = ROJO;
		// if both children of raiz are black, set raiz to red
		if (!isRed(raiz.izq) && !isRed(raiz.der))
			raiz.color = ROJO;

		raiz = delete(raiz, key);
		if (!isEmpty()) raiz.color = NEGRO;
		// assert check();
	}

	// delete the key-value pair with the given key raized at h
	private Nodo delete(Nodo h, K key) { 
		// assert get(h, key) != null;

		if (key.compareTo(h.key) < 0)  {
			if (!isRed(h.izq) && !isRed(h.izq.izq))
				h = moveRedLeft(h);
			h.izq = delete(h.izq, key);
		}
		else {
			if (isRed(h.izq))
				h = rotateRight(h);
			if (key.compareTo(h.key) == 0 && (h.der == null))
				return null;
			if (!isRed(h.der) && !isRed(h.der.izq))
				h = moveRedRight(h);
			if (key.compareTo(h.key) == 0) {
				Nodo x = min(h.der);
				h.key = x.key;
				h.val = x.val;
				h.der = deleteMin(h.der);
			}
			else h.der = delete(h.der, key);
		}
		return balance(h);
	}


	public void deleteMin() {
		if (isEmpty()) return;
		if (!isRed(raiz.izq) && !isRed(raiz.der))
			raiz.color = ROJO;

		raiz = deleteMin(raiz);
		if (!isEmpty()) raiz.color = NEGRO;
	}

	private Nodo deleteMin(Nodo h) { 
		if (h.izq == null)
			return null;

		if (!isRed(h.izq) && !isRed(h.izq.izq))
			h = moveRedLeft(h);

		h.izq = deleteMin(h.izq);
		return balance(h);
	}

	private Nodo moveRedLeft(Nodo h) {
		flipColors(h);
		if (isRed(h.der.izq)) { 
			h.der = rotateRight(h.der);
			h = rotateLeft(h);
			flipColors(h);
		}
		return h;
	}

	private Nodo moveRedRight(Nodo h) {
		flipColors(h);
		if (isRed(h.izq.izq)) { 
			h = rotateRight(h);
			flipColors(h);
		}
		return h;
	}

	private Nodo balance(Nodo h) {
		if (isRed(h.der))                      h = rotateLeft(h);
		if (isRed(h.izq) && isRed(h.izq.izq)) h = rotateRight(h);
		if (isRed(h.izq) && isRed(h.der))     flipColors(h);
		h.size = size(h.izq) + size(h.der) + 1;
		return h;
	}


	private Nodo put(Nodo h, K key, V val) { 
		if (h == null) return new Nodo(key, val, ROJO, 1);

		int cmp = key.compareTo(h.key);
		if      (cmp < 0) h.izq  = put(h.izq,  key, val); 
		else if (cmp > 0) h.der = put(h.der, key, val); 
		else              h.val   = val;
		if (isRed(h.der) && !isRed(h.izq))      h = rotateLeft(h);
		if (isRed(h.izq)  &&  isRed(h.izq.izq)) h = rotateRight(h);
		if (isRed(h.izq)  &&  isRed(h.der))     flipColors(h);
		h.size = size(h.izq) + size(h.der) + 1;

		return h;
	}

	private Nodo rotateRight(Nodo h) {
		Nodo x = h.izq;
		h.izq = x.der;
		x.der = h;
		x.color = x.der.color;
		x.der.color = ROJO;
		x.size = h.size;
		h.size = size(h.izq) + size(h.der) + 1;
		return x;
	}

	private Nodo rotateLeft(Nodo h) {
		Nodo x = h.der;
		h.der = x.izq;
		x.izq = h;
		x.color = x.izq.color;
		x.izq.color = ROJO;
		x.size = h.size;
		h.size = size(h.izq) + size(h.der) + 1;
		return x;
	}

	private void flipColors(Nodo h) {
		h.color = !h.color;
		h.izq.color = !h.izq.color;
		h.der.color = !h.der.color;
	}

	public int height() {
		return height(raiz);
	}
public int blackHeight()
{
	return blackHeight(raiz);
}
	public K min() {
		if(raiz != null) {
			return min(raiz).key;
		}
		return null;	
	}

	private Nodo min(Nodo n) {
		if(n.izq == null) return n;
		else return min(n.izq);
	}

	public K max() {
		if(raiz != null) {
			return max(raiz).key;
		}
		return null;
	}

	private Nodo max(Nodo n) {
		if(n.der == null) return n;
		else return max(n.der);
	}


	public Iterator<K> keys(){
		if (isEmpty()) return Arrays.asList((K[])new Object[0]).iterator();
		return keysInRange(min(), max());
	}

	public Iterable<V> valuesInRange(K lo, K hi) {
        if (lo == null) throw new IllegalArgumentException("first argument to keys() is null");
        if (hi == null) throw new IllegalArgumentException("second argument to keys() is null");
        ArregloDinamico<V> arreglo = new ArregloDinamico<>(1);
        keysToValues(raiz, arreglo, lo, hi);
        return arreglo;
    } 
	
    private void keysToValues(Nodo x, ArregloDinamico<V> arreglo, K lo, K hi) { 
        if (x == null) return; 
        int cmplo = lo.compareTo(x.key); 
        int cmphi = hi.compareTo(x.key); 
        if (cmplo < 0) keysToValues(x.izq, arreglo, lo, hi); 
        if (cmplo <= 0 && cmphi >= 0) arreglo.agregar(x.val); 
        if (cmphi > 0) keysToValues(x.der, arreglo, lo, hi); 
    } 


	public Iterator<K> keysInRange(K init, K end){
		if (init == null) throw new IllegalArgumentException("first argument to keys() is null");
		if (end == null) throw new IllegalArgumentException("second argument to keys() is null");
		K[] arreglo = (K[]) new Object[size()];
		//TODO ESTO ES UN ERROR AAAAAAAAAAAAAAAAAAAAAAAA 
		return null;
	}

	/*************************
	 *  Check integrity of red-black tree data structure.
	 *************************/
	private boolean check() {
		if (!isBST())            System.out.println("Not in symmetric order");
		if (!isSizeConsistent()) System.out.println("Subtree counts not consistent");
		if (!isRankConsistent()) System.out.println("Ranks not consistent");
		if (!is23())             System.out.println("Not a 2-3 tree");
		if (!isBalanced())       System.out.println("Not balanced");
		return isBST() && isSizeConsistent() && isRankConsistent() && is23() && isBalanced();
	}

	// does this binary tree satisfy symmetric order?
	// Note: this test also ensures that data structure is a binary tree since order is strict
	private boolean isBST() {
		return isBST(raiz, null, null);
	}

	// is the tree rooted at x a BST with all keys strictly between min and max
	// (if min or max is null, treat as empty constraint)
	// Credit: Bob Dondero's elegant solution
	private boolean isBST(Nodo x, K min, K max) {
		if (x == null) return true;
		if (min != null && x.key.compareTo(min) <= 0) return false;
		if (max != null && x.key.compareTo(max) >= 0) return false;
		return isBST(x.izq, min, x.key) && isBST(x.der, x.key, max);
	} 

	// are the size fields correct?
	private boolean isSizeConsistent() { return isSizeConsistent(raiz); }
	private boolean isSizeConsistent(Nodo x) {
		if (x == null) return true;
		if (x.size != size(x.izq) + size(x.der) + 1) return false;
		return isSizeConsistent(x.izq) && isSizeConsistent(x.der);
	} 

	// check that ranks are consistent
	private boolean isRankConsistent() {
		for (int i = 0; i < size(); i++)
			if (i != rank(select(i))) return false;
		Iterator<K> iter = keys();
		while(iter.hasNext()) {
			K key = iter.next();
			if (key.compareTo(select(rank(key))) != 0) return false;
		}
		return true;
	}
	/**
	 * Return the key in the symbol table whose rank is {@code k}.
	 * This is the (k+1)st smallest key in the symbol table. 
	 *
	 * @param  k the order statistic
	 * @return the key in the symbol table of rank {@code k}
	 * @throws IllegalArgumentException unless {@code k} is between 0 and
	 *        <em>n</em>�1
	 */
	public K select(int k) {
		if (k < 0 || k >= size()) {
			throw new IllegalArgumentException("argument to select() is invalid: " + k);
		}
		Nodo x = select(raiz, k);
		return x.key;
	}

	// the key of rank k in the subtree rooted at x
	private Nodo select(Nodo x, int k) {
		// assert x != null;
		// assert k >= 0 && k < size(x);
		int t = size(x.izq); 
		if      (t > k) return select(x.izq,  k); 
		else if (t < k) return select(x.der, k-t-1); 
		else            return x; 
	} 
	// Does the tree have no red right links, and at most one (left)
	// red links in a row on any path?
	private boolean is23() { return is23(raiz); }
	private boolean is23(Nodo x) {
		if (x == null) return true;
		if (isRed(x.der)) return false;
		if (x != raiz && isRed(x) && isRed(x.izq))
			return false;
		return is23(x.izq) && is23(x.der);
	} 

	// do all paths from root to leaf have same number of black edges?
	private boolean isBalanced() { 
		int black = 0;     // number of black links on path from root to min
		Nodo x = raiz;
		while (x != null) {
			if (!isRed(x)) black++;
			x = x.izq;
		}
		return isBalanced(raiz, black);
	}

	// does every path from the root to a leaf have the given number of black links?
	private boolean isBalanced(Nodo x, int black) {
		if (x == null) return black == 0;
		if (!isRed(x)) black--;
		return isBalanced(x.izq, black) && isBalanced(x.der, black);
	} 


	/*************************
	 *  
	 *************************/


}