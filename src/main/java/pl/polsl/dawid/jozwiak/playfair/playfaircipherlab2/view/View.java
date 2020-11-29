/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.dawid.jozwiak.playfair.playfaircipherlab2.view;

import java.util.ArrayList;

/**
 * View class that prints information requested by controller
 * @version 1.0
 * @author Dawid Jozwiak
 */
public class View {
    
    /**
     * Returns message of exception
     * @param what message should be printed
     */
    public void printException(String what){
         System.out.println("Exception: " + what);
    }

    /**
     * Method that shows array to curious user
     * @param square - keyArray that contains message + alphabet to be displayed on screen 
     */
    public void showArray(ArrayList<ArrayList<Character>> square){
        System.out.println("Your array:");
         square.forEach(s -> {
            System.out.println(s);
        });
    }

    /**
     * showMessage method that shows the message to user
     * @param message - message that will be printed
     */
    public void showMessage(String message){
        System.out.println(message);
    }
}
