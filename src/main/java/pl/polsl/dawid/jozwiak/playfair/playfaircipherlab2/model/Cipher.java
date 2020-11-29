/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.dawid.jozwiak.playfair.playfaircipherlab2.model;

import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Cipher class that encrypts or decrypts message provided by controller and
 * stores the data in runtime
 *
 * @author Dawid Jozwiak
 * @version 1.0
 */
public class Cipher {

    /**
     * key value that stores key that will be inserted into keyArray that will
     * be needed to encrypt/decrypt our message
     */
    private String key;
    /**
     * separator that separates duplicates and is inserted into last place if
     * the message is odd
     */
    private String sep;
    /**
     * message that we want to encrypt/decrypt
     */
    private String message;
    /**
     * char array filled with alphabet letters, need to create keyArray that
     * will be needed to encrypt/decrypt our message
     */
    private final String alphabet = "abcdefghiklmnopqrstuvwxyz";
    /**
     * array list that is used as our key array to encrypt elements
     */
    private ArrayList<ArrayList<Character>> square;

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return the separator
     */
    public String getSep() {
        return sep;
    }

    /**
     * @param sep the separator to set
     */
    public void setSep(String sep) {
        this.sep = sep;
    }

    /**
     * @return the plainText
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param plainText the plainText to set
     */
    public void setMessage(String plainText) {
        this.message = plainText;
    }

    /**
     * @param key key value that stores key that will be inserted into keyArray
     * that will be needed to encrypt/decrypt our message
     * @param sep separator that separates duplicates and is inserted into last
     * place if the message is odd
     * @param message message that we want to encrypt/decrypt
     */
    public Cipher(String key, String sep, String message) {
        this.key = key;
        this.sep = sep;
        this.message = message;
    }

    /**
     * Method that adds alphabet without duplicates to keyArray in order to
     * prepare it for insertion into 5x5 array
     *
     * @param key - key that we need to create keyArray
     * @throws DetectedForbiddenLetters - throwing exception when we detect
     * letters that aren't in English alphabet
     */
    public void addAlphabetWithoutDuplicates(String key) throws DetectedForbiddenLetters {

        if (key.length() > 25) {
            throw new DetectedForbiddenLetters();
        }

        //delets all white spaces from key
        key = key.replaceAll("\\s", "");
        key = key.toLowerCase();
        String str[] = key.split(",");
        List<String> keyArray = new ArrayList<>(Arrays.asList(str));

        //checking if there is forbidden letter and throwing exception if found
        for (String c : keyArray) {
            if (c.matches("[a-zA-Z]+") == false) {
                throw new DetectedForbiddenLetters();
            }
        }

        String alphabet1[] = alphabet.split(", ");
        List<String> alphabetArray = new ArrayList<>(Arrays.asList(alphabet1));
        //creating array that is filled with both key and alphabet
        keyArray.addAll(alphabetArray);

        //swapping j into i (as suggested in rules) and putting it all into string
        if (keyArray.contains("j")) {
            int index = keyArray.indexOf("j");
            keyArray.set(index, "i");
        }
        List<String> finalArray = new ArrayList<>();
        keyArray.stream().filter(temp -> (!finalArray.contains(temp))).forEachOrdered(temp -> {
            finalArray.add(temp);
        }); // If this element is not present in newList
        // then add it

        StringBuilder sb = new StringBuilder();

        finalArray.forEach(s -> {
            sb.append(s);
        });
        String finalString = sb.toString();

        String noDuplicates = Arrays.asList(finalString.split(""))
                .stream()
                .distinct()
                .collect(Collectors.joining());

        arrayMaker(noDuplicates);
    }

    /**
     * Method arrayMaker that makes ArrayList of constant size 5x5 and fills it
     * with our key+alphabet string
     *
     * @param finalArray - finalArray that was created in method above
     */
    private void arrayMaker(String finalArray) {

        ArrayList<Character> c = new ArrayList<>();

        // Copy character by character into arraylist 
        for (int i = 0; i < finalArray.length(); i++) {
            c.add(i, finalArray.charAt(i));
        }

        square = new ArrayList<>();

        int k = 0;

        for (int i = 0; i < 5; i++) {
            ArrayList<Character> row = new ArrayList<>();
            square.add(row);
            for (int j = 0; j < 5; j++) {
                row.add(c.get(k++));
            }
        }
    }

    /**
     * Method used for encryption of message
     *
     * @return returns encrypted message
     * @throws DetectedForbiddenLetters - throwing exception when we detect
     * letters that aren't in English alphabet
     * @throws WrongSeparator - throwing exception when we detect that separator
     * isn't single letter character
     */
    public String encryption() throws DetectedForbiddenLetters, WrongSeparator {

        message = message.toLowerCase();
        message = message.replaceAll("\\s", "");
        //if separator isn't a letter at all or isn't in english alphabet throw an exception or isn't single
        if (Character.isLetter(sep.charAt(0)) == false || sep.matches("[A-Za-z]{1}") == false || sep.isEmpty()) {
            throw new WrongSeparator();
        }

        //if message isn't a letter at all or isn't in english alphabet throw an exception or isn't single
        if (message.matches("[a-zA-Z]+") == false) {
            throw new DetectedForbiddenLetters();
        }

        char separator = sep.charAt(0);
        //delete whitespaces from message

        ArrayList<Character> ch = new ArrayList<>();

        // Copy character by character into arraylist 
        for (int i = 0; i < message.length(); i++) {
            ch.add(i, message.charAt(i));
        }

        ArrayListPreparation ar = new ArrayListPreparation();
        ArrayList<Character> messageArray = ar.removeDuplicates(ch, separator);
        //preparing array that will contain decrypted message
        ArrayList<Character> decryptedArray = new ArrayList<>();
        int rowA = 0;
        int columnA = 0;
        int rowB = 0;
        int columnB = 0;

        //itterate through array with message and save positions of letters in square
        for (int a = 0; a < messageArray.size() - 1; a += 2) {
            for (int c = 0; c < 5; c++) {
                for (int b = 0; b < 5; b++) {
                    if (messageArray.get(a).equals(square.get(c).get(b))) {
                        rowA = c;
                        columnA = b;
                        break;
                    }
                }
            }
            for (int c = 0; c < 5; c++) {
                for (int b = 0; b < 5; b++) {
                    if (messageArray.get(a + 1).equals(square.get(c).get(b))) {
                        rowB = c;
                        columnB = b;
                        break;
                    }
                }
            }
            //after finding both letters check which condition should be applied to them

            //1. If the letters appear on the same row of your table, 
            //replace them with the letters to their immediate right respectively
            //(wrapping around to the left side of the row if a letter in the original pair was on the right side of the row).
            if (rowA == rowB) {
                if (columnA == 4) {
                    decryptedArray.add(a, square.get(rowA).get(0));
                } else {
                    decryptedArray.add(a, square.get(rowA).get(columnA + 1));
                }

                if (columnB == 4) {
                    decryptedArray.add(a + 1, square.get(rowB).get(0));
                } else {
                    decryptedArray.add(a + 1, square.get(rowB).get(columnB + 1));
                }

                //if the letters appear on the same column of your table, 
                //replace them with the letters immediately below respectively 
                //(wrapping around to the top side of the column if a letter in the original pair was on the bottom side of the column).   
            } else if (columnA == columnB) {
                if (rowA == 4) {
                    decryptedArray.add(a, square.get(0).get(columnA));
                } else {
                    decryptedArray.add(a, square.get(rowA + 1).get(columnA));
                }
                if (rowB == 4) {
                    decryptedArray.add(a + 1, square.get(0).get(columnB));
                } else {
                    decryptedArray.add(a + 1, square.get(rowB + 1).get(columnB));
                }

                //If the letters are not on the same row or column, 
                //replace them with the letters on the same row respectively but at the other pair of corners of the rectangle defined by the original pair. 
                //The order is important – the first letter of the encrypted pair is the one that lies on the same row as the first letter of the plaintext pair.    
            } else {
                int distance = columnA - columnB;
                decryptedArray.add(a, square.get(rowA).get(columnA - distance));
                decryptedArray.add(a + 1, square.get(rowB).get(columnB + distance));
            }
        }
        //change array into string and send it to controller
        StringBuilder sb = new StringBuilder();
        decryptedArray.forEach(cha -> {
            sb.append(cha);
        });

        message = sb.toString();
        return message;
    }

    /**
     * Method used for decryption of message
     *
     * @return returns encrypted message
     * @throws DetectedForbiddenLetters - throwing exception when we detect
     * letters that aren't in English alphabet
     * @throws WrongSeparator - throwing exception when we detect that separator
     * isn't single letter character
     */
    public String decryption() throws DetectedForbiddenLetters, WrongSeparator {

        message = message.toLowerCase();
        //delete whitespaces from message
        message = message.replaceAll("\\s", "");
        //if message isn't a letter at all or isn't in english alphabet throw an exception or isn't single
        if (message.matches("^[a-zA-Z]*$") == false || message.equals("")) {
            throw new DetectedForbiddenLetters();
        }

        //if separator is empty throw an exception
        if (sep.isEmpty()) {
            throw new WrongSeparator();
        }

        //if separator isn't a letter at all or isn't in english alphabet throw an exception or isn't single
        if (Character.isLetter(sep.charAt(0)) == false || sep.matches("[A-Za-z]{1}") == false) {
            throw new WrongSeparator();
        }

        ArrayList<Character> ch = new ArrayList<>();

        // Copy character by character into arraylist 
        for (int i = 0; i < message.length(); i++) {
            ch.add(i, message.charAt(i));
        }
        ArrayList<Character> messageArray = ch;

        //preparing array that will contain decrypted message
        ArrayList<Character> decryptedArray = new ArrayList<>();
        int rowA = 0;
        int columnA = 0;
        int rowB = 0;
        int columnB = 0;

        //itterate through array with message and save positions of letters in square
        for (int a = 0; a < messageArray.size() - 1; a += 2) {
            for (int c = 0; c < 5; c++) {
                for (int b = 0; b < 5; b++) {
                    if (messageArray.get(a).equals(square.get(c).get(b))) {
                        rowA = c;
                        columnA = b;
                        break;
                    }
                }
            }
            for (int c = 0; c < 5; c++) {
                for (int b = 0; b < 5; b++) {
                    if (messageArray.get(a + 1).equals(square.get(c).get(b))) {
                        rowB = c;
                        columnB = b;
                        break;
                    }
                }
            }
            //after finding both letters check which condition should be applied to them

            //Rules like in encryption but reversed
            if (rowA == rowB) {
                if (columnA == 0) {
                    decryptedArray.add(a, square.get(rowA).get(4));
                } else {
                    decryptedArray.add(a, square.get(rowA).get(columnA - 1));
                }

                if (columnB == 0) {
                    decryptedArray.add(a + 1, square.get(rowB).get(4));
                } else {
                    decryptedArray.add(a + 1, square.get(rowB).get(columnB - 1));
                }
            } else if (columnA == columnB) {
                if (rowA == 0) {
                    decryptedArray.add(a, square.get(4).get(columnA));
                } else {
                    decryptedArray.add(a, square.get(rowA - 1).get(columnA));
                }
                if (rowB == 0) {
                    decryptedArray.add(a + 1, square.get(4).get(columnB));
                } else {
                    decryptedArray.add(a + 1, square.get(rowB - 1).get(columnB));
                }
            } else {
                int distance = columnA - columnB;
                decryptedArray.add(a, square.get(rowA).get(columnA - distance));
                decryptedArray.add(a + 1, square.get(rowB).get(columnB + distance));
            }
        }
        //change array into string and send it to controller
        StringBuilder sb = new StringBuilder();
        decryptedArray.forEach(cha -> {
            sb.append(cha);
        });
        //change array into string and send it to controller
        message = sb.toString();
        return message;

    }
    /**
     * private method that
     *
     * @param list - list that needs that we want to remove duplicate
     * @return - return new list with elements
     */

}

/**
 * Functional Interface used to implement lambda expression
 *
 * @author Dawid Jozwiak
 */
@FunctionalInterface
interface DuplicationsRemover {

    public Character addition(Character element);
}

/**
 * Class used to prepare ArrayList and implement Functional Interface
 *
 * @author Dawid Jozwiak
 * @version 1.0
 */
class ArrayListPreparation {

    /**
     * Method that uses lambda expression to add element to the list and prepare
     * it for encryption/decryption
     *
     * @param list - list that will be prepared
     * @param sep - separator
     * @return - finished list, read to be encrypted/decrypted
     */
    public ArrayList<Character> removeDuplicates(ArrayList<Character> list, char sep) {

        // Create a new ArrayList 
        ArrayList<Character> newList = new ArrayList<>();

        // Traverse through the first list       
        DuplicationsRemover adder = (element) -> element;
        list.forEach(element -> {
            newList.add(adder.addition(element));
        });
        // Traverse through the first list 
        for (int i = 0; i < newList.size() - 1; i++) {
            if ((newList.get(i)).equals(newList.get(i + 1))) {
                newList.add(i + 1, sep);
            }
            if ((newList.get(i)).equals('j')) {
                newList.set(i, 'i');
            }
        }

        if (newList.size() % 2 != 0) {
            newList.add(sep);
        }
        // return the new list 
        return newList;
    }
}
