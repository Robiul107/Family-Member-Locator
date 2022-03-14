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

public class Join_circle extends AppCompatActivity {

    EditText E1;
    Button b1,b2;
    String code,key,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_circle);
        E1=(EditText) findViewById(R.id.editText2);
        b1=(Button) findViewById(R.id.button3);
        b2=(Button) findViewById(R.id.button4);

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
                joinCycle(E1.getText().toString());
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNevagitionActivity();
            }
        });


    }


    public void joinCycle(String s){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("cycle");
        String id=myRef.push().getKey();
        myRef.child(s).child(key).setValue(key);
        Log.i("hi", code+"onLocationChanged: "+key);
        Toast.makeText(this,"Successfully added to your friend circle",Toast.LENGTH_SHORT).show();

       // addCycle();
    }

    void  goToNevagitionActivity()
    {

        Intent myintent = new Intent(this, Mynavigation.class);
        myintent.putExtra("Code",code);
        myintent.putExtra("key",key);
        myintent.putExtra("name",name);
        startActivity(myintent);
        finish();
    }

}
