package controller.Home;

import android.app.Dialog;
import android.content.Intent;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import Model.User;
import controller.Admin.AdminScreenActivity;
import nga.uit.edu.mytravel.R;

public class LoginFragment extends Fragment {
    EditText etMail, etPW;
    TextView btnforgot;
    float v = 0;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    ExtendedFloatingActionButton btn;

    ///Phân Quyền
    String strUID="";
    private DatabaseReference mData;
    public  String permission = "";


    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater .inflate(R.layout.login_tab,container,false);

        etMail = root.findViewById(R.id.Email);
        etPW = root.findViewById(R.id.PW);
        btn = root.findViewById(R.id.btnLogin);
        SetAnimation();
        btnforgot = root.findViewById(R.id.forgot);
        btnforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        btn.setOnClickListener(view->{
            login();

        });
        return root;
    }
    private void openDialog() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_forgotpassword);

        Window window =dialog.getWindow();
        if(window == null)
        {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowattribute = window.getAttributes();
        windowattribute.gravity = Gravity.CENTER;
        window.setAttributes(windowattribute);

        EditText etReEmail = dialog.findViewById(R.id.reEmail);
        Button btnCancel = dialog.findViewById(R.id.btncancel);
        Button btnSend = dialog.findViewById(R.id.btnsend);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = etReEmail.getText().toString();
                mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(getContext(),"Check your email to reset your password",Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }else {
                            Toast.makeText(getContext(),"Try again, something went wrong",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
        dialog.show();
    }
    private void checkUserPermission(FirebaseUser user)
    {
        mData = FirebaseDatabase.getInstance().getReference("Users");

        strUID = user.getUid();
        mData.child(strUID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User mUser = snapshot.getValue(User.class);
                if(mUser!=null) {
                    permission = mUser.getPermission();

                }
                if(permission.equals("admin"))
                {
                    startActivity(new Intent(getContext(), AdminScreenActivity.class));

                }
                else {
                    startActivity(new Intent(getContext(), HomeScreen.class));
                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void login() {
        if(!validateEmail()||!validatePass())
            return;
        else {
            String email = etMail.getText().toString().trim();
            String PW = etPW.getText().toString().trim();

            mAuth.signInWithEmailAndPassword(email, PW).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        Log.d("test", "success");
                        Toast.makeText(getActivity(), "Logining...", Toast.LENGTH_LONG).show();
                        /*startActivity(new Intent(getContext(), HomeScreen.class));*/
                        FirebaseUser user = mAuth.getCurrentUser();
                        checkUserPermission(user);
                    }
                    else
                    {
                        String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                        switch (errorCode) {

                            case "ERROR_INVALID_CUSTOM_TOKEN":
                                Toast.makeText(getContext(), "The custom token format is incorrect. Please check the documentation.", Toast.LENGTH_LONG).show();
                                break;

                            case "ERROR_CUSTOM_TOKEN_MISMATCH":
                                Toast.makeText(getContext(), "The custom token corresponds to a different audience.", Toast.LENGTH_LONG).show();
                                break;

                            case "ERROR_INVALID_CREDENTIAL":
                                Toast.makeText(getContext(), "The supplied auth credential is malformed or has expired.", Toast.LENGTH_LONG).show();
                                break;

                            case "ERROR_INVALID_EMAIL":
                                Toast.makeText(getContext(), "The email address is badly formatted.", Toast.LENGTH_LONG).show();
                                etMail.setError("The email address is badly formatted.");
                                etMail.requestFocus();
                                break;

                            case "ERROR_WRONG_PASSWORD":
                                Toast.makeText(getContext(), "The password is invalid.", Toast.LENGTH_LONG).show();
                                etPW.setError("password is incorrect ");
                                etPW.requestFocus();
                                etPW.setText("");
                                break;

                            case "ERROR_USER_MISMATCH":
                                Toast.makeText(getContext(), "The supplied credentials do not correspond to the previously signed in user.", Toast.LENGTH_LONG).show();
                                break;

                            case "ERROR_REQUIRES_RECENT_LOGIN":
                                Toast.makeText(getContext(), "This operation is sensitive and requires recent authentication. Log in again before retrying this request.", Toast.LENGTH_LONG).show();
                                break;

                            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                                Toast.makeText(getContext(), "An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address.", Toast.LENGTH_LONG).show();
                                break;

                            case "ERROR_EMAIL_ALREADY_IN_USE":
                                Toast.makeText(getContext(), "The email address is already in use by another account.   ", Toast.LENGTH_LONG).show();
                                etMail.setError("The email address is already in use by another account.");
                                etMail.requestFocus();
                                break;

                            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                                Toast.makeText(getContext(), "This credential is already associated with a different user account.", Toast.LENGTH_LONG).show();
                                break;

                            case "ERROR_USER_DISABLED":
                                Toast.makeText(getContext(), "The user account has been disabled by an administrator.", Toast.LENGTH_LONG).show();
                                break;

                            case "ERROR_USER_TOKEN_EXPIRED":
                                Toast.makeText(getContext(), "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
                                break;

                            case "ERROR_USER_NOT_FOUND":
                                Toast.makeText(getContext(), "There is no user record corresponding to this identifier.", Toast.LENGTH_LONG).show();
                                break;

                            case "ERROR_INVALID_USER_TOKEN":
                                Toast.makeText(getContext(), "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
                                break;

                            case "ERROR_OPERATION_NOT_ALLOWED":
                                Toast.makeText(getContext(), "This operation is not allowed. You must enable this service in the console.", Toast.LENGTH_LONG).show();
                                break;

                            case "ERROR_WEAK_PASSWORD":
                                Toast.makeText(getContext(), "The given password is invalid.", Toast.LENGTH_LONG).show();
                                etPW.setError("The password is invalid it must 6 characters at least");
                                etPW.requestFocus();
                                break;

                        }
                    }
                }
            });
        }
    }

    private Boolean validatePass()
    {

        String Pass = etPW.getText().toString();
        if(Pass.isEmpty())
        {
            etPW.setError("Field can not be empty!");
            return false;
        }
        else if(Pass.length()<=6){
            etPW.setError("Password must be longer than 6 characters!");
            return false;
        }
        else
        {
            return true;
        }

    }
    private Boolean validateEmail()
    {
        String email = etMail.getText().toString();
        String emailpatterns = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(email.isEmpty())
        {
            etMail.setError("Field can not be empty!");
            return false;
        }
        else if(!email.matches(emailpatterns))
        {
            etMail.setError("Ivalid email!");
            return false;
        }
        else
        {
            return true;
        }
    }
    public void SetAnimation()
    {
        etMail.setTranslationX(800);
        etPW.setTranslationX(800);

        etMail.setAlpha(v);
        etPW.setAlpha(v);

        etMail.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        etPW.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
    }

}
