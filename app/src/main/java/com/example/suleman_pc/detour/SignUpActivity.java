package com.example.suleman_pc.detour;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{
    EditText username,passwrd;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        findViewById(R.id.buttonSignUp).setOnClickListener(this);
        findViewById(R.id.textViewLogin).setOnClickListener(this);
        progressBar=findViewById(R.id.progressbar);
        username=findViewById(R.id.editTextEmail);
        passwrd=findViewById(R.id.editTextPassword);
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonSignUp:
                registerUser();
                break;
            case  R.id.textViewLogin:
                finish();
                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                this.finish();
                break;
        }

    }

    private void registerUser() {
        String email=username.getText().toString().trim();
        String password=passwrd.getText().toString().trim();
        if(email.isEmpty()){
            Toast.makeText(this,"Email required", Toast.LENGTH_SHORT).show();
            username.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this,"Please enter valid email", Toast.LENGTH_SHORT).show();
            username.requestFocus();
            return;
        }
        if(password.length() < 6){
            passwrd.setError("Password must be greater then 6 letters");
//            Toast.makeText(this,"Password required",Toast.LENGTH_SHORT).show();
            passwrd.requestFocus();
            return;
        }
        if(password.isEmpty()){
            Toast.makeText(this,"Password required", Toast.LENGTH_SHORT).show();
            passwrd.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    finish();
                    Intent intent=new Intent(SignUpActivity.this,LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),"Register succesfully", Toast.LENGTH_SHORT).show();

                }
                else {
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(getApplicationContext(),"You are already Registerd!", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Network error!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
