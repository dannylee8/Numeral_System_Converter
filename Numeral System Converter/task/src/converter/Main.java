package converter;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String[] parts;
        String result;

        Scanner sc = new Scanner(System.in);

        try {
            int sourceRadix = sc.nextInt();
            String sourceNumberString = sc.next();
            int targetRadix = sc.nextInt();
            
            if (targetRadix < 1 || targetRadix > 36) {
                throw new Exception("error");
            }
            if (sourceNumberString.contains(".")) {
                parts = sourceNumberString.split("\\.");
                String integerPart = parts[0];
                String fractionalPart = parts[1];
                result = convertInteger(sourceRadix, targetRadix, integerPart) + "." +
                        convertFraction(sourceRadix, targetRadix, fractionalPart);
            } else {
                result = convertInteger(sourceRadix, targetRadix, sourceNumberString);
            }
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("error");
        }
    }

    static String convertInteger(int fromRadix, int toRadix, String numberString) {
        int decimalInput;
        if (fromRadix == 1) {
            decimalInput = numberString.length();
        } else {
            decimalInput = Integer.parseInt(numberString, fromRadix);
        }
        if (toRadix == 1) {
            return "1".repeat(Math.max(0, decimalInput));
        } else {
            return Integer.toString(decimalInput, toRadix);
        }
    }

    static String convertFraction(int fromRadix, int toRadix, String numberString) {
        double decimalResult = 0;
        StringBuilder result = new StringBuilder();

        if (fromRadix != 10) {
            char[] addends = numberString.toCharArray();
            for (int i = 0; i < addends.length; i++) {
                double numRepresentation = Character.getNumericValue(addends[i]);
                decimalResult += numRepresentation / Math.pow(fromRadix, i + 1.0);
            }
        } else {
            decimalResult = Double.parseDouble("0." + numberString);
        }

        int integerPart;
        for (int i = 0; i < 5; i++) {
            decimalResult = decimalResult * toRadix; // Multiply fractional part by radix
            integerPart = (int) decimalResult; // Extract integer part from result
            result.append(Character.forDigit(integerPart, toRadix)); // Convert integer part to desired radix and append to result String
            decimalResult -= integerPart; // Subtract integer part from result
        }

        return result.toString();
    }
}