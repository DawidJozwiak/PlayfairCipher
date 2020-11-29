/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.dawid.jozwiak.playfair.playfaircipherlab.test;

import pl.polsl.dawid.jozwiak.playfair.playfaircipherlab2.model.Cipher;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import pl.polsl.dawid.jozwiak.playfair.playfaircipherlab2.model.DetectedForbiddenLetters;
import pl.polsl.dawid.jozwiak.playfair.playfaircipherlab2.model.WrongSeparator;

/**
 * Class used to test model
 *
 * @author Dawid Jozwiak
 * @version 1.0
 */
public class CipherTest {

    private Cipher cipher;

    /**
     * Before each test set this data example (data comes from "example" part on
     * Wikipedia)
     */
    @BeforeEach
    public void setUp() {
        cipher = new Cipher("playfair example", "x", "hide the gold in the tree stump");
    }

    /**
     * Test of method addAlphabetWithoutDuplicates
     *
     * @param key - key value that stores key that will be inserted into keyArray that will be needed to encrypt/decrypt our message 
     */
    @ParameterizedTest
    @ValueSource(strings = {":)", "&*!@#^", "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"})
    public void testaddAlphabetWithoutDuplicates(String key) {
        try {
            cipher.addAlphabetWithoutDuplicates(key);
            fail("An exception should be thrown when key isn't a word");
        } catch (DetectedForbiddenLetters e) {
        }
    }

    /**
     * Test of method Encryption
     * @param message - message that we want to encrypt/decrypt
     * @param separator - separator that separates duplicates and is inserted into last place if the message is odd
     */
    @ParameterizedTest
    @CsvSource({":), x", "message, &*!@#^", "uhm, 123"})
    public void testEncryptionIncorrect(String message, String separator) {
        try {
            cipher.setMessage(message);
            cipher.setSep(separator);
            cipher.encryption();
            fail("An exception should be thrown when message or separator is wrong");
        } catch (DetectedForbiddenLetters | WrongSeparator e) {
        }
    }

    /**
     * Testing if decryption is correct by checking if result is the same as in Wikipedia example
     * @param message - message that we want to encrypt/decrypt
     * @param separator - separator that separates duplicates and is inserted into last place if the message is odd
     * @param key - key value that stores key that will be inserted into keyArray that will be needed to encrypt/decrypt our message 
     */
    @ParameterizedTest
    @CsvSource({"Hide the gold in the tree stump, x, playfair example"})
    public void testEncryptionCorrect(String message, String separator, String key){
        try{
        cipher.addAlphabetWithoutDuplicates(key);
        }
        catch(DetectedForbiddenLetters e){
            fail("Key was incorrect");
        }
        try {
            assertEquals(cipher.encryption(), "bmodzbxdnabekudmuixmmouvif");
        } catch (DetectedForbiddenLetters ex) {
           fail("An exception shouldn't be thrown if encryption works correctly");
        } catch (WrongSeparator ex) {
            fail("An exception shouldn't be thrown if encryption works correctly");
        }
    }
    
    
    /**
     * Test of method Encryption
     * @param message - message that we want to encrypt/decrypt
     */
    @ParameterizedTest
    @ValueSource(strings = {":)", "&*!@#^", "", "123"})
    public void testDecryption(String message) {
        try {
            cipher.setMessage(message);
            cipher.decryption();
            fail("An exception should be thrown when message or separator is wrong");
        } catch (DetectedForbiddenLetters | WrongSeparator e) {
        }
    }
    
    /**
     * Testing if decryption is correct by checking if result is the same as in Wikipedia example
     * @param message - message that we want to encrypt/decrypt
     * @param separator - separator that separates duplicates and is inserted into last place if the message is odd
     * @param key - key value that stores key that will be inserted into keyArray that will be needed to encrypt/decrypt our message 
     */
    @ParameterizedTest
    @CsvSource({"bmodzbxdnabekudmuixmmouvif, x, playfair example"})
    public void testDecryptionCorrect(String message, String separator, String key){
        try{
        cipher.setMessage(message);
        cipher.addAlphabetWithoutDuplicates(key);
        }
        catch(DetectedForbiddenLetters e){
            fail("Key was incorrect");
        }
        try {
            assertEquals(cipher.decryption(), "hidethegoldinthetrexestump");
        } catch (DetectedForbiddenLetters ex) {
           fail("An exception shouldn't be thrown if decryption works correctly");
        } catch (WrongSeparator ex) {
            fail("An exception shouldn't be thrown if decryption works correctly");
        }
    }

    /**
     * Test of exception
     * @param key - key value that stores key that will be inserted into keyArray that will be needed to encrypt/decrypt our message
     */
    @ParameterizedTest
    @ValueSource(strings = {":)", "&*!@#^", "", "123"})
    public void testOfDetectedForbiddenLetters(String key) {
        DetectedForbiddenLetters exception = assertThrows(
                DetectedForbiddenLetters.class,
                () -> cipher.addAlphabetWithoutDuplicates(key));
        assertEquals("You used forbidden letter (or left it empty) in key (console parameter) or message!\n"
                + "Use only English alphabet.", exception.what());
    }

    /**
     * Test of exception
     * @param sep - separator that separates duplicates and is inserted into last place if the message is odd
     */
    @ParameterizedTest
    @ValueSource(strings = {":)", "&*!@#^", "123"})
    public void testOfWrongSeparator(String sep) {
        cipher.setSep(sep);
        WrongSeparator exception = assertThrows(
                WrongSeparator.class,
                () -> cipher.encryption());
        assertEquals("Your separator isn't a single character! Try again.", exception.what());
    }

}
