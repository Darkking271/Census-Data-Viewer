package Cache;

import java.util.LinkedHashSet;
import java.util.Set;
import java.io.*;

public class BTree<K> {
	
	private int n;
	private int count;
	private Node root;
	private Set<K> set;
	private RandomAccessFile raf;
	private String rootLoc;
	private String rafLoc;
	
	static class Entry implements Serializable{
		Object key;
		long pointer;
		Comparable diffKey;
		Entry(Object k, long pointer){
			this.key = k;
			this.pointer = pointer;
			diffKey = (Comparable)key;
		}
	}//END ENTRY
	
	static class Node implements Serializable{
		Entry[] key;
		Node[] child;
		Node parent;
		int rootCount;
		int degree;
		Node(int k){
			key = new Entry[k];
			child = new Node[k + 1];
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
	}//END NODE
	
	public BTree(int size, String rootLoc, String rafLoc){
		n = size;
		this.rootLoc = rootLoc;
		this.rafLoc = rafLoc;
		this.count = 0;
		root = new Node(n);
		root.parent = null;
		try {
			raf = new RandomAccessFile(rafLoc, "rw");
		}catch(IOException e){e.printStackTrace();}
	}//END CONSTRUCTOR

	public BTree(String rootLoc, String vLoc){
		root = null;
		try{
		    this.rootLoc = rootLoc;
		    this.rafLoc = vLoc;
			FileInputStream fileIn =
					new FileInputStream(this.rootLoc);
			ObjectInputStream in =
					new ObjectInputStream(fileIn);
			root = (Node)in.readObject();
			in.close();
			fileIn.close();
			this.n = root.degree;
			this.count = root.rootCount;
			raf = new RandomAccessFile(rafLoc, "rw");
		}catch(IOException i){i.printStackTrace();}
		catch(ClassNotFoundException c){c.printStackTrace();}
	}
	
	public void put(K key, int value)throws IOException{
		if (!containsKey(key)) {
			long pos = raf.getFilePointer();
			raf.seek(pos);
			raf.writeInt(value);
			Entry e = new Entry(key, pos);
			insert(e);
		}
		else updateKey(key, value);
	}//END put

	private void insert(Entry e){
		Node s = root;
		while (s.nodeSize() != 0){
			for (int i = 0; i < s.keySize(); i++){
				if (e.diffKey.compareTo(s.key[i].diffKey) < 0){
					s = s.child[i];
					break;
				}
				else if (s.key[i + 1] != null){
					if (e.diffKey.compareTo(s.key[i].diffKey) > 0 && e.diffKey.compareTo(s.key[i + 1].diffKey) < 0){
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
		}
		if (s.keySize() == 0){
			s.key[0] = e;
			this.count++;
		}
		else{
			int place = findPlace(s.key, e.diffKey, s.keySize());
			if (place == s.keySize()){
				s.key[place] = e;
				count++;
			}
			else if (place < s.keySize()){
				for (int i = s.keySize() - 1; i >= place; i--){
					s.key[i + 1] = s.key[i];
				}
				s.key[place] = e;
				count++;
			}
			if (s.keySize() == n){
				this.root = splitTable(s);

			}
		}
	}//END insert

	private Node splitTable(Node s){ 
		int split = n / 2;
		for (;;){
			if (s.parent != null){//Split Child
				Node a = new Node(n);
				Node b = new Node(n);
				Entry e = null;
				for (int i = 0; i < n; i++){
					if ( i < split){
						a.key[i] = s.key[i];
						a.child[i] = s.child[i];
					}
					else if (i == split){
						e = s.key[i];
						a.child[i] = s.child[i];
						b.child[0] = s.child[i + 1];
					}
					else if (i > split){
						b.key[i - split - 1] = s.key[i];
						b.child[i - split] = s.child[i + 1];
					}
				}
				s = s.parent;
				a.parent = s;
				b.parent = s;
				for (int i = 0; i < a.nodeSize(); i++){
					if (a.child[i] != null)
						a.child[i].parent = a;
				}
				for (int i = 0; i < b.nodeSize(); i++){
					if (b.child[i] != null)
						b.child[i].parent = b;
				}
				int place = findPlace(s.key, e.diffKey, s.keySize());
				
				if (place == s.keySize()){
					s.key[place] = e;
					s.child[place] = a;
					s.child[place + 1] = b;
				}
				else if (place < s.keySize()){
					for (int i = s.keySize() - 1; i >= place; i--){
						s.key[i + 1] = s.key[i];
						s.child[i + 2] = s.child[i + 1];
					}
					s.key[place] = e;
					s.child[place] = a;
					s.child[place + 1] = b;
					
				}
				if (s.keySize() < n)
					break;
			}
			else if (s.parent == null){//Split Root
				Node a = new Node(n);
				Node b = new Node(n);
				Node parent = new Node(n);
				parent.parent = null;
				for (int i = 0; i < n; i++){
					if (i < split){
						a.key[i] = s.key[i];
						a.child[i] = s.child[i];
					}
					else if (i == split){
						parent.key[0] = s.key[i];
						parent.child[0] = a;
						parent.child[1] = b;
						a.child[i] = s.child[i];
						b.child[0] = s.child[i + 1];
					}
					else if (i > split){
						b.key[i - split - 1] = s.key[i];
						b.child[i - split] = s.child[i + 1];
					}
				}//END FOR
				s = parent;
				a.parent = s;
				b.parent = s;
				for (int i = 0; i < a.nodeSize(); i++){
					if (a.child[i] != null)
						a.child[i].parent = a;
				}
				for (int i = 0; i < b.nodeSize(); i++){
					if (b.child[i] != null)
						b.child[i].parent = b;
				}
				return s;
			}
		}
		//Return to root
		for (;;){
			if (s.parent == null)
				return s;
			else
				s = s.parent;
		}
	}//END split

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

	public boolean containsKey(K key){
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
						if (k.compareTo(s.key[i].diffKey) > 0 && k.compareTo(s.key[i + 1].diffKey) < 0){
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
	}//END containsKey
	
	public void updateKey(K key, int value)throws IOException{
		Node s = root;
		Comparable k = (Comparable)key;
		for (;;){
			for (int i = 0; i < s.keySize(); i++){
				if (k.compareTo(s.key[i].diffKey) == 0) {
                    raf.seek(s.key[i].pointer);
                    raf.writeInt(value);
                    return;
                }
			}
			if (s.nodeSize() != 0){
				for (int i = 0; i < s.keySize(); i++){
					if (k.compareTo(s.key[i].diffKey) < 0){
						s = s.child[i];
						break;
					}
					else if (s.key[i + 1] != null){
						if (k.compareTo(s.key[i].diffKey) > 0 && k.compareTo(s.key[i + 1].diffKey) < 0){
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
	}//ED updateKey
	
	public int get(K key)throws IOException{
		Node s = root;
		Comparable k = (Comparable)key;
		for (;;){
			for (int i = 0; i < s.keySize(); i++){
				if (k.compareTo(s.key[i].diffKey) == 0) {
                    raf.seek(s.key[i].pointer);
                    return raf.readInt();
                }
			}
			if (s.nodeSize() != 0){
				for (int i = 0; i < s.keySize(); i++){
					if (k.compareTo(s.key[i].diffKey) < 0){
						s = s.child[i];
						break;
					}
					else if (s.key[i + 1] != null){
						if (k.compareTo(s.key[i].diffKey) > 0 && k.compareTo(s.key[i + 1].diffKey) < 0){
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
		return 0;
	}//END get

	public Set<K> keySet(){
		set = new LinkedHashSet<>();
		traverse(root);
		return set;
	}
	
	private void traverse(Node s){
		if (s.child[0] != null)
			traverse(s.child[0]);
		for (int i = 0; i < s.keySize(); i++){
			set.add((K)s.key[i].key);
			if (s.child[i + 1] != null)
				traverse(s.child[i + 1]);
		}
	}
	
	public int size(){
		return count;
	}//END getCount
	
	public boolean isEmpty(){
		if(count == 0)
			return true;
		return false;
	}

	public void save(){
		this.root.rootCount = this.count;
		this.root.degree = this.n;
		try{
			FileOutputStream fileOut =
					new FileOutputStream(this.rootLoc);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(this.root);
			out.close();
			fileOut.close();
		}catch(IOException e){e.printStackTrace();}
	}
	
}//END BTREE
