import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GlobalConverter {

    // Fonction universelle
    public static void main(String[] args) {
        if (args.length == 0) System.err.println("Usage : GlobalConverter [BASE] [ARGUMENT]\nBase : Hexadecimal, octal, binary, text, decimal");
        else if (args.length != 1) {
          myOptions(args);
        }
        else {
            System.err.println("Il manque l'argument à convertir !");
        }
    }

    public static List<Integer> myASCII(String value) {
        List<Integer> asciiValue = new ArrayList<>();

        for (int i = 0; i < value.length(); i++) {
            char car = value.charAt(i);
            int tmp = (int) car;
            asciiValue.add(tmp);
        }
        return asciiValue;
    }

    public static void myOptions(String[] arguments) {
        List<String> options = new ArrayList<>(Arrays.asList("-h", "hexadecimal", "-t", "text", "-o", "octal", "-d", "decimal", "-b" ,"binary"));

        if (!arguments[1].matches("^[a-zA-Z0-9 ]+$")) System.err.println("L'argument est invalide");
        else if (options.contains(arguments[0])) {
            hexaHandling(arguments);
            textHandling(arguments);
            octalHandling(arguments);
            decimalHandling(arguments);
            binaryHandling(arguments);
        }
        else {
            System.err.println("Le base numérique n'existe pas");
        }
    }

    // Fonction Hexadecimal

    public static String myHexadecimal(int value) {
        StringBuilder hexBuilder = new StringBuilder();

        while(value > 0) {
            int reste = value % 16;

            if (reste < 10) {
                hexBuilder.insert(0, reste);
            } else {
                char hexDegit = (char) ('A' + (reste - 10));
                hexBuilder.insert(0, hexDegit);
            }
            value = value / 16;
        }
        return hexBuilder.toString();
    }

    public static String myReverseHexadecimal(String hexadecimalString) {
        StringBuilder textBuilder = new StringBuilder();
        String[] hexadecimalGroups = hexadecimalString.split(" ");

        for (String hexadecimalGroup : hexadecimalGroups) {
            int decimalValue = 0;

            for (int i = 0; i < hexadecimalGroup.length(); i++) {
                char digit = hexadecimalGroup.charAt(i);
                int digitValue = getDigitValue(digit);
                decimalValue = decimalValue * 16 + digitValue;
            }

            char character = (char) decimalValue;
            textBuilder.append(character);
        }

        return textBuilder.toString();
    }

    public static int getDigitValue(char digit) {
        if (digit >= '0' && digit <= '9') {
            return digit - '0';
        } else if (digit >= 'A' && digit <= 'F') {
            return 10 + digit - 'A';
        } else if (digit >= 'a' && digit <= 'f') {
            return 10 + digit - 'a';
        } else {
            throw new IllegalArgumentException("Invalide digit hexadecimal: " + digit);
        }
    }

    public static void hexaHandling(String[] arguments) {
        if (arguments[0].equals("-h") || arguments[0].equals("hexadecimal") ) {
            StringBuilder hexBuilder = new StringBuilder();
            for (int i : myASCII(arguments[1])) {
                String hex = myHexadecimal(i);
                hexBuilder.append(hex).append(" ");
            }
            String hexResult = hexBuilder.toString().trim();
            System.out.println(hexResult);
        }
    }

    // Fonction Text

    public static String myText(String value) {

        if (value.matches("[01 ]+")) return myReverseBinary(value);
        else if (value.matches("[0-7 ]+")) return myReverseOctal(value);
        else if (value.matches("[0-9 ]+")) return myReverseDecimal(value);
        else if (value.matches("[0-9A-Fa-f ]+")) return myReverseHexadecimal(value);
        else return "no matches";
    }

    public static void textHandling(String[] arguments) {
        if (arguments[0].equals("-t") || arguments[0].equals("text") ) {
            String test = myText(arguments[1]);
            String textString = test.toString().trim();
            System.out.println(textString);
        }
    }

    // Fonction Octal

    public static String myOctal(int value) {
        StringBuilder octalBuilder = new StringBuilder();

        while(value > 0) {
            int reste = value % 8;
            octalBuilder.insert(0, reste);
            value = value / 8;
        }
        return octalBuilder.toString();
    }

    public static String myReverseOctal(String octalString) {
        StringBuilder textBuilder = new StringBuilder();
        String[] octalGroups = octalString.split(" ");

        for (String octalGroup : octalGroups) {
            int decimalValue = 0;

            for (int i = 0; i < octalGroup.length(); i++) {
                char digit = octalGroup.charAt(i);
                int digitValue = digit - '0';
                decimalValue = decimalValue * 8 + digitValue;
            }
            char character = (char) decimalValue;
            textBuilder.append(character);
        }
        return textBuilder.toString();
    }

    public static void octalHandling(String[] arguments) {
        if (arguments[0].equals("-o") || arguments[0].equals("octal") ) {
            StringBuilder octalBuilder = new StringBuilder();
            for (int i : myASCII(arguments[1])) {
                String octal = myOctal(i);
                octalBuilder.append(octal).append(" ");
            }
            String octalResult = octalBuilder.toString().trim();
            System.out.println(octalResult);
        }
    }

    // Fonction Decimal

    public static String myReverseDecimal(String decimalString) {
        StringBuilder textBuilder = new StringBuilder();

        String[] decimalGroups = decimalString.split(" ");

        for (String decimalGroup : decimalGroups) {
            int decimalValue = 0;

            for (int i = 0; i < decimalGroup.length(); i++) {
                char digit = decimalGroup.charAt(i);
                decimalValue = decimalValue * 10 + (digit - '0');
            }

            char character = (char) decimalValue;
            textBuilder.append(character);
        }
        return textBuilder.toString();
    }

    public static void decimalHandling(String[] arguments) {
        if (arguments[0].equals("-d") || arguments[0].equals("decimal") ) {
            StringBuilder decimalBuilder = new StringBuilder();
            for (int i : myASCII(arguments[1])) {
                decimalBuilder.append(i).append(" ");
            }
            String decimalResult = decimalBuilder.toString().trim();
            System.out.println(decimalResult);
        }
    }

    // Fonction Binary

    public static String myBinary(int value) {
        StringBuilder binaryBuilder = new StringBuilder();
        int fixedLenght = 8;

            for(int i = fixedLenght - 1; i >= 0; i--) {
                int bit = (value >> i) & 1;
                binaryBuilder.append(bit);
            }
            return binaryBuilder.toString();
        }

    public static String myReverseBinary(String binaryString) {
        StringBuilder textBuilder = new StringBuilder();

        String[] binaryGroups = binaryString.split(" ");

        for (String binaryGroup : binaryGroups) {
            int decimalValue = 0;

            for (int i = 0; i < binaryGroup.length(); i++) {
                char bit = binaryGroup.charAt(i);
                decimalValue = decimalValue * 2 + (bit - '0');
            }
            char character = (char) decimalValue;
            textBuilder.append(character);
        }
        return textBuilder.toString();
    }

    public static void binaryHandling (String[] arguments) {
        if (arguments[0].equals("-b") || arguments[0].equals("binary") ) {
            StringBuilder binaryBuilder = new StringBuilder();
            for (int i : myASCII(arguments[1])) {
                String binary = myBinary(i);
                binaryBuilder.append(binary).append(" ");
            }
            String binaryResult = binaryBuilder.toString().trim();
            System.out.println(binaryResult);
        }
    }
}