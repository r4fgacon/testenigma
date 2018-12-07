import java.io.*;
import java.util.*;



public class Trifid{
    private static String defaultKey = "EPSDUCVWYM.ZLKXNBTFGORIJHAQ"; //default key; len = 27
    private static String defaultPeriod = "5"; //usually 5 - 20, which is part of the key material agreed on by both sender and receiver
    //private static char ThreeDimKey[][][] = get3dKey(defaultKey);  // char[3][3][3]; // thats a comment

    public static String encipher(String string, String key, String sPeriod){
        
        key = key != null ? key : defaultKey;
        sPeriod = sPeriod != null ? sPeriod : defaultPeriod;
        int period = Integer.parseInt(sPeriod);

        string = Enigma.handleStringOnInput(string);

        String enciphered = encipherPostFormat(string, key, period);
        
        String properOutputString = outputFormat(enciphered, period);

        return properOutputString;
    }

    private static String outputFormat(String string, int period){
        // prints Spacebars in output based on period
        String properOutputString = "";
        for (int i = 0; i < string.length(); i++){
            properOutputString += string.charAt(i);
            if((i+1) % period == 0 && i != 0){
                properOutputString += ' ';
            }
        }
        return properOutputString;
    }


	private static String encipherPostFormat(String string, String key, int period) {
		int strLength = string.length();
        int tab[][] = new int[strLength][3];
        int newTab[][] = new int[strLength][3];
     
        tab = convertStringTo2dArray(string, key);
        newTab = switchUsingDeclaredPeriod(tab, strLength, period);
        String enciphered = convert2dIntTabToStr(newTab, strLength, key);

        return enciphered;
    }

    public static String decipher(String string, String key, String sPeriod) {
        key = key != null ? key : defaultKey;
        sPeriod = sPeriod != null ? sPeriod : defaultPeriod;
        int period = Integer.parseInt(sPeriod);

        string = Enigma.handleStringOnInput(string);

        int strLength = string.length();

        String cipher = convertStringToIntString(string, key);
        int newTab[][] = switchUsingDeclaredPeriodCounterClockWise(cipher, strLength, period);
        String deciphered = convert2dIntTabToStr(newTab, strLength, key);

        return deciphered;
    }
    

    public static int[][] convertStringTo2dArray(String string, String key){
        int[][] index3d = new int[string.length()][3];
        for (int i = 0; i < string.length(); i++)
            index3d[i] = convertCharToArray(string.charAt(i), key);
        
        return index3d;
    }

    private static int[] convertCharToArray(char letter, String key){
        // Method used only by convertStringTo2dArray
        int[] index2d = new int[3];
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                for (int k = 0; k < 3; k++){
                    if(get3dKey(key)[i][j][k] == letter){
                        index2d[0] = i+1;
                        index2d[1] = j+1;
                        index2d[2] = k+1;
                    }
                }
            }        
        }

        return index2d;
    }

    public static String convertStringToIntString(String string, String key){
        String cipher = "";
        for (int i = 0; i < string.length(); i++)
            cipher += getIntStringRepresentationOfChar(string.charAt(i), key);

        return cipher;
    }


	private static String getIntStringRepresentationOfChar(int number, String key) {
        // Method used only by convertStringToIntString
        String cipher = "";
		for (int i = 0; i < 3; i++){
		    for (int j = 0; j < 3; j++){
		        for (int k = 0; k < 3; k++){
		            if(get3dKey(key)[i][j][k] == number){
		                cipher += Integer.toString(i + 1);
		                cipher += Integer.toString(j + 1);
		                cipher += Integer.toString(k + 1);
                    }
                }
            }
        }
        return cipher;
    }

    public static int[][] convertIntStringTo2dArray(String string) {
        int tabLen = string.length() / 3;
        int[][] TwoDimArray = new int[tabLen][3];
        int iterations = 0;

        for (int i = 0; i < (tabLen); i++) {
            for (int j = 0; j < 3; j++) {
                TwoDimArray[i][j] = Character.getNumericValue(string.charAt(iterations));
                iterations++;
            }
        }
        return TwoDimArray;
    }

    public static String convert2dIntTabToStr(int tab[][], int elements, String key){
        String string = "";
        for (int i = 0; i < elements; i++)
            string += get3dKey(key)[tab[i][0] - 1][tab[i][1] - 1][tab[i][2] - 1];
        return string;
    }

    public static char[][][] get3dKey(String string){
        char[][][] threeSquares = new char[3][3][3];
        int iterations = 0;

        for (int i = 0; i < 3; i ++){
            for (int j = 0; j < 3; j++){
                for (int k = 0; k < 3; k++){
                    threeSquares[i][j][k] = string.charAt(iterations);
                    iterations++;
                }
            }
        }
    return threeSquares;
    }

    public static int[][] switchUsingDeclaredPeriod(int tab[][], int elements, int period){
        int amountPeriod = 0;
        int finalSquare = 0;
        String cipher = "";

        amountPeriod = elements / period; // amount of "flipable squares"
        finalSquare = elements % period; // checks if squares are uneven(needs additional loop if so)

        if(elements > period){
            
            for(int sqrs = 0; sqrs < amountPeriod; sqrs++){
                for(int j = 0; j < 3; j++){
                    for(int i = 0 + (sqrs * period); i < period + (sqrs * period); i++)
                        cipher += Integer.toString(tab[i][j]);
                }
            }
            if(finalSquare != 0){
                for(int j = 0; j < 3; j++){
                    for(int i = 0 + (amountPeriod * period); i < finalSquare + (amountPeriod * period); i++ )
                        cipher += Integer.toString(tab[i][j]);
                }
            }
        }
        else if (elements <= period){
            for(int j = 0; j < 3; j++){
                for(int i = 0; i < elements; i++)
                    cipher += Integer.toString(tab[i][j]);             
            }
        }

        return convertIntStringTo2dArray(cipher);
    }

    public static int[][] switchUsingDeclaredPeriodCounterClockWise(String string, int elements, int period){
        int amountPeriod = 0;
        int finalSquare = 0;
        
        int tab[][] = new int[elements][3];
        int iterations = 0;
        amountPeriod = elements / period; // amount of "flipable squares"
        finalSquare = elements % period; // checks if squares are uneven(needs additional loop if so)

        if(elements > period){
            for(int sqrs = 0; sqrs < amountPeriod; sqrs++){
                for(int j = 0; j < 3; j++){
                    for(int i = 0 + (sqrs * period); i < period + (sqrs * period); i++){
                        tab[i][j] = Character.getNumericValue(string.charAt(iterations));
                        iterations++;
                    }
                }
            }
            if(finalSquare != 0){
                for(int j = 0; j < 3; j++){
                    for(int i = 0 + (amountPeriod * period); i < finalSquare + (amountPeriod * period); i++ ){
                        tab[i][j] = Character.getNumericValue(string.charAt(iterations));
                        iterations++;
                    }
                }
            }
        }
        else if (elements <= period){
            for(int j = 0; j < 3; j++){
                for(int i = 0; i < elements; i++){
                    tab[i][j] = Character.getNumericValue(string.charAt(iterations));
                    iterations++;
                } 
            }
        }

    return tab;
    }
}