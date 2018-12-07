import java.io.*;
import java.util.*;


public class Morse {

    private static String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ.,:\"'!?@-;()=1234567890 ";
    private static List<Character> convertAlphabet = convertStringToArray(alphabet);//java Enigma -e Morse fdf

    private static List<String> MorseEncription = Arrays.asList(
        ".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..",
        ".---", "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.",
        "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--..", ".-.-.-",
        "--..--", "---...", ".-..-.", ".----.", "-.-.--", "..--..", ".--.-.",
        "-....-", "-.-.-.", "-.--.", "-.--.-", "-...-", ".----", "..---", "...--",
        "....-", ".....", "-....", "--...", "---..", "----.", "-----", "x");

    private static String morseAlphabet = "ROUNDTABLECFGHIJKMPQSVWXYZ";
    private static List<Character> convertMorseAlphabet = convertStringToArray(morseAlphabet);

    private static List<String> MorseDeEncryption = Arrays.asList(
        "...", "..-", "..x", ".-.", ".--", ".-x", ".x.", ".x-", ".xx", "-..", "-.-",
        "-.x", "--.", "---", "--x", "-x.", "-x-", "-xx", "x..", "x.-", "x.x", "x-.",
        "x--", "x-x", "xx.", "xx-");


    /**
     * Convert String to Array List 
     * @param stringLetter string of letter
     * @return list of letter' character
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
     * Convert String to Array List 
     * @param stringLetter string of letter
     * @return list of letter' character
     */
    private static List<Character> convertMorseSymbolsStringToArray(String stringMorseSymbols) {
        List<Character> listMorseSymbols = new
                ArrayList<>();

        for (char morseSymbol : stringMorseSymbols.toCharArray()) {
            listMorseSymbols.add(morseSymbol);
        }
        return listMorseSymbols;
    }


    /**
     * Convert english letters, signs and numbers to Morse code
     * @param userInput string of letter. String is user's entering
     * @return string of Morse signs
     */
    private static String englishToMorse(String userInput){
        String encipherUserInput = "";
        char endOfMorseSymbols = 'x';
        userInput = userInput.toUpperCase();

        for (int i = 0; i < userInput.length(); i++){

            int indexLetter = convertAlphabet.indexOf(userInput.charAt(i));
            encipherUserInput += MorseEncription.get(indexLetter);
            encipherUserInput += endOfMorseSymbols;

        }
        return encipherUserInput;
    }


    /**
     * Take argument as string of morse symbols. At first We converted user's input to morse code
     * @param morseCode is string of morse symbols
     * @return list of 3 letters long strings
     */
    private static List<String> groupBy3Letters(String morseCode){
        List<String> listOf3lettersStrings =  new ArrayList<>();
        char endOfString = 'x';

        List<Character> morseCodeToListSigns = convertMorseSymbolsStringToArray(morseCode);
        if (morseCodeToListSigns.size() % 3 == 0){

            for (int i = 0; i < morseCodeToListSigns.size() - 2; i +=3){

                String threeLetter = "";
                threeLetter += morseCodeToListSigns.get(i);
                threeLetter += morseCodeToListSigns.get(i+1);
                threeLetter += morseCodeToListSigns.get(i+2);
                listOf3lettersStrings.add(threeLetter);
                }
            
            } else {
                int countOfx = morseCodeToListSigns.size() % 3;

                for (int j = 0; j < countOfx; j++){

                    morseCodeToListSigns.add(endOfString);
                
                    for (int i = 0; i < morseCodeToListSigns.size() - 2; i +=3) {
                        
                        String threeLetter = "";
                        threeLetter += morseCodeToListSigns.get(i);
                        threeLetter += morseCodeToListSigns.get(i+1);
                        threeLetter += morseCodeToListSigns.get(i+2);
                        listOf3lettersStrings.add(threeLetter);
                        }
                    }            
                }
            return listOf3lettersStrings;
    }


    /**
     * Plain english -> morse -> 3 letters list
     * @param userInput
     * @return encrypt user's input to key = convertMorseAlphabet, but at first 1. we convert 
     user's input to morse symbols, than 2. group morse symbols by three symbols 3. and at 
     the and convert 3 letters long strings to letter, using convertMorseAlphabet
     */
    public static String encryptionUserInput(String userInput){
        List<String> listOf3lettersStrings = groupBy3Letters(englishToMorse(userInput));
        String Encryption = "";

        for (String threeletter : listOf3lettersStrings) {

            if (MorseDeEncryption.contains(threeletter)) {

                int indexThreeLetter = MorseDeEncryption.indexOf(threeletter);
                Encryption += convertMorseAlphabet.get(indexThreeLetter);
            }
            
        }
        return Encryption;
    }

    /**
     * 
     * @param userInput string of Character, which user entered to decode it
     * @return string of Morse symbols
     */
    public static String decodeInputToMorse(String userInput){
        String deсodedUserInput = "";
        userInput = userInput.toUpperCase();

        for (char letter : userInput.toCharArray()){

            int indexLetter = convertMorseAlphabet.indexOf(letter);
            deсodedUserInput += MorseDeEncryption.get(indexLetter);
        }
        return deсodedUserInput;
    }

    public static List<String> splitStringMorse(String morseString){
        List<String> morseStringList = new ArrayList<>();
        String[] splitMorseString = morseString.split("x");
        ArrayList<String> arraySplitMorseString = new
        ArrayList<String>(Arrays.asList(splitMorseString));
        /* 
        for (String morseSymbol : splitMorseString){

            morseStringList.add(morseSymbol);
            */
        
        
        return arraySplitMorseString;
    }


    public static String decodingUserInput(String userInput){
        List<String> morseSymbolsList = splitStringMorse(decodeInputToMorse(userInput));    
        String deсodedUserInput = "";

        for (String morseString : morseSymbolsList){

            int indexMorseString = MorseEncription.indexOf(morseString);
            deсodedUserInput += convertAlphabet.get(indexMorseString);
        }
        return deсodedUserInput;
    }
 
}
