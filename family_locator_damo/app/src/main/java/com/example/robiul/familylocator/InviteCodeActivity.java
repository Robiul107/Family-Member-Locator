package com.example.robiul.familylocator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InviteCodeActivity extends AppCompatActivity {

    String name,code,password,email;
    TextView t1;
    Button b1;
    static int i=0;
    String id="23456";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_code);
        t1=(TextView) findViewById(R.id.textView);
        b1=(Button) findViewById(R.id.button);

        Intent myintent=getIntent();

        if(myintent!=null)
        {
            name=myintent.getStringExtra("Name");
            code=myintent.getStringExtra("Code");
            password=myintent.getStringExtra("password");
            email=myintent.getStringExtra("email");



        }
        t1.setText(code);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("cycle");
        String id=myRef.push().getKey();
        myRef.child(code).child(code).setValue(code);
        myRef.child(code).child(code).setValue(code);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              MemberDetails M=new MemberDetails(name,code,email,password);

                // Write a message to the database
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("message");
                myRef.child(code).setValue(M);

                Toast.makeText(getApplicationContext(),"Registration successfull !",Toast.LENGTH_SHORT).show();
                finish();
                gotoNavigationActivity();


            }


        });


    }

    private void gotoNavigationActivity() {
        Intent myintent=new Intent(this,Mynavigation.class);
        myintent.putExtra("Code",code);
        myintent.putExtra("key",id);
        myintent.putExtra("name",name);
//        myintent.putExtra("name",name);
//        myintent.putExtra("email",email);
//        myintent.putExtra("password",password);
        startActivity(myintent);
    }
}
