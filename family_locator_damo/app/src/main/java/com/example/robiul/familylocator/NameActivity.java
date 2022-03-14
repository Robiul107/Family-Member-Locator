package com.example.robiul.familylocator;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class NameActivity extends AppCompatActivity {

   EditText name;
   Button nxt;
   CircleImageView image;
   Uri resulturi;
   String email,password;
  public   StorageReference mStorageRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        name=(EditText) findViewById(R.id.editTextname);
        nxt=(Button) findViewById(R.id.buttoninname);
        mStorageRef = FirebaseStorage.getInstance().getReference();

        Intent myintent=getIntent();

        if(myintent!=null)
        {
            email=myintent.getStringExtra("email");
            password=myintent.getStringExtra("password");

        }

        nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  generatecode();
                gotocodeactivity();

            }
        });






    }

    public  void gotocodeactivity()
    {


        Random r=new Random();

        int n=100000+r.nextInt(900000);
        String code=String.valueOf(n);

        Intent myintent=new Intent(NameActivity.this,InviteCodeActivity.class);

        myintent.putExtra("Code",code);
        myintent.putExtra("Name",name.getText().toString());
        myintent.putExtra("password",password);
        myintent.putExtra("email",email);


        startActivity(myintent);

        finish();
    }

    public void generatecode()
    {
        Date myDate=new Date();
        SimpleDateFormat format1=new SimpleDateFormat("yyy-MM-dd hh mm:ss a",Locale.getDefault());
        String date=format1.format(myDate);

        Random r=new Random();

        int n=100000+r.nextInt(900000);
        String code=String.valueOf(n);
        if(resulturi!=null)
        {
               Intent myintent=new Intent(NameActivity.this,InviteCodeActivity.class);

               myintent.putExtra("Code",code);
               myintent.putExtra("Name",name.getText().toString());
               startActivity(myintent);
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Please choose an image",Toast.LENGTH_SHORT).show();
        }

    }

}
