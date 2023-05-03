import javax.swing.*;

public class test extends JFrame {

    public static void main(String[] args) {






    }

    public static boolean hasInvalidCharacters(String str) {
        for (int i = 0; i < str.length(); i++) {
            int codepoint = str.codePointAt(i);
            if (codepoint < 32 || codepoint > 126) {
                return true; // Invalid character found
            }
            if (Character.isSupplementaryCodePoint(codepoint)) {
                i++; // Skip second code unit of a supplementary character
            }
        }
        return false; // No invalid characters found
    }
}
