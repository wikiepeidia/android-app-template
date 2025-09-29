/*
 * Copyright (c) 2025. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package group.project.androiddev.twitterclone.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class ValidationUtilsTest {

    @Test
    public void emailValidation() {
        assertTrue(ValidationUtils.isValidEmail("test@example.com"));
        assertFalse(ValidationUtils.isValidEmail("bad@"));
        assertFalse(ValidationUtils.isValidEmail(""));
    }

    @Test
    public void passwordValidation() {
        assertTrue(ValidationUtils.isValidPassword("password123"));
        assertFalse(ValidationUtils.isValidPassword("short"));
        assertFalse(ValidationUtils.isValidPassword(null));
    }

    @Test
    public void usernameValidation() {
        assertTrue(ValidationUtils.isValidUsername("john_doe"));
        assertFalse(ValidationUtils.isValidUsername("a"));
        assertFalse(ValidationUtils.isValidUsername("bad name"));
    }

    @Test
    public void passwordsMatch() {
        assertTrue(ValidationUtils.doPasswordsMatch("abc12345", "abc12345"));
        assertFalse(ValidationUtils.doPasswordsMatch("abc12345", "abc123"));
        assertFalse(ValidationUtils.doPasswordsMatch(null, "abc123"));
    }
}

