package com.example.robiul.familylocator;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class LocationService extends Service implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private static long INTERVAL = 1000*2;
    private static long FASTEST_INTERVAL = 1000;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation,lStart,lEnd;
    static double distance=0,speedy=0,dupdate=0;
    private final IBinder mBinder = new LocalBinder();
    int i=0;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //return null;
        createLocationRequest();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();
        return mBinder;
    }

    private void createLocationRequest() {
        mLocationRequest=new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        try{
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,mLocationRequest,this);
        }catch(SecurityException e)
        {

        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
    Activity_2.progressDialog.dismiss();
    mCurrentLocation=location;
    if(lStart==null)
    {
        lStart = lEnd = mCurrentLocation;
    }
    else
    {
        lEnd=mCurrentLocation;
    }

    updateUI();

    }

    private void updateUI() {
        if(Activity_2.p==0)
        {
           distance=distance+(lStart.distanceTo(lEnd)/1000.00);
           Activity_2.endTime= System.currentTimeMillis();
           long diff =Activity_2.endTime-Activity_2.startTime;
           long diff2=diff/1000;
           if(diff2%5==0)
           {
               speedy=(distance-dupdate)*200;
               dupdate=distance;
               if(speedy>1)
               {
                   Activity_2.am.setRingerMode(1);

               }
               else
               {
                   Activity_2.am.setRingerMode(2);

               }
           }
           diff= TimeUnit.MILLISECONDS.toMinutes(diff);

           Activity_2.time.setText("Total time :"+diff+"minutes");
           Activity_2.distance.setText(new DecimalFormat("#.###").format(distance)+ "Km's.");
           Activity_2.speedy.setText(new DecimalFormat("###.###").format(speedy)+ "m/s.");
           lStart=lEnd;
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        stopLocationUpdates();
        if(mGoogleApiClient.isConnected())
            mGoogleApiClient.disconnect();
        lStart=lEnd=null;
        distance=0;
        return super.onUnbind(intent);
    }

    private void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,this);
        distance=0;
    }

    public class LocalBinder extends Binder {
        public LocationService getService(){
            return LocationService.this;
        }
    }
}





