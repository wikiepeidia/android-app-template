/*
 * Copyright (c) 2025. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package group.project.androiddev.twitterclone.di;

import android.content.Context;

import group.project.androiddev.twitterclone.data.auth.AuthRepository;
import group.project.androiddev.twitterclone.data.auth.FakeAuthApi;
import group.project.androiddev.twitterclone.data.session.SessionManager;

public final class ServiceLocator {
    private static FakeAuthApi authApi;
    private static AuthRepository authRepository;
    private static SessionManager sessionManager;

    private ServiceLocator() {
    }

    public static synchronized FakeAuthApi getAuthApi() {
        if (authApi == null) authApi = new FakeAuthApi();
        return authApi;
    }

    public static synchronized AuthRepository getAuthRepository() {
        if (authRepository == null) authRepository = new AuthRepository(getAuthApi());
        return authRepository;
    }

    public static synchronized SessionManager getSessionManager(Context context) {
        if (sessionManager == null)
            sessionManager = new SessionManager(context.getApplicationContext());
        return sessionManager;
    }
}

