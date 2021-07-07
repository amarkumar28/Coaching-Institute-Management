package com.example.home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class adminlogin extends AppCompatActivity {
    private EditText Email, Password;
    private Button Submit;
    private FirebaseAuth mAuth;
    private ProgressDialog PD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlogin);

        Email = (EditText) findViewById(R.id.email_admin);
        Password = (EditText) findViewById(R.id.password_admin);
        Submit = (Button) findViewById(R.id.Submit_admin);
        mAuth = FirebaseAuth.getInstance();
        PD = new ProgressDialog(this);
    /*  FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            finish();
            startActivity(new Intent(Login.this, menu.class));
        }
*/
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String admin_mail = Email.getText().toString().trim();
                String pswd = Password.getText().toString().trim();
                if (TextUtils.isEmpty(admin_mail)) {
                    Toast.makeText(adminlogin.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(pswd)) {
                    Toast.makeText(adminlogin.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(admin_mail.equals("") && pswd.equals(""))
                {
                    startActivity(new Intent(adminlogin.this,adminmenu.class));
                    finish();
                }
            }
        });
    }
}
