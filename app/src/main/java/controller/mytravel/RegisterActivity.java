package controller.mytravel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import controller.minh.ChangeProfileActivity;
import nga.uit.edu.mytravel.R;

public class RegisterActivity extends AppCompatActivity {

    Button btnLogin, btnRegister;
    EditText etName, etUserName, etEmail, etPassWord, etPhoneNum, etRePassWord;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateName()||!validatePhone()||!validateEmail()||!validateUserName()||!validatePass()||!validateRePass())
                    return;
                else
                {
                    String enterusername = etUserName.getText().toString();
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
                    Query check = databaseReference.orderByChild("userName").equalTo(enterusername);

                    check.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists())
                            {
                                etUserName.setError("user already exists");
                                etUserName.requestFocus();
                            }
                            else
                            {
                                Register();
                                Toast.makeText(RegisterActivity.this,"Register succesfully",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addControls() {
        btnLogin = (Button) findViewById(R.id.btnlogin);
        btnRegister = (Button)findViewById(R.id.btnRegister);
        etEmail = (EditText) findViewById(R.id.txtEmail);
        etName = (EditText) findViewById(R.id.txtName);
        etUserName = (EditText) findViewById(R.id.txtUsername);
        etPassWord = (EditText) findViewById(R.id.txtPassword);
        etPhoneNum = (EditText) findViewById(R.id.txtPhone);
        etRePassWord = (EditText) findViewById(R.id.txtRePassword);
    }
    private void Register()
    {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        String email = etEmail.getText().toString().trim();
        String pass = etPassWord.getText().toString().trim();
        String phone = etPhoneNum.getText().toString().trim();
        String Name = etName.getText().toString().trim();
        String UserName = etUserName.getText().toString().trim();

        User u = new User(Name, email, pass, phone, UserName);
        databaseReference.child(UserName).setValue(u);
    }

    private Boolean validateName()
    {

        String Name = etName.getText().toString().trim();
        if(Name.isEmpty())
        {
            etName.setError("Field can not be empty!");
            return false;
        }

        else{
            etName.setError(null);
            return true;
        }
    }
    private Boolean validateUserName()
    {

        String UName = etUserName.getText().toString().trim();
        if(UName.isEmpty()) {
            etUserName.setError("Field can not be empty!");
            return false;
        }

        else{
            etUserName.setError(null);
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
    private Boolean validateEmail()
    {
        String email = etEmail.getText().toString();
        String emailpatterns = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(email.isEmpty())
        {
            etEmail.setError("Field can not be empty!");
            return false;
        }
        else if(!email.matches(emailpatterns))
        {
            etEmail.setError("Ivalid email!");
            return false;
        }
        else
        {
            return true;
        }
    }
    private Boolean validatePhone()
    {

        String phone = etPhoneNum.getText().toString();
        if(phone.isEmpty())
        {
            etPhoneNum.setError("Field can not be empty!");
            return false;
        }
        else if(phone.length()!=10){
            etPhoneNum.setError("invalid phone number");
            return false;
        }
        else{
            etPhoneNum.setError(null);
            return true;
        }
    }
    private Boolean validateRePass()
    {

        String RPass = etRePassWord.getText().toString();
        String Pass = etPassWord.getText().toString();
        if(RPass.isEmpty())
        {
            etRePassWord.setError("Field can not be empty!");
            return false;
        }
        else if(!RPass.matches(Pass)){
            etRePassWord.setError("Retype Password and Password are difference");
            return false;
        }

        else
        {
            return true;
        }

    }
}