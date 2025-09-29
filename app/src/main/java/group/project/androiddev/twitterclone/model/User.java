/*
 * Copyright (c) 2025. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package group.project.androiddev.twitterclone.model;

public class User {
    public final String id;
    public final String email;
    public final String username;
    public boolean emailVerified;

    public User(String id, String email, String username, boolean emailVerified) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.emailVerified = emailVerified;
    }
}

