package com.example.suleman_pc.detour;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class SignUpActivity extends Activity implements View.OnClickListener {
    private static final int CHOOSE_IMAGE = 101;
    EditText userEmail, passwrd, confirmpassword;
    FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign_up);
        findViewById(R.id.buttonSignUp).setOnClickListener(this);
        findViewById(R.id.textViewLogin).setOnClickListener(this);
        progressBar = findViewById(R.id.progressbar);
        userEmail = findViewById(R.id.editTextEmail);
        passwrd = findViewById(R.id.editTextPassword);
        confirmpassword = findViewById(R.id.editTextConfirmPassword);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSignUp:
                userEmail.onEditorAction(EditorInfo.IME_ACTION_DONE);
                passwrd.onEditorAction(EditorInfo.IME_ACTION_DONE);
                registerUser();
                break;
            case R.id.textViewLogin:
                finish();
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                this.finish();
                break;
        }

    }

    private void registerUser() {
        userEmail.setEnabled(false);
        passwrd.setEnabled(false);
        confirmpassword.setEnabled(false);
        String email = userEmail.getText().toString().trim();
        String password = passwrd.getText().toString().trim();
        String confirmpassword1 = confirmpassword.getText().toString().trim();

        if (email.isEmpty()) {
            Toast.makeText(this, "Email required", Toast.LENGTH_SHORT).show();
            userEmail.requestFocus();
            userEmail.setEnabled(true);
            passwrd.setEnabled(true);
            confirmpassword.setEnabled(true);
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please enter valid email", Toast.LENGTH_SHORT).show();
            userEmail.requestFocus();
        } else if (password.length() < 6) {
            passwrd.setError("Password must be greater then 6 letters");
//            Toast.makeText(this,"Password required",Toast.LENGTH_SHORT).show();
            passwrd.requestFocus();
            userEmail.setEnabled(true);
            passwrd.setEnabled(true);
            confirmpassword.setEnabled(true);
        } else if (password.isEmpty()) {
            Toast.makeText(this, "Password required", Toast.LENGTH_SHORT).show();
            passwrd.requestFocus();

        } else if (confirmpassword1.isEmpty()) {
            Toast.makeText(this, "Please enter confirm Password!", Toast.LENGTH_SHORT).show();
            confirmpassword.requestFocus();
            userEmail.setEnabled(true);
            passwrd.setEnabled(true);
            confirmpassword.setEnabled(true);

        } else if (!password.equals(confirmpassword1)) {
            Toast.makeText(this, "Password does not match ,Please try again", Toast.LENGTH_LONG).show();
            confirmpassword.setText("");
            passwrd.setText("");
            userEmail.setEnabled(true);
            passwrd.setEnabled(true);
            confirmpassword.setEnabled(true);
        } else {
            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                finish();

                                Intent intent = new Intent(SignUpActivity.this, ProfileActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
//                            Toast.makeText(getApplicationContext(), "Register succesfully", Toast.LENGTH_SHORT).show();
                            } else {
                                if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                    userEmail.setEnabled(true);
                                    passwrd.setEnabled(true);
                                    Toast.makeText(getApplicationContext(), "You are already Registerd!", Toast.LENGTH_SHORT).show();

                                } else {
                                    userEmail.setEnabled(true);
                                    passwrd.setEnabled(true);
                                    Toast.makeText(getApplicationContext(), "Network error!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
        }
    }

}
