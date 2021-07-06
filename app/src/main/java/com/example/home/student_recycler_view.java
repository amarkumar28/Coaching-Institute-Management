package com.example.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import static android.view.View.OnClickListener;


public class student_recycler_view extends AppCompatActivity {
    private Context context;
    private RecyclerView recyclerView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.books_recycler_view);
        recyclerView = findViewById(R.id.bookrecyclerview);

        new student_list_adapter().readstudents(new student_list_adapter.DataStatus() {
            @Override
            public void DataIsLoaded(List<UserHelperClass> students, List<String> keys) {
                setConfig(recyclerView, student_recycler_view.this, students, keys);
            }
        });
    }
    public void setConfig(RecyclerView recyclerView,Context context,List<UserHelperClass> students,List<String> keys) {
        this.context=context;
        studentsAdapter mstudentsAdapter = new studentsAdapter(students, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mstudentsAdapter);
    }
    class StudentItemView extends RecyclerView.ViewHolder{
        private TextView name_of_student,phone_of_student,email_of_student;
        public String key;
        Button edit_button;
        public StudentItemView(View itemView) {
            super(itemView);
            name_of_student=itemView.findViewById(R.id.studentname);
            phone_of_student=itemView.findViewById(R.id.studentphone);
            email_of_student=itemView.findViewById(R.id.studentemail);
            edit_button=itemView.findViewById(R.id.edit_btn);
            edit_button.setOnClickListener(v -> {
                final AlertDialog.Builder alert = new AlertDialog.Builder(student_recycler_view.this);
                View view = getLayoutInflater().inflate(R.layout.content, null);
                EditText update_name = view.findViewById(R.id.update_name);
                EditText update_phone_number = view.findViewById(R.id.update_phone_number);
                EditText update_email = view.findViewById(R.id.update_email);
                Button update_submit = view.findViewById(R.id.update_submit);
                @SuppressLint("UseSwitchCompatOrMaterialCode") Switch fees_switch= view.findViewById(R.id.fees_switch);

                DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("students").child(key);
                fees_switch.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(fees_switch.isChecked()) {
                            databaseReference1.child("month").setValue("Submitted");
                        }
                    }
                });

                alert.setView(view);
                final AlertDialog alertDialog = alert.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();

                update_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("students").child(key);
                        if(!update_name.getEditableText().toString().isEmpty()){
                            databaseReference.child("name").setValue(update_name.getEditableText().toString());
                        }
                        if(!update_phone_number.getEditableText().toString().isEmpty()){
                            databaseReference.child("number").setValue(update_phone_number.getEditableText().toString());
                        }
                        if(!update_email.getEditableText().toString().isEmpty()){
                            databaseReference.child("email").setValue(update_email.getEditableText().toString());
                        }

                        Toast.makeText(student_recycler_view.this,"Data Updated Succesfully",Toast.LENGTH_SHORT).show();

                        alertDialog.dismiss();
                    }
                });
            });
        }
        public void bind(UserHelperClass studens, String key){
            name_of_student.setText(studens.getName().toUpperCase());
            phone_of_student.setText(studens.getNumber());
            email_of_student.setText(studens.getEmail());
            this.key=key;

        }
    }
    class studentsAdapter extends RecyclerView.Adapter<StudentItemView>{
        private List<UserHelperClass> studentlist;
        private List<String> studentkeys;

        public studentsAdapter(List<UserHelperClass> studentlist, List<String> studentkeys) {
            this.studentlist = studentlist;
            this.studentkeys = studentkeys;
        }


        @NonNull
        @Override
        public StudentItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(context).inflate(R.layout.student_entry,parent,false);
            return new StudentItemView(view);
        }

        @Override
        public void onBindViewHolder(@NonNull StudentItemView holder, int position) {
            holder.bind(studentlist.get(position),studentkeys.get(position));
        }

        @Override
        public int getItemCount() {
            return studentlist.size();
        }
    }
}
