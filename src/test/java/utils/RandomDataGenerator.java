package utils;

import java.util.Random;
import java.util.UUID;

/**
 * Generates random test data so each test run uses unique credentials.
 * This avoids conflicts on the shared demo store that resets every hour.
 */
public class RandomDataGenerator {

    private static final Random random = new Random();

    /** Generate a unique email address for registration. */
    public static String generateEmail() {
        String uid = UUID.randomUUID().toString().substring(0, 8);
        return "testuser_" + uid + "@mailinator.com";
    }

    /** Generate a random first name from a fixed list. */
    public static String generateFirstName() {
        String[] names = {"Alice", "Bob", "Carol", "David", "Eve",
                          "Frank", "Grace", "Henry", "Iris", "Jack"};
        return names[random.nextInt(names.length)];
    }

    /** Generate a random last name from a fixed list. */
    public static String generateLastName() {
        String[] names = {"Smith", "Johnson", "Williams", "Brown", "Jones",
                          "Garcia", "Miller", "Davis", "Wilson", "Taylor"};
        return names[random.nextInt(names.length)];
    }

    /** Generate a valid password that meets nopCommerce requirements. */
    public static String generatePassword() {
        // nopCommerce requires at least 6 characters
        return "Test" + random.nextInt(9000 + 1000) + "!";
    }

    /** Generate a random integer between min and max (inclusive). */
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
