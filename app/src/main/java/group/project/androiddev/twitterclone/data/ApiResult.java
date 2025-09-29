/*
 * Copyright (c) 2025. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package group.project.androiddev.twitterclone.data;

import androidx.annotation.Nullable;

public class ApiResult<T> {
    public final boolean success;
    @Nullable
    public final T data;
    @Nullable
    public final String errorCode;
    @Nullable
    public final String message;

    private ApiResult(boolean success, @Nullable T data, @Nullable String errorCode, @Nullable String message) {
        this.success = success;
        this.data = data;
        this.errorCode = errorCode;
        this.message = message;
    }

    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<>(true, data, null, null);
    }

    public static <T> ApiResult<T> error(String code, String message) {
        return new ApiResult<>(false, null, code, message);
    }
}

