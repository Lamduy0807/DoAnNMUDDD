package controller.mytravel;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.firebase.auth.FirebaseUser;

import nga.uit.edu.mytravel.R;

public class LoginFragment extends Fragment {
    EditText etMail, etPW;
    TextView btnforgot;
    float v = 0;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    ExtendedFloatingActionButton btn;
    SharedPreferences sharedPreferences;

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

    private void login() {
        String email = etMail.getText().toString();
        String PW = etPW.getText().toString();
        mAuth.signInWithEmailAndPassword(email,PW).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()) {
                    Log.d("test","success");
                    Toast.makeText(getActivity(),"logininggg",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getContext(), HomeScreen.class));
                }
            }
        });
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
