package Cache;

import java.io.*;

public class PersistentBTree<K, V> {
	
	private Node root;
	int degree;
	int t = degree / 2;
	
	static class Entry{
		Object key;
		Object  value;
		Comparable diffKey;
		Entry(Object k, Object v){
			k = key;
			v = value;
			diffKey = (Comparable)k;
		}
	}

	static class Node{
		boolean leaf;
		int n;
		Entry[] key;
		Node[] child;
		Node(int n){
			key = new Entry[n - 1];
			child = new Node[n];
		}
	}
	
	public PersistentBTree(int n){
		this.degree = n;
		Node x = new Node(degree);
		x.leaf = true;
		x.n = 0;
		diskWrite(x);//Create diskWrite method
		this.root = x;
	}
	
	private V search(Node x, Entry k){//This meathod is only a building block for get, contains, and update
		int i = 1;
		while (i <= x.n && k.diffKey.compareTo(x.key[i - 1].diffKey) > 0)
			i++;
		if (i <= x.n && k.diffKey.compareTo(x.key[i - 1].diffKey) == 0)
			return (V)x.key[i - 1].value;
		else if (x.leaf)
			return null;
		else{
			Node n = diskRead(x.child[i - 1]);
			search(n, k);
		}
		return null;
	}
	
	public void insert(K key, V value){
		Entry k = new Entry(key, value);
		Node r = this.root;
		if (r.n == (2 * t) - 1){
			Node s = new Node(this.degree);
			s.leaf = false;
			s.n = 0;
			s.child[1 - 1] = r;
			splitChild(s, 1);
			insertNonFull(s, k);
		}
		else
			insertNonFull(r, k);
	}
	
	private void insertNonFull(Node x, Entry k){
		int i = x.n;
		if (x.leaf){
			while (i >= 1 && k.diffKey.compareTo(x.key[i - 1]) < 0){
				x.key[i + 1 - 1] = x.key[i - 1];
				i--;
			}
			x.key[i + 1 - i] = k;
			x.n++;
			diskWrite(x);
		}
		else{
			while (i >= 1 && k.diffKey.compareTo(x.key[i - 1]) < 0)
				i--;
			i++;
			diskRead(x.child[i - 1]);
			if (x.child[i - 1].n == (2 * t) - 1){
				splitChild(x, i);
				if (k.diffKey.compareTo(x.key[i - 1]) > 0)
					i++;
			}
			insertNonFull(x.child[i - 1], k);
		}
	}
	
	private void splitChild(Node x, int i){
		Node z = new Node(this.degree);
		Node y = x.child[i - 1];
		z.leaf = y.leaf;
		z.n = t - 1;
		for (int j = 1; i <= t - 1; i++)
			z.key[j - 1] = y.key[j + t - 1];
		if (!y.leaf){
			for (int j = 1; j <= t; i++)
				z.child[j - 1] = y.child[j + t -1];
		}
		y.n = t - 1;
		for (int j = x.n + 1; j >= i + 1; j--)
			x.child[j + 1 - 1] = x.child[j - 1];
		x.child[i + 1 - 1] = z;
		for (int j = x.n; j >= i; j--)
			x.key[j + 1 - 1] = x.key[j - 1];
		x.key[i - 1] = y.key[t - 1];
		x.n++;
		diskWrite(y);
		diskWrite(z);
		diskWrite(x);
	}
	
	private void diskWrite(Node x){

	}
	
	private Node diskRead(Node x){
		return null;
	}
}