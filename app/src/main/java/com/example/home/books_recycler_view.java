package com.example.home;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class books_recycler_view extends AppCompatActivity {
    RecyclerView recyclerView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.books_recycler_view);
        recyclerView=findViewById(R.id.bookrecyclerview);

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("books");
        
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, String previousChildName) {
                    String fileName = snapshot.getKey();
                    String url = snapshot.getValue(String.class);
                    ((myadapter) (recyclerView.getAdapter())).update(fileName, url);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(books_recycler_view.this));
        myadapter Myadapter=new myadapter(recyclerView, books_recycler_view.this,new ArrayList<String>(),new ArrayList<String >());
        recyclerView.setAdapter(Myadapter);
    }
}
