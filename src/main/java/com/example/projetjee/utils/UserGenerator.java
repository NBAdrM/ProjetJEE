package com.example.projetjee.utils;
import java.util.Random;

public class UserGenerator {

    /*
     * Method to generate a username based on the first name and last name
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
