package utils;

import java.util.Random;

/**
 * Generates random test data for use in tests.
 * Avoids hardcoded values and makes each test run unique.
 */
public class RandomDataGenerator {

    private static final Random random = new Random();

    /** Generate a random first name. */
    public static String generateFirstName() {
        String[] names = {"Alice", "Bob", "Carol", "David", "Eve",
                          "Frank", "Grace", "Henry", "Iris", "Jack"};
        return names[random.nextInt(names.length)];
    }

    /** Generate a random last name. */
    public static String generateLastName() {
        String[] names = {"Smith", "Johnson", "Williams", "Brown", "Jones",
                          "Garcia", "Miller", "Davis", "Wilson", "Taylor"};
        return names[random.nextInt(names.length)];
    }

    /** Generate a random UK-style post code. */
    public static String generatePostCode() {
        String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H"};
        return letters[random.nextInt(letters.length)] +
               letters[random.nextInt(letters.length)] +
               (random.nextInt(9) + 1) + " " +
               (random.nextInt(9) + 1) +
               letters[random.nextInt(letters.length)] +
               letters[random.nextInt(letters.length)];
    }

    /** Generate a random deposit amount between 100 and 1000. */
    public static String generateAmount() {
        return String.valueOf(100 + random.nextInt(900));
    }

    /** Generate a random integer between min and max inclusive. */
    public static int randomInt(int min, int max) {
        return min + random.nextInt(max - min + 1);
    }

    /** Generate a random string of given length. */
    public static String randomString(int length) {
        String chars = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
