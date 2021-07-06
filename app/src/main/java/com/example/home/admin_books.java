package com.example.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class admin_books extends AppCompatActivity {
    Button select_file,upload_file;
    TextView tvtext;
    EditText Filename;
    Uri pdfUri;
    ProgressDialog progressDialog;
    FirebaseStorage storage;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_books);

        storage=FirebaseStorage.getInstance();
        database=FirebaseDatabase.getInstance();

        select_file=findViewById(R.id.select_file);
        upload_file=findViewById(R.id.upload_file);
        Filename=findViewById(R.id.filename);
        tvtext=findViewById(R.id.upload);

        select_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(admin_books.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
                {   select_pdf();
                }
                else
                {
                    ActivityCompat.requestPermissions(admin_books.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},9);
                }
            }
        });
        upload_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(Filename.getText().toString().trim())) {
                    Toast.makeText(admin_books.this, "Please Enter File name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(pdfUri!=null)
                {
                    uploadFile(pdfUri);
                }
                else
                {
                    Toast.makeText(admin_books.this,"Select a File",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void uploadFile(Uri pdfUri)
    {   final String filename= Filename.getText().toString().trim() + ".pdf";
        final String filename1=Filename.getText().toString().trim();
        progressDialog=new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading File..!! ");
        progressDialog.setProgress(0);
        progressDialog.show();

        StorageReference storageReference=storage.getReference().child("books");
        storageReference.child(filename).putFile(pdfUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!urlTask.isSuccessful());
                        Uri downloadUrl = urlTask.getResult();
                        String url = downloadUrl.toString();
                        DatabaseReference reference=database.getReference().child("books");
                        reference.child(filename1).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {   progressDialog.dismiss();
                                    Toast.makeText(admin_books.this,"File is succesfully Uploaded",Toast.LENGTH_SHORT).show();
                                }
                                else
                                    Toast.makeText(admin_books.this,"File not succesfully Uploaded",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(admin_books.this,"File not succesfully Uploaded on failure",Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                int currrentProgress= (int) (100*snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                progressDialog.setProgress(currrentProgress);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull  String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==9 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            select_pdf();
        }
        else
        {
            Toast.makeText(admin_books.this,"please provide permission",Toast.LENGTH_SHORT).show();
        }
    }

    private void select_pdf()
    {
        Intent intent= new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,86);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 86 && resultCode == RESULT_OK && data != null) {
            pdfUri = data.getData();
            tvtext.setText(data.getData().getLastPathSegment());
        } else {
            Toast.makeText(admin_books.this, "Please select a file", Toast.LENGTH_SHORT).show();
        }
    }
}