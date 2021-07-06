package com.example.home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private TextView textView,tv;
    private EditText Email, Password;
    private Button Submit;
    private FirebaseAuth mAuth;
    private ProgressDialog PD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        textView = (TextView) findViewById(R.id.register);
        tv = (TextView) findViewById(R.id.forgotpwd);
        Email = (EditText) findViewById(R.id.email_login);
        Password = (EditText) findViewById(R.id.password_login);
        Submit = (Button) findViewById(R.id.Submit_login);
        textView.setPaintFlags(8);
        tv.setPaintFlags(8);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);
                finish();
            }
        });
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I = new Intent(Login.this, forgorpassword.class);
                startActivity(I);
                finish();
            }
        });
        mAuth = FirebaseAuth.getInstance();
        PD=new ProgressDialog(this);
    /*  FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            finish();
            startActivity(new Intent(Login.this, menu.class));
        }
*/        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String User_mail = Email.getText().toString().trim();
                String pswd = Password.getText().toString().trim();
                if(TextUtils.isEmpty(User_mail)){
                    Toast.makeText(Login.this,"Please Enter Email",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(pswd)){
                    Toast.makeText(Login.this,"Please Enter Password",Toast.LENGTH_SHORT).show();
                    return;
                }
                validate(User_mail, pswd);
            }
        });
    }
    private void validate(String User_mail,String pswd)
    {   PD.setMessage("Matching Credentials !!!");
        PD.show();
        mAuth.signInWithEmailAndPassword(User_mail,pswd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    PD.dismiss();
                    startActivity(new Intent(Login.this,menu.class));
                    finish();
                }
                else{
                    PD.dismiss();
                    Toast.makeText(Login.this,"Email or Password is incorrect",Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}