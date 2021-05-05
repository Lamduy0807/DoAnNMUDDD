package controller.mytravel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import controller.minh.ChangeProfileActivity;
import nga.uit.edu.mytravel.R;

public class LoginActivity extends AppCompatActivity {
    Button btnCreateAcc, btnLogin;
    EditText etUserName, etPassWord;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        addControls();
        addEvents();
    }

    private void addEvents() {
        btnCreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                createAcc();
            }

        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateUserName()||!validatePass())
                    return;
                else
                {
                    progressBar.setVisibility(View.VISIBLE);
                    isUser();
                }

            }
        });

    }
    private Boolean validateUserName()
    {
        String email = etUserName.getText().toString();

        if(email.isEmpty())
        {
            etUserName.setError("Field can not be empty!");
            return false;
        }
        else
        {
            return true;
        }
    }
    private Boolean validatePass()
    {

        String Pass = etPassWord.getText().toString();
        if(Pass.isEmpty())
        {
            etPassWord.setError("Field can not be empty!");
            return false;
        }
        else if(Pass.length()<=6){
            etPassWord.setError("Password must be longer than 6 characters!");
            return false;
        }
        else
        {
            return true;
        }

    }

    private void isUser() {
        String enterusername = etUserName.getText().toString();
        String enterpass = etPassWord.getText().toString();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        Query check = databaseReference.orderByChild("userName").equalTo(enterusername);

        check.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String passWfromdatabase = snapshot.child(enterusername).child("passWord").getValue(String.class);
                    if(passWfromdatabase.equals(enterpass))
                    {
                        Intent intent = new Intent(getApplicationContext(), ChangeProfileActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        progressBar.setVisibility(View.GONE);
                        etPassWord.setError("wrong password!");
                    }
                }
                else {
                    progressBar.setVisibility(View.GONE);
                    etUserName.setError("No user exist");
                    etUserName.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    private void createAcc() {
        Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }

    private void addControls() {
        btnCreateAcc= (Button) findViewById(R.id.btnCreateAcc);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        etUserName = (EditText) findViewById(R.id.txtUsername);
        etPassWord = (EditText) findViewById(R.id.txtPassword);
        progressBar = (ProgressBar) findViewById(R.id.Progressbar);
    }

}