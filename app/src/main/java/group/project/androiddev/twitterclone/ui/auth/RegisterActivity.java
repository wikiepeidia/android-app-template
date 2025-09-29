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
import group.project.androiddev.twitterclone.di.ServiceLocator;
import group.project.androiddev.twitterclone.model.User;
import group.project.androiddev.twitterclone.util.ValidationUtils;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout emailLayout, usernameLayout, passwordLayout, confirmLayout;
    private TextInputEditText emailInput, usernameInput, passwordInput, confirmInput;
    private ProgressBar progress;
    private Button registerButton;
    private TextView goToLogin;

    private AuthRepository authRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        authRepository = ServiceLocator.getAuthRepository();

        emailLayout = findViewById(R.id.emailLayout);
        usernameLayout = findViewById(R.id.usernameLayout);
        passwordLayout = findViewById(R.id.passwordLayout);
        confirmLayout = findViewById(R.id.confirmLayout);
        emailInput = findViewById(R.id.emailInput);
        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        confirmInput = findViewById(R.id.confirmInput);
        progress = findViewById(R.id.progress);
        registerButton = findViewById(R.id.registerButton);
        goToLogin = findViewById(R.id.goToLogin);

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
        usernameInput.addTextChangedListener(watcher);
        passwordInput.addTextChangedListener(watcher);
        confirmInput.addTextChangedListener(watcher);

        registerButton.setOnClickListener(v -> tryRegister());
        goToLogin.setOnClickListener(v -> startActivity(new Intent(this, group.project.androiddev.twitterclone.ui.auth.LoginActivity.class)));
    }

    private void validateFormRealtime() {
        String email = getText(emailInput);
        String username = getText(usernameInput);
        String password = getText(passwordInput);
        String confirm = getText(confirmInput);

        if (email.isEmpty()) emailLayout.setError(getString(R.string.email_required));
        else if (!ValidationUtils.isValidEmail(email))
            emailLayout.setError(getString(R.string.email_invalid));
        else emailLayout.setError(null);

        if (username.isEmpty()) usernameLayout.setError(getString(R.string.username_required));
        else if (!ValidationUtils.isValidUsername(username))
            usernameLayout.setError(getString(R.string.username_invalid));
        else usernameLayout.setError(null);

        if (password.isEmpty()) passwordLayout.setError(getString(R.string.password_required));
        else if (!ValidationUtils.isValidPassword(password))
            passwordLayout.setError(getString(R.string.password_weak));
        else passwordLayout.setError(null);

        if (!ValidationUtils.doPasswordsMatch(password, confirm))
            confirmLayout.setError(getString(R.string.passwords_do_not_match));
        else confirmLayout.setError(null);
    }

    private void tryRegister() {
        validateFormRealtime();
        if (emailLayout.getError() != null || usernameLayout.getError() != null || passwordLayout.getError() != null || confirmLayout.getError() != null)
            return;
        String email = getText(emailInput);
        String username = getText(usernameInput);
        String password = getText(passwordInput);

        setLoading(true);
        authRepository.register(email, username, password, (ApiResult<User> result) -> {
            setLoading(false);
            if (result.success && result.data != null) {
                Intent i = new Intent(this, group.project.androiddev.twitterclone.ui.auth.VerifyEmailActivity.class);
                i.putExtra("email", email);
                startActivity(i);
                finish();
            } else if (result.errorCode != null) {
                if (group.project.androiddev.twitterclone.data.ApiErrorCodes.USER_EXISTS.equals(result.errorCode)) {
                    emailLayout.setError(getString(R.string.error_user_exists));
                } else {
                    emailLayout.setError(getString(R.string.error_generic));
                }
            } else {
                emailLayout.setError(getString(R.string.error_generic));
            }
        });
    }

    private void setLoading(boolean loading) {
        progress.setVisibility(loading ? View.VISIBLE : View.GONE);
        registerButton.setEnabled(!loading);
        emailInput.setEnabled(!loading);
        usernameInput.setEnabled(!loading);
        passwordInput.setEnabled(!loading);
        confirmInput.setEnabled(!loading);
    }

    private static String getText(TextInputEditText editText) {
        return editText.getText() == null ? "" : editText.getText().toString().trim();
    }
}
