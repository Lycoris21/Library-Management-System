package utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

public class PasswordHasher {
    public static String hashPassword(String password) {
        try {
            // Handle null or empty password
            if (password == null || password.isEmpty()) {
                return "";
            }

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    public static boolean verifyPassword(String inputPassword, String storedPassword) {
        // Handle null cases
        if (inputPassword == null || storedPassword == null) {
            return false;
        }

        // Hash the input password
        String hashedInputPassword = hashPassword(inputPassword);

        // Check if the hashed input matches the stored password
        return hashedInputPassword.equals(storedPassword);
    }
}