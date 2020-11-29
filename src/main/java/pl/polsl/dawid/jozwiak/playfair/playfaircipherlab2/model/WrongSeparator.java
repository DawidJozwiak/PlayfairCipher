/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.dawid.jozwiak.playfair.playfaircipherlab2.model;

/**
 *
 * Exception class that prints message when forbidden letters are detected
 * @version 1.0
 * @author Dawid Jozwiak
 */
public class WrongSeparator extends Exception {
     /**
     * Non-parameter constructor
     */
    public WrongSeparator() {
    }

    /**
     * Exception class constructor
     *
     * @param what display message
     */
    public WrongSeparator(String what) {
         super(what);
    }
    
    /**
     * 
     * @return string that returns message
     */
    public String what(){
        return "Your separator isn't a single character! Try again.";
    }
}
