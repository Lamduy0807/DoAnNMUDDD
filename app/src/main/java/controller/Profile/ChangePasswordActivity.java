package controller.Profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

import nga.uit.edu.mytravel.R;

public class ChangePasswordActivity extends AppCompatActivity {
    private EditText edPass, edNewPass, edConfirmPass;
    private Button btnOK;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Toolbar tb = findViewById(R.id.tb);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        edPass = findViewById(R.id.edPass);
        edNewPass = findViewById(R.id.edNewPass);
        edConfirmPass = findViewById(R.id.edConfirmPass);
        btnOK=findViewById(R.id.btnchagePass);
        mAuth = FirebaseAuth.getInstance();
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtPass=edPass.getText().toString().trim();
                String txtNewPass=edNewPass.getText().toString().trim();
                String txtConfirmPass=edConfirmPass.getText().toString().trim();
                if(txtPass.isEmpty()||txtNewPass.isEmpty()||txtConfirmPass.isEmpty()){
                    Toast.makeText(ChangePasswordActivity.this, "Please fill out all field", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(txtNewPass.length()>6){
                        if(txtNewPass.equals(txtConfirmPass)){
                            changePass(txtPass,txtNewPass,v);
                        }
                        else{
                            edConfirmPass.setError("Confirm password not correct!");
                            Toast.makeText(ChangePasswordActivity.this, "Confirm password not correct", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(ChangePasswordActivity.this, "Invalid new password", Toast.LENGTH_SHORT).show();
                    }
                }
                

            }
        });


    }

    private void changePass(String txtPass,String txtNewPass,View view) {
        FirebaseUser user;
        user = mAuth.getInstance().getCurrentUser();
        final String email = user.getEmail();
        AuthCredential credential = EmailAuthProvider.getCredential(email,txtPass );

        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    user.updatePassword(txtNewPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(ChangePasswordActivity.this, "Something wrong, Try again", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(ChangePasswordActivity.this, "Change Password Successful", Toast.LENGTH_SHORT).show();
                                Back(view);
                            }
                        }
                    });
                } else {
                    //Toast.makeText(ChangePasswordActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                    String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                    switch (errorCode) {

                        case "ERROR_INVALID_CUSTOM_TOKEN":
                            Toast.makeText(ChangePasswordActivity.this, "The custom token format is incorrect. Please check the documentation.", Toast.LENGTH_LONG).show();
                            break;

                        case "ERROR_CUSTOM_TOKEN_MISMATCH":
                            Toast.makeText(ChangePasswordActivity.this, "The custom token corresponds to a different audience.", Toast.LENGTH_LONG).show();
                            break;

                        case "ERROR_INVALID_CREDENTIAL":
                            Toast.makeText(ChangePasswordActivity.this, "The supplied auth credential is malformed or has expired.", Toast.LENGTH_LONG).show();
                            break;

                        case "ERROR_INVALID_EMAIL":
                            Toast.makeText(ChangePasswordActivity.this, "The email address is badly formatted.", Toast.LENGTH_LONG).show();

                            break;

                        case "ERROR_WRONG_PASSWORD":
                            Toast.makeText(ChangePasswordActivity.this, "The password is invalid.", Toast.LENGTH_LONG).show();
                            edPass.setError("password is incorrect ");
                            edPass.requestFocus();
                            edPass.setText("");
                            break;

                        case "ERROR_USER_MISMATCH":
                            Toast.makeText(ChangePasswordActivity.this, "The supplied credentials do not correspond to the previously signed in user.", Toast.LENGTH_LONG).show();
                            break;

                        case "ERROR_REQUIRES_RECENT_LOGIN":
                            Toast.makeText(ChangePasswordActivity.this, "This operation is sensitive and requires recent authentication. Log in again before retrying this request.", Toast.LENGTH_LONG).show();
                            break;

                        case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                            Toast.makeText(ChangePasswordActivity.this, "An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address.", Toast.LENGTH_LONG).show();
                            break;


                        case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                            Toast.makeText(ChangePasswordActivity.this, "This credential is already associated with a different user account.", Toast.LENGTH_LONG).show();
                            break;

                        case "ERROR_USER_DISABLED":
                            Toast.makeText(ChangePasswordActivity.this, "The user account has been disabled by an administrator.", Toast.LENGTH_LONG).show();
                            break;

                        case "ERROR_USER_TOKEN_EXPIRED":
                            Toast.makeText(ChangePasswordActivity.this, "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
                            break;

                        case "ERROR_USER_NOT_FOUND":
                            Toast.makeText(ChangePasswordActivity.this, "There is no user record corresponding to this identifier.", Toast.LENGTH_LONG).show();
                            break;

                        case "ERROR_INVALID_USER_TOKEN":
                            Toast.makeText(ChangePasswordActivity.this, "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
                            break;

                        case "ERROR_OPERATION_NOT_ALLOWED":
                            Toast.makeText(ChangePasswordActivity.this, "This operation is not allowed. You must enable this service in the console.", Toast.LENGTH_LONG).show();
                            break;

                        case "ERROR_WEAK_PASSWORD":
                            Toast.makeText(ChangePasswordActivity.this, "The given password is invalid.", Toast.LENGTH_LONG).show();
                            edPass.setError("The password is invalid it must 6 characters at least");
                            edPass.requestFocus();
                            break;

                    }
                }
            }
        });

    }

    


    public void Back(View view) {
        Intent intent= new Intent(ChangePasswordActivity.this, ProfileActivity.class);
        startActivity(intent);    }
}