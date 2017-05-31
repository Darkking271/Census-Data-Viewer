/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Practice;

import java.util.ArrayList;

/**
 *
 * @author Alex
 */
public class corelation 
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        double x[] = {4.0, 6.0, 8.0, 4.0, 2.0, 1.0, 5.0, 7.0, 4.0, 6.0};
        double y[] = {85.0, 80.0, 92.0, 70.0, 65.0, 60.0, 89.0, 82.0, 81.0, 95.0};
        double xy[] = new double[10];
        double totalX = 0;
        double totalY = 0;
        double totalXY = 0;
        for (int i = 0; i < 10; i ++)
        {
            xy[i] = x[i] * y[i];
        }
        for (int i = 0; i < 10; i ++)
        {
            totalX += x[i];
            totalY += y[i];
            totalXY += xy[i];
        }
        double c = calcCorel(totalX, totalY, 10, totalXY, x, y);
        System.out.println("Corelation: " + c);
    }    
    
    private static double calcCorel(double totalPop, double totalMonth, double count, double xy, double pop[], double month[])
    {
        double popAve = totalPop / count;
        double monthAve = totalMonth / count;
        double n = 1 / (count - 1);
        double numerator = n * (xy - (count * popAve * monthAve));
        System.out.println("Num " + popAve + " " + monthAve + " " + numerator + " " + xy);
        
        double popSD = calcSD(pop, popAve, count);
        double monthSD = calcSD(month, monthAve, count);
        double denominator = popSD * monthSD;
        System.out.println("Den " + popSD + " " + monthSD + " " + denominator);
        
        double corelation = numerator / denominator;
        return corelation;
    }
    
    private static double calcSD(double list[], double ave, double count)
    {
        double total = 0;
        for (int i = 0; i < count; i++)
        {
            double num = list[i] - ave;
            total += Math.pow(num, 2);
        }
        double totalAve = total / count;
        System.out.println("Total: " + total);
        System.out.println("Count: " + count);
        System.out.println("Ave: " + totalAve);
        double corel = Math.sqrt(totalAve);
        return corel;
    }
    
}
