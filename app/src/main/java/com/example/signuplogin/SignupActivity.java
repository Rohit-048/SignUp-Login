package com.example.signuplogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    EditText firstName, lastName, mobileNumber, email, password, confirmPassword;
    CheckBox termsAgreement;
    Button submitButton;
    TextView loginRedirectText;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize Firebase Database
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");

        // Initialize views
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        mobileNumber = findViewById(R.id.mobileNumber);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        termsAgreement = findViewById(R.id.termsAgreement);
        submitButton = findViewById(R.id.submitButton);
        loginRedirectText = findViewById(R.id.loginRedirectText);

        // Handle submit button click
        // Handle submit button click
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fname = firstName.getText().toString().trim();
                String lname = lastName.getText().toString().trim();
                String mobile = mobileNumber.getText().toString().trim();
                String mail = email.getText().toString().trim();
                String pwd = password.getText().toString().trim();
                String cnf_pwd = confirmPassword.getText().toString().trim();

                if (fname.isEmpty() || lname.isEmpty() || mobile.isEmpty() || mail.isEmpty() || pwd.isEmpty() || cnf_pwd.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else if (!pwd.equals(cnf_pwd)) {
                    Toast.makeText(SignupActivity.this, "Password does not match", Toast.LENGTH_SHORT).show();
                } else if (!termsAgreement.isChecked()) {
                    Toast.makeText(SignupActivity.this, "Please agree to terms & conditions", Toast.LENGTH_SHORT).show();
                } else {
                    // Create HelperClass instance with user data
                    HelperClass helperClass = new HelperClass(fname, lname, mobile, mail, pwd);

                    // Store user data in Firebase Realtime Database
                    reference.child(mail.replace(".", ",")).setValue(helperClass);

                    // Show signup success message
                    Toast.makeText(SignupActivity.this, "You have signed up successfully!", Toast.LENGTH_SHORT).show();

                    // Redirect to login activity
                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });


        // Handle login redirect text click
        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Redirect to login activity
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Finish current activity
            }
        });
    }

    // Method for login button click (used in XML onClick attribute)
    public void goToLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish(); // Finish current activity
    }
}
