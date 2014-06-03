package utils;

public class Utils {

    public static String formatNumberWithDots(String number) {
        StringBuilder result = new StringBuilder();
        int cont = 0;
        for (char digit : new StringBuilder(number).reverse().toString().toCharArray()) {
            if (cont == 3) {
                result.insert(0, ".");
                cont = 0;
            }
            cont++;
            result.insert(0, digit);
        }
        return result.toString();
    }
}
