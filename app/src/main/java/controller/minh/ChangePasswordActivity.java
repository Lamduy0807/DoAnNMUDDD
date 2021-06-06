package controller.minh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
                    Toast.makeText(ChangePasswordActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    


    public void Back(View view) {
        Intent intent= new Intent(ChangePasswordActivity.this, ProfileActivity.class);
        startActivity(intent);    }
}