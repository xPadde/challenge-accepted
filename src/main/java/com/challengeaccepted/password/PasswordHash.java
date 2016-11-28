package com.challengeaccepted.password;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 * Class responsible for handling password.
 */
public class PasswordHash {

    /**
     * Generate the salted and hashed password.
     *
     * @param password is the password to salt and hash.
     * @return the number of iterations the SecretKeyFactory use, a salt and the password (hexed).
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static String generateHashedPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        int iterations = 1000;
        char[] charPassword = password.toCharArray();
        byte[] salt = getSalt();

        PBEKeySpec spec = new PBEKeySpec(charPassword, salt, iterations, 64 * 8);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hashedPassword = secretKeyFactory.generateSecret(spec).getEncoded();

        return iterations + ":" + toHex(salt) + ":" + toHex(hashedPassword);
    }

    /**
     * Method used for validating user provided password through Spring Security layer.
     *
     * @param password is the password the user provided.
     * @param salt is the salt connected to the user.
     * @return the generated password for later validation.
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static String generatePasswordForSpringSecValidation(String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        int iterations = 1000;
        char[] charPassword = password.toCharArray();
        byte[] saltAsByte = fromHex(salt);

        PBEKeySpec spec = new PBEKeySpec(charPassword, saltAsByte, iterations, 64 * 8);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hashedPassword = secretKeyFactory.generateSecret(spec).getEncoded();

        return iterations + ":" + toHex(saltAsByte) + ":" + toHex(hashedPassword);
    }


    /**
     * Compare the user provided password with the password in the database.
     *
     * @param inputPassword  is the user provided password.
     * @param storedPassword is the password stored in the database.
     * @return 0 (true) if the password provided is correct, else false.
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static boolean validateHashedPassword(String inputPassword, String storedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String[] passwordParts = storedPassword.split(":");
        int iterations = Integer.parseInt(passwordParts[0]);
        char[] charPassword = inputPassword.toCharArray();
        byte[] salt = fromHex(passwordParts[1]);
        byte[] storedPasswordHashed = fromHex(passwordParts[2]);

        PBEKeySpec pbeKeySpec = new PBEKeySpec(charPassword, salt, iterations, storedPasswordHashed.length * 8);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] typedPasswordHashed = secretKeyFactory.generateSecret(pbeKeySpec).getEncoded();

        return isHashedPasswordsEqual(storedPasswordHashed, typedPasswordHashed);
    }

    public static boolean isHashedPasswordsEqual(byte[] storedPasswordHashed, byte[] typedPasswordHashed) {
        int diff = storedPasswordHashed.length ^ typedPasswordHashed.length;

        for (int i = 0; i < storedPasswordHashed.length && typedPasswordHashed.length < i; i++) {
            diff |= storedPasswordHashed[i] ^ typedPasswordHashed[i];
        }

        return diff == 0;
    }

    /**
     * Translate String to hexadecimals.
     *
     * @param hex is the String to translate.
     * @return the String in hexadecimal.
     */
    public static byte[] fromHex(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

    /**
     * Translate hexadecimals to String,
     *
     * @param array is the hexadecimals to translate.
     * @return the hexadecimals in String.
     */
    private static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();

        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        }
        return hex;
    }

    /**
     * Generate salt.
     *
     * @return the salt.
     * @throws NoSuchAlgorithmException
     */
    private static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return salt;
    }

}