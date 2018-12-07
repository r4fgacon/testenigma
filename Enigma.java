import java.io.*;
// import java.time.Period;
import java.util.*;

public class Enigma{
    public static void main(String[] args){
        List<String> listArg = Arrays.asList("-e", "-d");
        List<String> avaiableOptions = Arrays.asList("-e : ENCIPHER", "-d : DECIPER", "-l : LIST");
        List<String> listCiphers = Arrays.asList("Atbash", "Morse", "ROT13", "Trifid");
        // Add new Ciphers here, and to runClass() method.

        try{
        if (args[0].equals("-l")){
            System.out.println("Available ciphers: ");
            for (String cipher : listCiphers){
                System.out.println(cipher);
            }
            System.out.println("java Enigma [option(-e/-d)] [cipher] [key] [period] <text to cipher>");
            System.out.println("Key option available only for ROT13 and Trifid, period for Trifid; not necessary");
        }
        else if (listArg.contains(args[0]) && listCiphers.contains(args[1]) && args.length == 3)
            runClass(args[0], args[1], args[2]);
        else if (listArg.contains(args[0]) && listCiphers.contains(args[1]) && args.length == 4)
            runClass(args[0], args[1], args[2], args[3]);
        else if (listArg.contains(args[0]) && listCiphers.contains(args[1]) && args.length == 5)
            runClass(args[0], args[1], args[2], args[3], args[4]);
        else
        {
            System.out.println("Incorrect input. Available options as follows.");
            
            System.out.println("\noptions: ");
            //printListAsStringsSpacedCommas(avaiableOptions);
            for (String option : avaiableOptions){
                System.out.println(option);
            }
            System.out.println("\nciphers: " );
            printListAsStringsSpacedCommas(listCiphers);
            System.out.println("\n");
        }

        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Not enough arguments");
            System.out.println("Correct usage:");
            System.out.println("java Enigma [option] [cipher] <text to cipher> ");
            System.out.println("java Enigma [option] ROT13 [key] <text to cipher> ");
            System.out.println("java Enigma [option] Trifid [key] [period] <text to cipher>");
            System.out.println("Key and period are optional.");
            System.out.println("\noptions: ");

            for (String option : avaiableOptions){
                System.out.println(option);
            }
            System.out.println("\nciphers: " );
            printListAsStringsSpacedCommas(listCiphers);
            System.out.println("\n");
        }
        catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }
    
    private static void runClass(String option, String cipher, String message){
        String key = null;
        String period = null;
        switch (cipher){
            case "Atbash":
                if (option.equals("-e"))
                    System.out.println(Atbash.cipherUserInput(message));
                else if (option.equals("-d"))
                    System.out.println(Atbash.decodeUserInput(message));
                break;
            case "Morse":
                if (option.equals("-e"))
                    System.out.println(Morse.encryptionUserInput(message));
                else if (option.equals("-d"))
                    System.out.println(Morse.decodingUserInput(message));
                break;            
            case "ROT13":
                if (option.equals("-e")){
                    System.out.println(message + key);
                    System.out.println(ROT13.encipher(message, key));}
                else if (option.equals("-d"))
                    System.out.println(ROT13.decipher(message, key));
                break;
            case "Trifid":
                if (option.equals("-e"))
                    System.out.println(Trifid.encipher(message, key, period));
                else if (option.equals("-d"))
                    System.out.println(Trifid.decipher(message, key, period));
                break;
        }
    }

    private static void runClass(String option, String cipher, String key, String message){
        String period = null;
        switch (cipher){         
            case "ROT13":
                key = key.toUpperCase();
                if(validateInputKey(key, 26)){
                    if (option.equals("-e"))
                        System.out.println(ROT13.encipher(message, key));
                    else if (option.equals("-d"))
                        System.out.println(ROT13.decipher(message, key));
                }
                else if(!(validateInputKey(key, 26))){
                    throw new IllegalArgumentException("Trifid key requires 26 unique letters");
                }
                break;
            
            case "Trifid":
                key = key.toUpperCase();
                if(validateInputKey(key, 27)){
                    if (option.equals("-e"))
                        System.out.println(Trifid.encipher(message, key, period));
                    else if (option.equals("-d"))
                        System.out.println(Trifid.decipher(message, key, period));
                }
                else if(!(validateInputKey(key, 27))){
                    throw new IllegalArgumentException("Trifid key requires 26 unique letters and a dot");
                }
                break;

            
        }

    }
    private static void runClass(String option, String cipher, String key, String period, String message){
        switch (cipher){
            case "Trifid":
                key = key.toUpperCase();
                if(validateInputKey(key, 27) && validateInputPeriod(period)){
                    if (option.equals("-e"))
                        System.out.println(Trifid.encipher(message, key, period));
                    else if (option.equals("-d"))
                        System.out.println(Trifid.decipher(message, key, period));
                }
                else if(!(validateInputKey(key, 27))){
                    throw new IllegalArgumentException("Trifid key requires 26 unique letters and a dot");
                }
                else if(!(validateInputPeriod(period))){
                    throw new IllegalArgumentException("Period needs to be a number in range from 5 to 20\n");
                }
                break;
        }

    }

    private static void printListAsStringsSpacedCommas(List<String> list){
        boolean isFirst = true;
        for (String element : list) {
            if (isFirst) {
                System.out.print(element);
                isFirst = false;
            } else {
                System.out.print(", " + element);
            }
        }
    }



    public static String handleStringOnInput(String string){
        String newString = "";
        for(int i = 0; i < string.length(); i++){
            if(string.charAt(i) != ' ')
                newString += string.charAt(i);
        }
        return newString;
    }

    public static boolean validateInputPeriod(String string){
        if(Integer.parseInt(string) >= 5 && Integer.parseInt(string) <= 20)
            return true;
        return false;
    }

    public static boolean validateInputKey(String string, int len){
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        if(len == 27)
            alphabet += '.';
        if(string.length() != len)
            return false;
        else{
            for(int i = 0; i < len; i++){
                for(int j = i + 1; j < len; j++){
                    if(string.charAt(i) == string.charAt(j))
                        return false;
                }
            }
            for(int i = 0; i < len; i++){
                char letter = string.charAt(i);
                String sLetter = "" + letter;
                if(!(alphabet.contains(sLetter)))
                    return false;
            }
        }
        return true;
    }
    
}