/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Practice;

import java.net.*;
import java.io.*;
import HashTable.HashTable;
import java.util.Arrays;
import java.util.regex.Pattern;
import org.xml.sax.XMLReader;

/**
 *
 * @author Alex 
 */
public class XMLPractice 
{

    /**
     * @param args the command line arguments
     * @throws java.net.MalformedURLException
     */
    public static void main(String[] args) throws Exception 
    {

        URL site = new URL("http://api.census.gov/data/2016/pep/population?get=POP,GEONAME&for=state:*");
        BufferedReader in = new BufferedReader(
        new InputStreamReader(site.openStream()));

        String inputLine = in.readLine();
        String data[] = new String[52];
        int i = 0;
        while ((inputLine = in.readLine()) != null)
        {
            data[i] = inputLine;
            i++;
        }
        in.close();
        /*for (int x = 0; x < data.length; x++)
        {
        System.out.println(data[x]);
        }*/
        
        HashTable<String, Integer> states = new HashTable();
        for (int x = 0; x < 52; x++)
        {
            String state[] = data[x].split("\"");
            states.put(state[3], Integer.parseInt(state[1]));
        }
        for (String s : states.alphaSet())
        {
            System.out.println(s + ": " + states.get(s));
        }
    }
    
}
