package Testers;

import Cache.PersistentBTree;

/**
 * Created by darki on 3/29/2017.
 */
public class PersistenBTreeTester {

    public static void main(String args[]){
        PersistentBTree<Integer, Integer> tree = new PersistentBTree(4);
        tree.insert(1, null);
        tree.insert(2, null);
    }
}
