package com.example.home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgorpassword extends AppCompatActivity {
    private EditText ET;
    private Button button;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgorpassword);
        mAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        ET=(EditText)findViewById(R.id.email_forgot);
        button=(Button)findViewById(R.id.Submit_forgot);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ET.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(forgorpassword.this,"Fill your valid email address",Toast.LENGTH_LONG).show();
                    return;
                }
                else{
                    progressDialog.show();
                    mAuth.sendPasswordResetEmail(ET.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressDialog.setMessage("Sending Your reset mail");
                            if(task.isSuccessful()){
                                progressDialog.dismiss();
                                Toast.makeText(forgorpassword.this,"Your Password Reset Email Sent to your Enail",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(forgorpassword.this,Login.class));
                                finish();
                            }
                            else{
                                progressDialog.dismiss();
                                Toast.makeText(forgorpassword.this,"Connection Failed",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}