package com.example.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class student_details extends AppCompatActivity {
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        listView = findViewById(R.id.listview);
        ArrayList<String> list=new ArrayList<>();
        ArrayAdapter adapter=new ArrayAdapter<String>(this,R.layout.list_item,list);
        listView.setAdapter(adapter);

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        String uid=user.getUid();

        Calendar calendar1=Calendar.getInstance();
        String current_month = DateFormat.getDateInstance(DateFormat.MONTH_FIELD).format(calendar1.getTime()).toString();
        int index1=current_month.indexOf('-');
        int index2=current_month.indexOf('-',3);
        String month=current_month.substring(index1+1,index2);

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("students").child(uid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                UserHelperClass userHelperClass=snapshot.getValue(UserHelperClass.class);
                list.add("Name  : " + userHelperClass.getName());
                list.add("Gender  : " + userHelperClass.getGender());
                list.add("Date of Birth  : " + userHelperClass.getDob());
                list.add("Mobile Number  : " + userHelperClass.getNumber());
                list.add("Email Id  : " + userHelperClass.getEmail());
                if(userHelperClass.getMonth().equals("Submitted") && !userHelperClass.getMonth().equals(month)){
                    reference.child("month").setValue(month);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}