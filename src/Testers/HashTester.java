/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Testers;

import java.util.HashMap;
import java.util.Arrays;
import HashTable.HashTable;

/**
 *
 * @author Alex <your.name at your.org>
 */
public class HashTester 
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        HashTable<String, Integer> map = new HashTable();
        map.put("A", 1);
        //System.out.println("Size: " + map.size());
        map.put("B", 2);
        //System.out.println("Size: " + map.size());
        map.put("C", 3);
        //System.out.println("Size: " + map.size());
        map.put("D", 4);
        map.put("E", 5);
        map.put("F", 6);
        map.put("G", 7);
        map.put("H", 8);
        map.put("I", 9);
        map.put("J", 10);
        map.put("K", 11);
        map.put("L", 12);
        map.put("M", 13);
        map.put("N", 14);
        map.put("O", 15);
        map.put("P", 16);
        map.put("Q", 17);
        // map.put("A", 19);
        //map.remove("A");
        //map.remove("C");
        //map.put("Q", 17);
        //map.put("R", 18);
        System.out.println("Size: " + map.size());
        //System.out.println("Limit: " + map.limit());
        //System.out.println("Table: " + map.tableSize());
        if (map.isEmpty())
            System.out.println("Table is empty!");
        else   
            System.out.println("Table is not empty");
        System.out.println();
        
        /*map.keySet().forEach((s) -> {
            System.out.println(s + " " + map.get(s));
        });*/
        System.out.println("A: " + map.get("A"));
        System.out.println("O: " + map.get("O"));
        System.out.println("Z: " + map.get("Z"));
        System.out.println("Q: " + map.get("Q") + "\n");
        
        if (map.contains("P"))
            System.out.println("There's a P");
        else
            System.out.println("There's no P");
        if (map.contains("Z"))
            System.out.println("There's a Z");
        else
            System.out.println("There's no Z");
        if (map.contains("R"))
            System.out.println("There's an R");
        else
            System.out.println("There's no R");
        if (map.contains("A"))
            System.out.println("There's an A");
        else
            System.out.println("There's no A");
        if (map.contains("Q"))
            System.out.println("There's a Q");
        else
            System.out.println("There's no Q");
        System.out.println();
        for(String s : map.keySet())
        {
            System.out.println(s + ": " + map.get(s));
        }
        /*System.out.println("\nEmptying table!");
        map.empty();
        if (map.isEmpty())
            System.out.println("Table is empty");
        else   
            System.out.println("Table is not empty");*/
        
        //HashTable newMap = new HashTable(map);
        //System.out.println("\nA: " + newMap.get("A"));
    }
    
}
