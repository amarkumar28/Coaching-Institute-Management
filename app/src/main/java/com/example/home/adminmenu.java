package com.example.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class adminmenu extends AppCompatActivity {
    ImageView uploadbooks,updatestudent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminmenu);

        uploadbooks=findViewById(R.id.upload_books);
        updatestudent=findViewById(R.id.update_student);

        uploadbooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(adminmenu.this,admin_books.class);
                startActivity(intent);
            }
        });

        updatestudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(adminmenu.this,student_recycler_view.class);
                startActivity(intent);
            }
        });
    }
}