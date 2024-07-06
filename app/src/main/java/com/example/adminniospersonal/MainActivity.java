package com.example.adminniospersonal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    TextInputEditText emailInput, passwordInput;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.black));
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);

        SharedPreferences sh = getSharedPreferences("authData", MODE_PRIVATE);
        if (sh.getString("email", "").length() > 4) {
            startActivity(new Intent(getApplicationContext(), AdminDashboard.class));
        }

    }


    public void LoginAdmin(View view) {

        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();
        if (email.equals("") || password.equals("")) {
            Toast.makeText(this, "Please enter email and password properly", Toast.LENGTH_SHORT).show();
            return;
        }

        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Authenticating...");
        dialog.show();

        mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        dialog.dismiss();
                        if (task.isSuccessful()) {
                            SharedPreferences sharedPreferences = getSharedPreferences("authData",MODE_PRIVATE);

                            SharedPreferences.Editor myEdit = sharedPreferences.edit();
                            myEdit.putString("email", email);
                            myEdit.commit();

                            startActivity(new Intent(getApplicationContext(), AdminDashboard.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "Invalid email or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}