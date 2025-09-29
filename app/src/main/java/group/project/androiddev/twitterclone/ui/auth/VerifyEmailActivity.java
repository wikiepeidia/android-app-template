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
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import group.project.androiddev.twitterclone.R;
import group.project.androiddev.twitterclone.data.ApiResult;
import group.project.androiddev.twitterclone.data.auth.AuthRepository;
import group.project.androiddev.twitterclone.di.ServiceLocator;

public class VerifyEmailActivity extends AppCompatActivity {

    private AuthRepository authRepository;
    private String email;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email);

        authRepository = ServiceLocator.getAuthRepository();

        TextView message = findViewById(R.id.message);
        Button resendButton = findViewById(R.id.resendButton);
        Button verifiedButton = findViewById(R.id.verifiedButton);

        email = getIntent().getStringExtra("email");
        if (email == null) email = "";
        message.setText(getString(R.string.verify_email_message, email));

        resendButton.setOnClickListener(v -> resend(resendButton));
        verifiedButton.setOnClickListener(v -> markVerified());
    }

    private void resend(Button resendButton) {
        setLoading(resendButton, true);
        authRepository.resendVerification(email, (ApiResult<Void> res) -> {
            setLoading(resendButton, false);
            resendButton.setEnabled(false);
            resendButton.postDelayed(() -> resendButton.setEnabled(true), 2000);
        });
    }

    private void markVerified() {
        authRepository.markEmailVerified(email, (ApiResult<Void> res) -> {
            Intent i = new Intent(this, group.project.androiddev.twitterclone.ui.auth.LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
            finish();
        });
    }

    private void setLoading(Button resendButton, boolean loading) {
        resendButton.setEnabled(!loading);
    }
}
