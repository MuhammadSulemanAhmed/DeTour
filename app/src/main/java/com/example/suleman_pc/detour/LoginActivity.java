package com.example.suleman_pc.detour;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suleman_pc.detour.Model.Main;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.logging.LogRecord;

public class LoginActivity extends Activity {
    private static final int RC_SIGN_IN = 11;
    Button skip;
    private FirebaseAuth mAuth;
    EditText editTextemail, editTextpassword;
    ProgressBar progressBar;
    TextView text;
    //    Button facebooksign;
    CallbackManager callbackManager;
    ProgressDialog progressDialog;
    Dialog passwrodResetDialoge;
    ImageView imgfb;
    TextView dob, emailfb, frndz,tv_forgotpassword;
    GoogleSignInClient mGoogleSignInClient;
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        callbackManager.onActivityResult(requestCode, resultCode, data);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        progressBar = findViewById(R.id.progressbar);
        mAuth = FirebaseAuth.getInstance();
        editTextemail = findViewById(R.id.editTextEmail);
        passwrodResetDialoge=new Dialog(LoginActivity.this);
//        imgfb = findViewById(R.id.profilePic);
//        dob = findViewById(R.id.dobfb);
//        emailfb = findViewById(R.id.emailfb);
//        frndz = findViewById(R.id.frndfb);
        text = findViewById(R.id.btnSignup);
        tv_forgotpassword=findViewById(R.id.tv_forgotpasswrod);
        editTextpassword = findViewById(R.id.editTextPassword);
        progressDialog = new ProgressDialog(LoginActivity.this);
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = findViewById(R.id.facebookSignUp);
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        tv_forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             resetDialoge();
            }
        });
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));
        if (AccessToken.getCurrentAccessToken() != null) {
//            emailfb.setText(AccessToken.getCurrentAccessToken().getUserId());
        }
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                progressDialog.setMessage("Retrivingdata");
                progressDialog.show();
//                String accesstoken = loginResult.getAccessToken().getToken();
//                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
//                    @Override
//                    public void onCompleted(JSONObject object, GraphResponse response) {
//                        progressDialog.dismiss();
//                        Log.d("response",object.toString());
//                       getData(object);
//                    }
//                });
//                Bundle parameters=new Bundle();
//                parameters.putString("fields","id,email,birthday,friends");
//                graphRequest.setParameters(parameters);
//                graphRequest.executeAsync();
//
//            }
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        skip = findViewById(R.id.skip_signup);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
                editTextemail.onEditorAction(EditorInfo.IME_ACTION_DONE);
                editTextpassword.onEditorAction(EditorInfo.IME_ACTION_DONE);
                editTextemail.setEnabled(false);
                editTextpassword.setEnabled(false);
                skip.setEnabled(false);
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
            }
        });
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                finish();
            }
        });
//        printKeyHash();
    }

    private void resetDialoge() {
        passwrodResetDialoge.setContentView(R.layout.password_reset_dialoge);
        passwrodResetDialoge.show();
        final EditText resetEmail=passwrodResetDialoge.findViewById(R.id.edittext_resetemail);
        final Button sendbtn=passwrodResetDialoge.findViewById(R.id.send_resetEmail);

        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userEmail=resetEmail.getText().toString().trim();
                if(userEmail.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please enter Email!",Toast.LENGTH_SHORT).show();
                }
                else {
                    FirebaseAuth auth = FirebaseAuth.getInstance();
//                String emailAddress = "user@example.com";

                    auth.sendPasswordResetEmail(userEmail)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        passwrodResetDialoge.dismiss();
//                                    Log.d(TAG, "Email sent.");
                                        Toast.makeText(getApplicationContext(),"Password reset Email is send " +
                                                "Please check your Email!",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    private void getData(JSONObject object) {
        try {
            URL profile_picture = new URL("https://graph.facebook.com/" + object.getString("id") + "picture?width=250&height=250");

            Picasso.with(this).load(profile_picture.toString()).into(imgfb);
            dob.setText(object.getString("birthday"));
            frndz.setText(object.getString("Friends") + object.getJSONObject("friends").getJSONObject("summary").getString("total_count"));
            emailfb.setText(object.getString("email"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


//    private void printKeyHash() {
//        try {
//            PackageInfo packageInfo = getPackageManager().getPackageInfo("com.example.suleman_pc.detour", PackageManager.GET_SIGNATURES);
//            for (Signature signature : packageInfo.signatures) {
//
//                MessageDigest messageDigest = MessageDigest.getInstance("SHA");
//                messageDigest.update(signature.toByteArray());
//                Log.d("KeyHash", Base64.encodeToString(messageDigest.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    private void userLogin() {
        String email = editTextemail.getText().toString().trim();
        String password = editTextpassword.getText().toString().trim();
        if (email.isEmpty()) {
            Toast.makeText(this, "Email required", Toast.LENGTH_SHORT).show();
            editTextemail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please enter valid email", Toast.LENGTH_SHORT).show();
            editTextemail.requestFocus();
            return;
        }
        if (password.length() < 6) {
            editTextpassword.setError("Password must be greater then 6 letters");
//            Toast.makeText(this,"Password required",Toast.LENGTH_SHORT).show();
            editTextpassword.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            Toast.makeText(this, "Password required", Toast.LENGTH_SHORT).show();
            editTextpassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();

                } else {
                    editTextemail.setEnabled(true);
                    editTextpassword.setEnabled(true);
                    skip.setEnabled(true);
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void handleFacebookAccessToken(AccessToken token) {
//        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        } else {

                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
        progressDialog.setMessage("Signing in...");
        progressDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.d("error", "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("error1", "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("su", "signInWithCredential:success");
                            progressDialog.dismiss();
                            addUsertoDatabase();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d("fail", "signInWithCredential:failure", task.getException());
//                            Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
//                            updateUI(null);
                            Toast.makeText(getApplicationContext(), "Network Error! Failed to SignUp with Google", Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }

    private void addUsertoDatabase() {
        FirebaseUser user = mAuth.getCurrentUser();
        String userId=user.getUid();
        String userName=user.getDisplayName();
        String userEmail=user.getEmail();
        double lati=0;
        double longi=0;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("users");
//        myRef.setValue("DeTour");
        database.getReference("App_Title").setValue("DeTour");
        myRef.child(userId).child("name").setValue(userName);
        myRef.child(userId).child("email").setValue(userEmail);
        myRef.child(userId).child("latitude").setValue(lati);
        myRef.child(userId).child("longitude").setValue(longi);
    }


}
