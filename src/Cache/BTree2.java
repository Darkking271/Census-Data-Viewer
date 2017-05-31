package Cache;

import java.util.LinkedHashSet;
import java.util.Set;

public class BTree2<K, V> {
	
	private int n;
	private int count;
	private Node root;
	private Set<K> set;

	static class Entry{
		Object key;
		Object value;
		Comparable diffKey;
		Entry(Object k, Object v){
			this.key = k;
			this.value = v;
			diffKey = (Comparable)key;
		}
	}//END ENTRY
	
	static class Node{
		Entry[] key;
		Node[] child;
		Node parent;
		
		Node(int k){
			key = new Entry[k - 1];
			child = new Node[k];
		}	
		
		private int keySize(){
			int count = 0;
			for (int i = 0; i < key.length; i++){
				if (key[i] != null)
					count++;
			}
			return count;
		}
		
		private int nodeSize(){
			int count = 0;
			for (int i = 0; i < child.length; i++){
				if (child[i] != null)
					count++;
			}
			return count;
		}
	}//ENTRY NODE
	
	public BTree2(int size){
		this.n = size;
		this.count = 0;
		this.root = new Node(n);
		root.parent = null;
	}
	
	public void put(K key, V value){
		if (contains(key)){
			insert(key, value);
		}
		else
			update(key, value);
	}
	
	private void insert(K key, V value){
		Node s = root;
		Entry e = new Entry(key, value);
		while (s.nodeSize() != 0){
			for (int i = 0; i < s.keySize(); i++){
				if (e.diffKey.compareTo(s.key[i].diffKey) < 0){
					s = s.child[i];
					break;
				}
				else if (s.key[i + 1] != null){
					if (e.diffKey.compareTo(s.key[i].diffKey) > 0 
							&& e.diffKey.compareTo(s.key[i + 1].diffKey) < 0){
						s = s.child[i + 1];
						break;
					}
				}
				else{
					if (e.diffKey.compareTo(s.key[i].diffKey) > 0){
						s = s.child[i + 1];
						break;
					}
				}
			}
		}//END SEARCH
		
		if (s.keySize() == 0){
			s.key[0] = e;
			this.count++;
		}
		else if (s.keySize() < n){
			int place = findPlace(s.key, e.diffKey, s.keySize());
			if (place == s.keySize()){
				s.key[place] = e;
				this.count++;
			}
			else{
				for (int i = s.keySize() - 1; i >= place; i--)
					s.key[i + 1] = s.key[i];
				s.key[place] = e;
				this.count++;
			}
		}
		else split(s, e);
	}//END INSERT
	
	private void split(Node s, Entry e){
		int split = n / 2;
		for (;;){
			if (s.parent != null){//SPLIT CHILD
				
			}
			else if (s.parent == null){//SPLIT ROOT
				
			}
		}
	}
	
	private int findPlace(Entry[] list, Comparable key, int count) {
		for (int i = 0; i < count; i++){
			if (key.compareTo(list[i].diffKey) < 0){
				return i;
			}
			else if (list[i + 1] != null){
				if(key.compareTo(list[i].diffKey) > 0 && key.compareTo(list[i + 1].diffKey) < 0){
					return i + 1;
				}
			}
		}
		return count;
	}
	
	public boolean contains(K key){
		Node s = root;
		Comparable k = (Comparable)key;
		for (;;){
			for (int i = 0; i < s.keySize(); i++){
				if (k.compareTo(s.key[i].diffKey) == 0)
					return true;
			}
			
			if (s.nodeSize() != 0){
				for (int i = 0; i < s.keySize(); i++){
					if (k.compareTo(s.key[i].diffKey) < 0){
						s = s.child[i];
						break;
					}
					else if (s.key[i + 1] != null){
						if (k.compareTo(s.key[i].diffKey) > 0 
								&& k.compareTo(s.key[i + 1].diffKey) < 0){
							s = s.child[i + 1];
							break;
						}
					}
					else if (k.compareTo(s.key[i].diffKey) > 0){
						s = s.child[i + 1];
						break;
					}
				}
			}
			else break;
		}
		return false;
	}//END CONTAINS
	
	public void update(K key, V value){
		Node s = root;
		Comparable k = (Comparable)key;
		for (;;){
			for (int i = 0; i < s.keySize(); i++){
				if (k.compareTo(s.key[i].diffKey) == 0)
					s.key[i].value = value;
			}
			
			if (s.nodeSize() != 0){
				for (int i = 0; i < s.keySize(); i++){
					if (k.compareTo(s.key[i].diffKey) < 0){
						s = s.child[i];
					}
					else if (s.key[i + 1] != null){
						if (k.compareTo(s.key[i].diffKey) > 0
								&& k.compareTo(s.key[i + 1]) < 0){
							s = s.child[i + 1];
							break;
						}
					}
					else if (k.compareTo(s.key[i].diffKey) > 0){
						s = s.child[i + 1];
						break;
					}
				}
			}
			else break;
		}
	}//END UPDATE
	
}
