package com.example.robiul.familylocator;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class My_circle extends AppCompatActivity {

    EditText E1;
    Button b1;
    String code,key,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_circle);
        E1=(EditText) findViewById(R.id.circle_id);
        b1=(Button) findViewById(R.id.okbtn);

        Intent myintent=getIntent();

        if(myintent!=null)
        {
            code=myintent.getStringExtra("Code");
            key=myintent.getStringExtra("key");
            name=myintent.getStringExtra("name");
        }
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCycle(E1.getText().toString());
                NevagitionActivity();
            }
        });



    }



    public void myCycle(String s){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("cycle");
        String id=myRef.push().getKey();
        myRef.child(code).child(s).setValue(s);
        Toast.makeText(this,"Successfully added to your friend circle",Toast.LENGTH_SHORT).show();

    }

    void  NevagitionActivity()
    {

        Intent myintent = new Intent(this, Mynavigation.class);
        startActivity(myintent);
        finish();
    }

}
