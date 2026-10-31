package com.autonomousqa.taskboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        TextView error = findViewById(R.id.error);
        Button signIn = findViewById(R.id.sign_in);

        signIn.setOnClickListener(view -> {
            String address = email.getText().toString().trim();
            String secret = password.getText().toString();
            if (address.isEmpty()) {
                error.setText(R.string.email_required);
                return;
            }
            if (secret.isEmpty()) {
                error.setText(R.string.password_required);
                return;
            }
            if (Accounts.check(address, secret)) {
                error.setText("");
                startActivity(new Intent(this, TasksActivity.class));
                finish();
            }
        });
    }
}
