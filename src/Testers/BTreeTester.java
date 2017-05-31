package Testers;
import Cache.BTree;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import Cache.KMeans;

import java.io.ObjectOutputStream;
import java.util.*;

public class BTreeTester {

	public static void main(String[] args) throws IOException{
		/*BTree<Integer> tree = new BTree<>(32,
                System.getProperty("user.dir") + File.separator + "test" + File.separator + "root.bt",
                System.getProperty("user.dir") + File.separator + "test" + File.separator + "test.raf");
		/*tree.put(2, null);
		tree.put(3, null);
		tree.put(1, null);
		tree.put(7, null);
		tree.put(9, null);
		tree.put(9, 12345);
		tree.put(6, null);
		tree.put(8, null);
		tree.put(8, 14414);
		tree.put(10, null);
		tree.put(11, null);
		tree.put(4, null);
		tree.put(12, null);
		tree.put(5, null);
		tree.put(14, null);
		tree.put(15, null);
		tree.put(17, null);
		tree.put(16, null);
		tree.put(18, null);
		tree.put(19, null);
		tree.put(20, null);
		tree.put(13, null);
		tree.put(21, null);
		tree.put(22, null);
		tree.put(25, null);
		tree.put(24, null);
		tree.put(23, null);
		tree.put(26, null);
		tree.put(27, null);
		for (Integer i : tree.keySet())
			System.out.println(i + ": " + tree.get(i));
		if (tree.containsKey(2))
			System.out.println("2 is in the tree");
		else
			System.out.println("2 is not in the tree");
		if (tree.containsKey(14))
			System.out.println("14 is in the tree");
		else
			System.out.println("14 is not in the tree");
		if (tree.containsKey(12))
			System.out.println("12 is in the tree");
		else
			System.out.println("12 is not in the tree");
		System.out.println("2: " + tree.get(2));
		System.out.println("9: " + tree.get(9));
		System.out.println("8: " + tree.get(8));
		System.out.println("5 before update: " + tree.get(5));
		tree.updateKey(5, 1231231);
		System.out.println("5 after update: " + tree.get(5));
		System.out.println("Number of items: " + tree.size());

		for (int i = 1000; i >= 0; i--){
			tree.put(i, i);
		}
		for (int i = 1000; i >= 0; i--){
			System.out.println(i + ": " + tree.containsKey(i) + ": " + tree.get(i));
		}
		for (Integer i : tree.keySet()){
		    System.out.println(i + ": " + tree.get(i));
        }
		//tree.save();
		/*BTree<Integer> newTree = new BTree(System.getProperty("user.dir") +
				File.separator + "test" + File.separator + "root.bt",
                System.getProperty("user.dir") +
                File.separator + "test" + File.separator + "test.raf");
		for (Integer s : newTree.keySet()){
			System.out.println(s + ": " + newTree.get(s));
		}*/
		BTree<String> tree = new BTree<>(System.getProperty("user.dir") + File.separator + "runfiles" + File.separator + "root.bt",
                System.getProperty("user.dir") + File.separator + "runfiles" + File.separator + "value.raf");
        KMeans<String> clusters = new KMeans(300, tree.size());
        for (String i : tree.keySet()){
            System.out.println(i + ": " + tree.get(i));
            clusters.add(i, tree.get(i));
        }
        clusters.run();
        System.out.println();
        //clusters.displayLists();
        String name = "AHMED";
        ArrayList<String> names = new ArrayList<>(clusters.getCluster(name, tree.get(name)));
        System.out.println(name + ": " + tree.get(name) + "\n");
        for (String s : names){
            System.out.println(s);
        }

        KMeans<String> cluster = null;

	}

}
