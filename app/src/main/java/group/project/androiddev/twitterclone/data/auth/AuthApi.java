/*
 * Copyright (c) 2025. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package group.project.androiddev.twitterclone.data.auth;

import group.project.androiddev.twitterclone.data.ApiResult;
import group.project.androiddev.twitterclone.model.User;

public interface AuthApi {
    ApiResult<User> register(String email, String username, String password);

    ApiResult<User> login(String email, String password);

    ApiResult<Void> resendVerification(String email);

    ApiResult<Void> markEmailVerified(String email);

    boolean isEmailVerified(String email);
}

