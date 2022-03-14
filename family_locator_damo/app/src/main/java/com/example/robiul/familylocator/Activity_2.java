package com.example.robiul.familylocator;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.media.AudioManager;
import android.os.Bundle;
import android.provider.MediaStore.Audio;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class Activity_2 extends AppCompatActivity {

    static boolean status;
    LocationManager locationManager;
    static TextView distance,time,speedy;
    Button btnStart,btnPause,btnStop;
    static long startTime,endTime;
    static ProgressDialog progressDialog;
    static int p=0;
    LocationService myservice;
    static AudioManager am;

    private ServiceConnection sc=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            LocationService.LocalBinder binder =(LocationService.LocalBinder)iBinder;
            myservice = binder.getService();
            status=true;

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            status=false;

        }
    };


  /* @Override
   public boolean bindService(Intent service, ServiceConnection conn, int flags){
       return super.bindService(service, conn, flags);
   }*/

    @Override
    protected void onDestroy(){
        if(status==true) {
            unbindService();
        }
        super.onDestroy();
    }

    private void unbindService() {
        if(status==false)
            return;
        Intent i = new Intent (getApplicationContext(),LocationService.class);
        unbindService(sc);
        status=false;
    }


    @Override
    public void onBackPressed()
    {
        if(status==false)
        {
            super.onBackPressed();
        }
        else
        {
            moveTaskToBack(true);
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,@NonNull int[] grantResults ){
        switch (requestCode)
        {
            case 1000:
            {
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(this, "GRANTED", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this, "DENIED",Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                ||ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED
        )
        {
            requestPermissions(new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION


            },1000);
        }
        distance=(TextView)findViewById(R.id.distance);
        speedy=(TextView)findViewById(R.id.speedy);

        time=(TextView)findViewById(R.id.time);
        btnPause=(Button)findViewById(R.id.btnPause);
        btnStart=(Button)findViewById(R.id.btnStart);
        btnStop=(Button)findViewById(R.id.btnStop);
        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkGPS();
                locationManager=(LocationManager)getSystemService(LOCATION_SERVICE);
                if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                {
                    return;
                }
                if(status==false)
                {
                    bindService();
                }


                progressDialog =new ProgressDialog(Activity_2.this);
                progressDialog.setIndeterminate(true);
                progressDialog.setCancelable(false);
                progressDialog.show();

                btnStart.setVisibility(View.GONE);
                btnPause.setVisibility(View.VISIBLE);
                btnPause.setText("Pause");
                btnStop.setVisibility(View.VISIBLE);

                ///27.24



            }
        });


        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnPause.getText().toString().equalsIgnoreCase("Pause"))
                {
                    btnPause.setText("Resume");
                    p=1;
                }

                else if(btnPause.getText().toString().equalsIgnoreCase("resume"))
                {


                    checkGPS();
                    locationManager=(LocationManager)getSystemService(LOCATION_SERVICE);
                    if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                    {
                        return;
                    }

                    btnPause.setText("Pause");
                    p=0;
                }

            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(status==true)
                {
                    unbindService();
                }

                btnStart.setVisibility(View.VISIBLE);
                btnPause.setText("Pause");
                btnPause.setVisibility(View.GONE);
                btnStop.setVisibility(View.GONE);


            }
        });
    }

    private void checkGPS() {
        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            showGPSDisableALert();
        }
    }

    private void showGPSDisableALert() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Enable Gps to Use application")
                .setCancelable(false)
                .setPositiveButton("Enable GPS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent =new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);



                    }
                });

        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();

            }
        });

        AlertDialog alert =alertDialogBuilder.create();
    }

    private void bindService() {
        if(status==true)
        {
            return;
        }
        Intent i=new Intent (getApplicationContext(),LocationService.class);
        bindService(i,sc,BIND_AUTO_CREATE);
        startTime=System.currentTimeMillis();

    }
}

