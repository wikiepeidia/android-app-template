/*
 * Copyright (c) 2025. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package group.project.androiddev.twitterclone.util;

import java.util.regex.Pattern;

public final class ValidationUtils {
    private ValidationUtils() {
    }

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public static boolean isValidEmail(String email) {
        return email != null && !email.isEmpty() && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 8;
    }

    public static boolean doPasswordsMatch(String password, String confirm) {
        return password != null && password.equals(confirm);
    }

    public static boolean isValidUsername(String username) {
        if (username == null || username.isEmpty()) return false;
        // Letters, numbers, underscores, 3-20 chars
        return username.matches("[A-Za-z0-9_]{3,20}");
    }
}
