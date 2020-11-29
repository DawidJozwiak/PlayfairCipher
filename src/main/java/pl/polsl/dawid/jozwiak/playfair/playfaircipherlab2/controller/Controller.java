/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.dawid.jozwiak.playfair.playfaircipherlab2.controller;

import java.util.InputMismatchException;
import java.util.Scanner;
import pl.polsl.dawid.jozwiak.playfair.playfaircipherlab2.model.Cipher;
import pl.polsl.dawid.jozwiak.playfair.playfaircipherlab2.model.DetectedForbiddenLetters;
import pl.polsl.dawid.jozwiak.playfair.playfaircipherlab2.model.WrongSeparator;
import pl.polsl.dawid.jozwiak.playfair.playfaircipherlab2.view.View;
/**
 * Controller class used to take input and send it to model and show it using view
 * @author Dawid Jozwiak
 * @version 1.0
 */
public class Controller {

    /**
     * userInputHandler is a method that handles input and send it to its private fields. It also catches exceptions thrown by model.
     * @param key - key to encrypting/decrypting taken from console 
     */
    public void userInputHandler(String key){

        View view = new View();
        Scanner scanner = new Scanner(System.in);
        //User should choose what to do
        view.showMessage("What would you like to do?");
        view.showMessage("1. Encrypt message");
        view.showMessage("2. Decrypt message");
        //Take integer that specifies the choice
        int choice = 0;
        try {
            choice = scanner.nextInt();
            
        } catch (InputMismatchException exception) {
            view.printException("Not an integer, please pick a number 1 or 2.");
            userInputHandler(key);
        }

        switch(choice){
            case 1:
                //Take message
                view.showMessage("Give me the message you would like to encrypt");
                Scanner scanner1 = new Scanner(System.in);
                String input = scanner1.nextLine();

                //Take separator
                view.showMessage("Give me the separator (If you don't want to press x)");
                String input1 = scanner1.nextLine();
                if(input1.equals("x")){
                    input1 = "x";
                }
                Controller controller = new Controller();

                //Calling private method of controller to 
                String encryptedMessage = controller.encrypt(input, input1, key);
                view.showMessage("Your encrypted message is:");
                //calling view to print ready message
                view.showMessage(encryptedMessage);

                view.showMessage("");
                view.showMessage("Would you like to decrypt it?");
                view.showMessage("1. Yes");
                view.showMessage("2. No");
                int choice1 = 0;
                //selecting other options
                while (true) {
                    try {
                        choice1 = scanner.nextInt();
                        switch(choice1){
                            case 1:
                                view.showMessage("Your decrypted message is:");
                                view.showMessage(controller.decrypt(encryptedMessage, input1, key));
                                view.showMessage("");
                                userInputHandler(key);
                                break;
                            case 2:
                                userInputHandler(key);
                                break;
                            default:
                                while(choice1 != 1 || choice1 != 2){
                                    view.showMessage("Pick a number 1 or 2");
                                    choice1 = scanner.nextInt();
                                }
                        }
                    } catch (InputMismatchException exception) {
                        view.printException("Not an integer, please pick a number 1 or 2. Press enter key to exit");
                        if (scanner.next().isEmpty()) {
                            break;
                        }
                    }
                }
                break;

            case 2:
                //asking user to message to decrypt
                view.showMessage("Give me the message you would like to decrypt");
                Scanner scanner2 = new Scanner(System.in);
                String input2 = scanner2.nextLine();
                //Ask user for single letter separator
                view.showMessage("Give me the separator (If you don't want to press x)");
                String input3 = scanner2.nextLine();
                if(input3.equals("x")){
                    input1 = "x";
                }
                Controller controller1 = new Controller();

                //calling private decryption method and printing its results by view
                String decryptedMessage = controller1.decrypt(input2, input3, key);
                view.showMessage("Your decrypted message is:");
                view.showMessage(decryptedMessage);

                //after decrypting we ask user if he wants to encrypt or come back to menu
                view.showMessage("");
                view.showMessage("Would you like to encrypt it?");
                view.showMessage("1. Yes");
                view.showMessage("2. No");

                while (true) {
                    try {
                        choice1 = scanner.nextInt();
                        switch(choice1){
                            case 1:
                                view.showMessage("Your encrypted message is:");
                                view.showMessage(controller1.encrypt(decryptedMessage, input3, key) + "\n");
                                userInputHandler(key);
                                break;
                            case 2:
                                userInputHandler(key);
                                break;
                            default:
                                while(choice1 != 1 || choice1 != 2){
                                    view.showMessage("Pick a number 1 or 2");
                                    choice1 = scanner.nextInt();
                                }
                        }
                    } catch (InputMismatchException exception) {
                        view.printException("Not an integer, please pick a number 1 or 2. Press enter key to exit");
                        if (scanner.next().isEmpty()) {
                            break;
                        }
                    }
                }

                break;


            default:
                view.printException("Please pick a number 1 or 2");
                userInputHandler(key);                
        }


    }

    /**
     * encrypt is a method that takes all input and request model's methods one by one to encrypt message
     * @param message - message that we want to encrypt given by user
     * @param separator - single letter used to separate duplicates or add to odd letter-number message
     * @param key - key that was given in command line used to create keyArray
     * @return encryptedMessage to inputHandler that later will be printed in view
     */
    private String encrypt(String message, String separator, String key) {

        View view = new View();
        String encryptedMessage = "";
        Cipher cipher = new Cipher(key, separator, message);
        try{

            cipher.addAlphabetWithoutDuplicates(key);
        }
        catch(DetectedForbiddenLetters e){
           view.printException(e.what());
            view.showMessage("");
            userInputHandler(key);
        }



        try{
            encryptedMessage = cipher.encryption();
        }
        catch(DetectedForbiddenLetters e){
            view.printException(e.what());
            view.showMessage("");
            userInputHandler(key);
        }
        catch(WrongSeparator e){
            view.printException(e.what());
            view.showMessage("");
            userInputHandler(key);
        }
        return encryptedMessage;
    }


    /**
     * decrypt is a method that takes all input and request model's methods one by one to decrypt message
     * @param message - message that we want to encrypt given by user
     * @param separator - single letter used to separate duplicates or add to odd letter-number message
     * @param key - key that was given in command line used to create keyArray
     * @return encryptedMessage to inputHandler that later will be printed in view
     */
    private String decrypt(String message, String separator, String key) {
        View view = new View();
        String encryptedMessage = "";
        Cipher cipher = new Cipher(key, separator, message);
        try{

            cipher.addAlphabetWithoutDuplicates(key);
        }
        catch(DetectedForbiddenLetters e){
            view.printException(e.what());
            view.showMessage("");
        }


        //try to call method addAlphabetWithoutDuplicates to prepare string for array, if it contains forbidden letters throw an exceptions
        try{
            encryptedMessage = cipher.decryption();
        }
        catch(DetectedForbiddenLetters e){
            view.printException(e.what());
            view.showMessage("");
            userInputHandler(key);
        }
        catch(WrongSeparator e){
            view.printException(e.what());
            view.showMessage("");
            userInputHandler(key);
        }
        return encryptedMessage;

    }
}

