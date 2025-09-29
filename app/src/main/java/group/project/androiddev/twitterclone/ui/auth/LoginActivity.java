/*
 * Copyright (c) 2025. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package group.project.androiddev.twitterclone.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import group.project.androiddev.twitterclone.R;
import group.project.androiddev.twitterclone.data.ApiErrorCodes;
import group.project.androiddev.twitterclone.data.ApiResult;
import group.project.androiddev.twitterclone.data.auth.AuthRepository;
import group.project.androiddev.twitterclone.data.session.SessionManager;
import group.project.androiddev.twitterclone.di.ServiceLocator;
import group.project.androiddev.twitterclone.model.User;
import group.project.androiddev.twitterclone.util.ValidationUtils;
import group.project.androiddev.twitterclone.MainActivity;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout emailLayout, passwordLayout;
    private TextInputEditText emailInput, passwordInput;
    private ProgressBar progress;
    private Button loginButton;
    private TextView goToRegister;

    private AuthRepository authRepository;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        authRepository = ServiceLocator.getAuthRepository();
        sessionManager = ServiceLocator.getSessionManager(this);

        if (sessionManager.isLoggedIn()) {
            goToHome();
            return;
        }

        emailLayout = findViewById(R.id.emailLayout);
        passwordLayout = findViewById(R.id.passwordLayout);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        progress = findViewById(R.id.progress);
        loginButton = findViewById(R.id.loginButton);
        goToRegister = findViewById(R.id.goToRegister);

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                validateFormRealtime();
            }
        };
        emailInput.addTextChangedListener(watcher);
        passwordInput.addTextChangedListener(watcher);

        loginButton.setOnClickListener(v -> tryLogin());
        goToRegister.setOnClickListener(v -> startActivity(new Intent(this, group.project.androiddev.twitterclone.ui.auth.RegisterActivity.class)));
    }

    private void validateFormRealtime() {
        String email = getText(emailInput);
        String password = getText(passwordInput);

        if (email.isEmpty()) {
            emailLayout.setError(getString(R.string.email_required));
        } else if (!ValidationUtils.isValidEmail(email)) {
            emailLayout.setError(getString(R.string.email_invalid));
        } else {
            emailLayout.setError(null);
        }

        if (password.isEmpty()) {
            passwordLayout.setError(getString(R.string.password_required));
        } else if (!ValidationUtils.isValidPassword(password)) {
            passwordLayout.setError(getString(R.string.password_weak));
        } else {
            passwordLayout.setError(null);
        }
    }

    private void tryLogin() {
        validateFormRealtime();
        String email = getText(emailInput);
        String password = getText(passwordInput);
        if (emailLayout.getError() != null || passwordLayout.getError() != null) return;

        setLoading(true);
        authRepository.login(email, password, (ApiResult<User> result) -> {
            setLoading(false);
            if (result.success && result.data != null) {
                sessionManager.saveUser(result.data.id, result.data.email, result.data.username);
                goToHome();
            } else if (result.errorCode != null) {
                switch (result.errorCode) {
                    case ApiErrorCodes.EMAIL_NOT_VERIFIED:
                        Intent i = new Intent(this, group.project.androiddev.twitterclone.ui.auth.VerifyEmailActivity.class);
                        i.putExtra("email", email);
                        startActivity(i);
                        break;
                    case ApiErrorCodes.WRONG_CREDENTIALS:
                        passwordLayout.setError(getString(R.string.error_wrong_credentials));
                        break;
                    default:
                        passwordLayout.setError(getString(R.string.error_generic));
                        break;
                }
            } else {
                passwordLayout.setError(getString(R.string.error_generic));
            }
        });
    }

    private void setLoading(boolean loading) {
        progress.setVisibility(loading ? View.VISIBLE : View.GONE);
        loginButton.setEnabled(!loading);
        emailInput.setEnabled(!loading);
        passwordInput.setEnabled(!loading);
    }

    private void goToHome() {
        Intent home = new Intent(this, MainActivity.class);
        home.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(home);
    }

    private static String getText(TextInputEditText editText) {
        return editText.getText() == null ? "" : editText.getText().toString().trim();
    }
}
