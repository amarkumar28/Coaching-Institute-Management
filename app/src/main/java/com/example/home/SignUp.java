package com.example.home;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;

public class SignUp extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    EditText dob,name,email,password,confirmpassword;
    Button submit_b;
    EditText Phone;
    RadioGroup RG;
    RadioButton RB;
    TextView textView;
    String date,gender,Name,Email,Password,ConfirmPassword,number;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseDatabase root_node=FirebaseDatabase.getInstance();
    DatabaseReference reference=root_node.getReference("students");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        Phone = (EditText) findViewById(R.id.phone_number);
        dob = findViewById(R.id.dob);
        RG = (RadioGroup) findViewById(R.id.radioG);
        submit_b = (Button) findViewById(R.id.Submit);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        textView = findViewById(R.id.login);
        password = findViewById(R.id.Password);
        confirmpassword = findViewById(R.id.confirmPassword);
        textView.setPaintFlags(8);
        RG.setOnCheckedChangeListener(this);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this,Login.class);
                startActivity(intent);
            }
        });
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONDAY);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        SignUp.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        date = day + "/" + month + "/" + year;
                        dob.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        submit_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name = name.getText().toString().trim();
                Email = email.getText().toString().trim();
                Password = password.getText().toString().trim();
                ConfirmPassword = confirmpassword.getText().toString().trim();
                number=Phone.getText().toString().trim();
                if(TextUtils.isEmpty(Email)){
                    Toast.makeText(SignUp.this,"Please Enter Email",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(Password)){
                    Toast.makeText(SignUp.this,"Please Enter Password",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(ConfirmPassword)){
                    Toast.makeText(SignUp.this,"Please Enter Confirm Password",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Password.length()<6 || Password.length()>20) {
                    Toast.makeText(SignUp.this,"Password length should be between 6-20",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(number)) {
                    Toast.makeText(SignUp.this, "Enter Phone Number ...!!", Toast.LENGTH_SHORT).show();
                }
                if (number.length() != 10) {
                    Toast.makeText(SignUp.this, "Enter Valid Phone Number ...!!", Toast.LENGTH_SHORT).show();
                }
                if(Password.equals(ConfirmPassword)) {
                    mAuth.createUserWithEmailAndPassword(Email, Password)
                            .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                                        String uid=user.getUid();
                                        Calendar calendar1=Calendar.getInstance();
                                        String current_month = DateFormat.getDateInstance(DateFormat.MONTH_FIELD).format(calendar1.getTime()).toString();
                                        int index1=current_month.indexOf('-');
                                        int index2=current_month.indexOf('-',3);
                                        String month=current_month.substring(index1+1,index2);
                                        UserHelperClass helperClass=new UserHelperClass(number,Name,Email,date,gender,month);
                                        reference.child(uid).setValue(helperClass);

                                        startActivity(new Intent(SignUp.this,menu.class));
                                        Toast.makeText(SignUp.this,"Registration Successful",Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        Toast.makeText(SignUp.this,"Authentication Failed",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });
    }
    @Override
    public void onCheckedChanged(RadioGroup RG, int checkedId) {
        switch (checkedId)
        {
            case R.id.male:
                gender="Male";
                break;

            case R.id.female:
                gender="Female";
                break;
        }
    }
}