/*
 * Copyright (c) 2025. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package group.project.androiddev.twitterclone.data.auth;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import group.project.androiddev.twitterclone.data.ApiResult;
import group.project.androiddev.twitterclone.model.User;

public class AuthRepository {
    public interface Callback<T> {
        void onResult(ApiResult<T> result);
    }

    private final AuthApi api;
    private final ExecutorService io = Executors.newSingleThreadExecutor();
    private final Handler main = new Handler(Looper.getMainLooper());

    public AuthRepository(AuthApi api) {
        this.api = api;
    }

    public void register(String email, String username, String password, Callback<User> callback) {
        io.execute(() -> {
            ApiResult<User> res = api.register(email, username, password);
            main.post(() -> callback.onResult(res));
        });
    }

    public void login(String email, String password, Callback<User> callback) {
        io.execute(() -> {
            ApiResult<User> res = api.login(email, password);
            main.post(() -> callback.onResult(res));
        });
    }

    public void resendVerification(String email, Callback<Void> callback) {
        io.execute(() -> {
            ApiResult<Void> res = api.resendVerification(email);
            main.post(() -> callback.onResult(res));
        });
    }

    public void markEmailVerified(String email, Callback<Void> callback) {
        io.execute(() -> {
            ApiResult<Void> res = api.markEmailVerified(email);
            main.post(() -> callback.onResult(res));
        });
    }

    public boolean isEmailVerifiedSync(String email) {
        return api.isEmailVerified(email);
    }
}

