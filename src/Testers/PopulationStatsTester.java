/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Testers;

import Census.PopStats;
import HashTable.HashTable;
import java.util.HashMap;
/**
 *
 * @author Alex <your.name at your.org>
 */
public class PopulationStatsTester 
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception
    {
        /*
        //Retrieve Data
        String months = "http://api.census.gov/data/2016/pep/natmonthly?get=POP&for=us:*&MONTHLY=71:82";
        String states = "http://api.census.gov/data/2016/pep/charagegroups?get=POP,GEONAME&for=state:*";
        String pop = "http://api.census.gov/data/2016/pep/charagegroups?get=POP,GEONAME&for=us:*";
        
        //Create Object
        PopStats anualPop = new PopStats(months, states, pop);
        anualPop.Populate();
        
        //Check Values
        System.out.println("US Population: " + anualPop.getPop() + "\n");
        for (String s : anualPop.getMonthSet())
        {
            System.out.println(s + ": " + anualPop.getMonthPop(s));
        }
        System.out.println();
        for (String s : anualPop.getStateSet())
        {
            System.out.println(s + ": " + anualPop.getStatePop(s));
        }
        //System.out.println(anualPop.getMonthPop("October"));
        HashMap<Integer, PopStats> pop16 = new HashMap<>();
        pop16.put(2016, anualPop);
        System.out.println(pop16.get(2016).getMonthPop("September"));
        */
    }
}