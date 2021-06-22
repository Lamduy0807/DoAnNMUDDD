package controller.mytravel;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
                        Toast.makeText(getContext(), "User register successfully!", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getContext(),"Sign up fail!",Toast.LENGTH_LONG).show();
                    }
                    progressIndicator.setVisibility(View.GONE);
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
