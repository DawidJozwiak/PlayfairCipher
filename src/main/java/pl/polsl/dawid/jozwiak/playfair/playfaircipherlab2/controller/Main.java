/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.dawid.jozwiak.playfair.playfaircipherlab2.controller;

/**
 * Main class
 * @version 1.0
 * @author Dawid Jozwiak
 */
public class Main {

    /**
     * Main method that takes key to encrypting/decrypting from console.
     * 
     * @param args program call parameters
     */
    public static void main(String[] args) {
        
        Controller controller = new Controller();
         String key = "";
        //read all agruments (key)
        for (String arg : args) {
            key += arg;
        }
        controller.userInputHandler(key);
        
    }
    
}
