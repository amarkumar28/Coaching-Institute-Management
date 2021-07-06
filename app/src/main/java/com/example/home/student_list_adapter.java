package com.example.home;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class student_list_adapter {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databasereference;
    private List<UserHelperClass> students=new ArrayList<>();
    public interface DataStatus{
        void DataIsLoaded(List<UserHelperClass> students, List<String> keys);
    }
    public student_list_adapter() {
        firebaseDatabase=FirebaseDatabase.getInstance();
        databasereference= firebaseDatabase.getReference("students");
    }
    public void readstudents(DataStatus dataStatus)
    {
        databasereference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                students.clear();
                List<String> keys=new ArrayList<>();
                for(DataSnapshot keynode : snapshot.getChildren()) {
                    keys.add(keynode.getKey());
                    UserHelperClass student = keynode.getValue(UserHelperClass.class);
                    students.add(student);
                }
                dataStatus.DataIsLoaded(students,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}