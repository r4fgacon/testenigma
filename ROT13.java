import java.io.*;
import java.util.*;


public class ROT13{
    private static String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static String defaultKey = "NOPQRSTUVWXYZABCDEFGHIJKLM"; // default key; len = 26
    
    private static List<Character> alphabetL = stringToCharArray(alphabet);
    //private static List<Character> cipherL = stringToCharArray(defaultKey);


    public static String encipher(String string, String key){
        key = key != null ? key : defaultKey;
        String newString = "";
        string = Enigma.handleStringOnInput(string);
        string = string.toUpperCase();
        for (int i = 0; i < string.length(); i++){
            int temp = alphabetL.indexOf(string.charAt(i));
            newString += key.charAt(temp);
        }
        return newString;
    }


    public static String decipher(String string, String key){
        key = key != null ? key : defaultKey;
        String newString = "";
        string = Enigma.handleStringOnInput(string);
        string = string.toUpperCase();
        List<Character> cipherL = stringToCharArray(key);
        for (int i = 0; i < string.length(); i++){
            int temp = cipherL.indexOf(string.charAt(i));
            newString += alphabet.charAt(temp);
        }
        return newString;
    }

    private static List<Character> stringToCharArray(String string){
        List<Character> list = new ArrayList<Character>();
        string = string.toUpperCase();
        for(char letter : string.toCharArray())
            list.add(letter);
        return list;
    }
}