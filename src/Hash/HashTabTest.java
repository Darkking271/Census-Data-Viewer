/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hash;

/**
 *
 * @author Alex <your.name at your.org>
 */
public class HashTabTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        HashTable newTab = new HashTable();
        
        newTab.put("Hello", 5);
        
        if (newTab.ContainsKey("Hello"))
            System.out.println("It's There");
        else
            System.out.println("It's not there");
    }
    
}
