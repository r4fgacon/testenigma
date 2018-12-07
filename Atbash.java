import java.io.*;
import java.util.*;

public class Atbash{

    private static String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static List<Character> alphabetToListChar = convertStringToArray(alphabet);

    private static String cipheringKey = "ZYXWVUTSRQPONMLKJIHGFEDCBA";
    private static List<Character> cipheredKeyToListChar = convertStringToArray(cipheringKey);

    /**
     * @param stringLetter is string of letters
     * @return list of Characters from stringLetter
     */
    private static List<Character> convertStringToArray(String stringLetter) {
        List<Character> listLetter = new
                ArrayList<>();
        stringLetter = stringLetter.toUpperCase();
        for (char letter : stringLetter.toCharArray()) {
            listLetter.add(letter);
        }
        return listLetter;
    } 

    /**
     * @param userInput is string of letter, which user entered
     * @return ciphered user's input by using cipheringKey
     */
    public static String cipherUserInput(String userInput){
        String cipheredWord = "";
        userInput = userInput.toUpperCase();
        for (int i = 0; i < userInput.length(); i++) {
            int indexCharacter = alphabetToListChar.indexOf(userInput.charAt(i));
            cipheredWord +=  cipheredKeyToListChar.get(indexCharacter);
        }
        return cipheredWord;
    }

    /**
     * @param userInput is string of letter, which user entered
     * @return  decoded user's input by using alphabet
     */
    public static String decodeUserInput(String userInput){
        String decodedWord = "";
        userInput = userInput.toUpperCase();
        for (int i = 0; i < userInput.length(); i++) {
            int indexCharacter = cipheredKeyToListChar.indexOf(userInput.charAt(i));
            decodedWord += alphabetToListChar.get(indexCharacter);
        }

        return decodedWord;
    }
}