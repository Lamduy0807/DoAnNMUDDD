package controller.mytravel;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import nga.uit.edu.mytravel.R;

public class RegisterFragment extends Fragment {
    EditText etMail, etPW, etRPW;
    float v = 0;
    ExtendedFloatingActionButton btnRe;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    CircularProgressIndicator progressIndicator;
    private String PERMISION = "user";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater .inflate(R.layout.register_tab,container,false);
        etMail = root.findViewById(R.id.rEmail);
        etPW = root.findViewById(R.id.rPW);
        etRPW = root.findViewById(R.id.rePW);
        btnRe = root.findViewById(R.id.btnRegister);
        progressIndicator = root.findViewById(R.id.ppbarRe);
        setanimation();
        btnRe.setOnClickListener(view->{
            progressIndicator.setVisibility(View.VISIBLE);
            createuser();
        });

        return root;
    }

    private void createuser() {
        if(!validateEmail()||!validatePass()||!validateRePass())
            return;
        else {
            String email = etMail.getText().toString().trim();
            String PW = etPW.getText().toString().trim();
            mAuth.createUserWithEmailAndPassword(email, PW).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Saveuser(mAuth.getUid(),PERMISION);
                        UploadAvatar();
                        Toast.makeText(getContext(), "User register successfully!", Toast.LENGTH_LONG).show();
                    }
                    else {
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
                                Toast.makeText(getContext(), "The password is invalid or the user does not have a password.", Toast.LENGTH_LONG).show();
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
                                Toast.makeText(getContext(), "There is no user record corresponding to this identifier. The user may have been deleted.", Toast.LENGTH_LONG).show();
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
                    progressIndicator.setVisibility(View.GONE);
                }
            });
        }
    }

    private void UploadAvatar() {

        String MyUri = "https://firebasestorage.googleapis.com/v0/b/doannmuddd-2c6f2.appspot.com/o/Profile%20Pic%2Favt.png?alt=media&token=37e2b084-8b67-4cf0-86b7-253f5aeef533";
        HashMap<String,Object> userMap = new HashMap<>();
        userMap.put("image",MyUri);
        databaseReference.child(mAuth.getCurrentUser().getUid()).updateChildren(userMap);
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

    private Boolean validateRePass() {

        String RPass = etRPW.getText().toString();
        String Pass = etRPW.getText().toString();
        if (RPass.isEmpty()) {
            etRPW.setError("Field can not be empty!");
            return false;
        } else if (!RPass.matches(Pass)) {
            etRPW.setError("Retype Password and Password are difference");
            return false;
        } else {
            return true;
        }
    }
    private void Saveuser(String uid, String permission) {

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        User user = new User(permission);
        databaseReference.child(uid).setValue(user);
    }
    public void setanimation()
    {
        etMail.setTranslationX(800);
        etPW.setTranslationX(800);
        etRPW.setTranslationX(800);

        etMail.setAlpha(v);
        etPW.setAlpha(v);
        etRPW.setAlpha(v);

        etMail.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        etPW.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        etRPW.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
    }
}
