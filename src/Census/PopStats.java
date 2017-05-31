/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Census;

import java.util.Set;
import java.io.*;
import java.util.Scanner;

/**
 *
 * @author Alex
 */
public class PopStats 
{
    private File monthFile;
    private File stateFile;
    private File popFile;
    private String months[];
    private String states[];
    private String pop[];
    private HashTable<String, Integer> statesMap = new HashTable<String, Integer>();
    private HashTable<String, Integer> monthMap = new HashTable<String, Integer>();
    private int totalPop;
    private String monthNames[] = {"January", "Febuary", "March", "April", 
                                   "May", "June", "July", "August",
                                   "September", "October", "November", "December"};
    
    private String stateNames[] = {"Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut",
                                     "Delaware", "District of Columbia", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana",
                                     "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", 
                                     "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico",
                                     "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania",
                                     "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia", 
                                     "Washington", "West Virginia", "Wisconsin", "Wyoming", "Puerto Rico",};
    
    public PopStats(String monthFile, String stateFile, String popFile) throws Exception
    {
        this.monthFile = new File(monthFile);
        this.stateFile = new File(stateFile);
        this.popFile = new File(popFile);
        createMonths();
        createStates();
        createPop();
    }
    
    public String[] month()
    {
        return months;
    }
    
    public String[] states()
    {
        return states;
    }
    
    public void Populate()
    {
        totalPopulation();
        populateMonths();
        populateStates();
    }
    
    private void totalPopulation()
    {
        String usPop[] = pop[0].split("\"");
        totalPop = Integer.parseInt(usPop[1]);
    }
    
    private void populateMonths()
    {
        for (int i = 0; i < months.length; i++)
        {
            String data[] = months[i].split("\"");
            monthMap.put(monthNames[i], Integer.parseInt(data[1]));
        }
    }
    
    private void populateStates()
    {
        for (int i = 0; i < states.length; i++)
        {
            String data[] = states[i].split("\"");
            statesMap.put(stateNames[i], Integer.parseInt(data[1]));
        }
    }
    
    public int getStatePop(String state)
    {
        int statePop = statesMap.get(state);
        return statePop;
    }
    
    public int getMonthPop(String month)
    {
        int monthPop = monthMap.get(month);
        return monthPop;
    }
    
    public int getPop()
    {
        return totalPop;
    }
    
    public Set<String> getMonthSet()
    {
        return monthMap.alphaSet();
    }
    
    public Set<String> getStateSet()
    {
        return statesMap.alphaSet();
    }
    
    public boolean containsMonth(String month)
    {
        return monthMap.contains(month);
    }
    
    public boolean containsState(String state)
    {
        return statesMap.contains(state);
    }

    private void createMonths() throws Exception
    {
        Scanner in = new Scanner(monthFile);
        int i = 0;
        while (in.hasNext())
        {
            String data[] = in.nextLine().split("\"");
            monthMap.put(monthNames[i], Integer.parseInt(data[1]));
            i++;
        }
        in.close();
    }

    private void createStates() throws Exception
    {
        Scanner in = new Scanner(stateFile);
        int i = 0;
        while (in.hasNext())
        {
            String data[] = in.nextLine().split("\"");
            statesMap.put(stateNames[i], Integer.parseInt(data[1]));
            i++;
        }
        in.close();
    }

    private void createPop() throws Exception
    {
        Scanner in = new Scanner(popFile);

        while (in.hasNextLine())
        {
            String usPop[] = in.nextLine().split("\"");
            totalPop = Integer.parseInt(usPop[1]);
        }
        in.close();
    }
}
