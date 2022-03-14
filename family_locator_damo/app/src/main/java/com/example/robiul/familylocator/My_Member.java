package com.example.robiul.familylocator;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karan.churi.PermissionManager.PermissionManager;

public class My_Member extends AppCompatActivity {

    // Write a message to the database
    FirebaseDatabase database;
    DatabaseReference myRef;
    TextView txt;
   // String code;
    FirebaseUser user;
    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference databaseReference;
    String email="";
    String code="";
    PermissionManager manager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__member);
        txt=(TextView) findViewById(R.id.textidfire);

        Intent myintent=getIntent();

        if(myintent!=null)
        {

            code=myintent.getStringExtra("Code");

        }
        loadFirebaseUser();


    }

    public void loadFirebaseUser() {
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("message");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.e("onChildAdded", "addChildEventListener" + s);
                String olddata = "";
                String userscode= null;
                olddata = dataSnapshot.getKey();
                MemberDetails M;
                M = dataSnapshot.child(code).getValue(MemberDetails.class);
                txt.setText(M.getName()+"\n"+M.getEmil());

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.e("onChildChanged", "addChildEventListener" + dataSnapshot.getKey());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.e("onChildRemoved", "addChildEventListener");
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.e("onChildMoved", "addChildEventListener" + s);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Log.e("onCalcnelle", databaseError.toString() + "addChildEventListener");
            }
        });
    }

}
