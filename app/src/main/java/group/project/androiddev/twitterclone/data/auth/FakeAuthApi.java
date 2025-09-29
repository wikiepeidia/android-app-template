/*
 * Copyright (c) 2025. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package group.project.androiddev.twitterclone.data.auth;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import group.project.androiddev.twitterclone.data.ApiErrorCodes;
import group.project.androiddev.twitterclone.data.ApiResult;
import group.project.androiddev.twitterclone.model.User;
import group.project.androiddev.twitterclone.util.ValidationUtils;

public class FakeAuthApi implements AuthApi {
    private final Map<String, String> emailToPassword = new HashMap<>();
    private final Map<String, User> users = new HashMap<>(); // key: email

    @Override
    public synchronized ApiResult<User> register(String email, String username, String password) {
        if (!ValidationUtils.isValidEmail(email) || !ValidationUtils.isValidPassword(password) || !ValidationUtils.isValidUsername(username)) {
            return ApiResult.error(ApiErrorCodes.UNKNOWN, "Invalid input");
        }
        if (emailToPassword.containsKey(email)) {
            return ApiResult.error(ApiErrorCodes.USER_EXISTS, "User exists");
        }
        String id = UUID.randomUUID().toString();
        User user = new User(id, email, username, false);
        users.put(email, user);
        emailToPassword.put(email, password);
        // Pretend we sent an email
        return ApiResult.success(user);
    }

    @Override
    public synchronized ApiResult<User> login(String email, String password) {
        String stored = emailToPassword.get(email);
        if (stored == null || !stored.equals(password)) {
            return ApiResult.error(ApiErrorCodes.WRONG_CREDENTIALS, "Wrong credentials");
        }
        User user = users.get(email);
        if (user == null) {
            return ApiResult.error(ApiErrorCodes.UNKNOWN, "User missing");
        }
        if (!user.emailVerified) {
            return ApiResult.error(ApiErrorCodes.EMAIL_NOT_VERIFIED, "Email not verified");
        }
        return ApiResult.success(user);
    }

    @Override
    public synchronized ApiResult<Void> resendVerification(String email) {
        if (!users.containsKey(email)) {
            return ApiResult.error(ApiErrorCodes.UNKNOWN, "No such user");
        }
        // Pretend email re-sent
        return ApiResult.success(null);
    }

    @Override
    public synchronized ApiResult<Void> markEmailVerified(String email) {
        User user = users.get(email);
        if (user == null) return ApiResult.error(ApiErrorCodes.UNKNOWN, "No such user");
        user.emailVerified = true;
        return ApiResult.success(null);
    }

    @Override
    public synchronized boolean isEmailVerified(String email) {
        User user = users.get(email);
        return user != null && user.emailVerified;
    }
}
