package com.example.projetjee.utils;
import java.util.Random;

public class UserGenerator {

    /*
     * Method to generate a username composed of the first letter of the first name and the last name
     * If the username already exists, a number is added at the end of the username
     *
     * @param firstName : the first name of the user
     * @param lastName : the last name of the user
     * @return the generated username as a string
     */
    public static String generateUsername(String firstName, String lastName) {
        // Concatenate the first letter of the first name with the last name
        String baseUsername = firstName.toLowerCase().charAt(0) + "." + lastName.toLowerCase();
        String username = baseUsername;

        int i = 1;
        // Check if the username already exists
        //TODO : Change usernameExists when Adrien push
        while (usernameExists(username)) {
            username = baseUsername + i;
            i++;
        }
        return username;
    }

    /*
     * Method to generate a password
     *
     * @return the generated password as a string of 12 characters
     */
    public static String generatePassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
        Random random = new Random();
        int length = 12;
        StringBuilder password = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            // Add a random character from the characters string
            password.append(characters.charAt(random.nextInt(characters.length())));
        }
        return password.toString();
    }
}
