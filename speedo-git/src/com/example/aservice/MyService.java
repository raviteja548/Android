
package com.example.aservice;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
  
public class MyService extends Service implements LocationListener {
/////////////////////////
	

    // flag for GPS status
    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;

    // flag for GPS status
    boolean canGetLocation = false;

    Location location; // location
    double latitude; // latitude
    double longitude; // longitude

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 10 * 1; // 1 minute

    // Declaring a Location Manager
    protected LocationManager locationManager;

	private Location loc = null;

	
//////////////////////////	
@Override  
 public IBinder onBind(Intent intent) {
	  
  return null;  
 }  
  
 @Override  
 public void onCreate() {  
  
	  Toast.makeText(this, "Service Created2", Toast.LENGTH_SHORT).show();  
  
 }  
  
 @Override  
 public void onStart(final Intent intent, int startid) {  
  int n = intent.getExtras().getInt("time1");
  Toast.makeText(this, "Value received as..."+n, Toast.LENGTH_SHORT).show();
  
  
  loc  = getLocation();
  Toast.makeText(this, "Service Created2 "+loc.getLatitude()+" "+loc.getLongitude(), Toast.LENGTH_LONG).show();
 }  
   
 @Override  
 public void onDestroy() {  
  Toast.makeText(this, "Service Stopped", Toast.LENGTH_LONG).show();
  if(locationManager != null){
	  location = null;
      locationManager.removeUpdates(MyService.this);
  }     
 }

@Override
public void onLocationChanged(Location location) {
	Toast.makeText(this, "Location updated"+location.getLatitude()+" "+location.getLongitude(), Toast.LENGTH_LONG);
	
}

@Override
public void onProviderDisabled(String provider) {
	// TODO Auto-generated method stub
	
}

@Override
public void onProviderEnabled(String provider) {
	// TODO Auto-generated method stub
	
}

@Override
public void onStatusChanged(String provider, int status, Bundle extras) {
	// TODO Auto-generated method stub
	
}


// method to get geo location
public Location getLocation() {
	
    try {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // getting GPS status
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        // getting network status
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnabled && !isNetworkEnabled) {
            // no network provider is enabled
        	Toast.makeText(this, "disabled", Toast.LENGTH_SHORT).show();
        } else {
            this.canGetLocation = true;
            if (isNetworkEnabled) {
                locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    float accuracy = location.getAccuracy();
                    if (location != null) {
                    	Toast.makeText(this, "nw acc"+accuracy, Toast.LENGTH_SHORT).show();
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    }
                }
            }
            // if GPS Enabled get lat/long using GPS Services
            if (isGPSEnabled) {
              	  Toast.makeText(this, "gps", Toast.LENGTH_SHORT).show();
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        float accuracy = location.getAccuracy();
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            Toast.makeText(this, "inside."+latitude+" "+longitude+" Acc"+accuracy, Toast.LENGTH_LONG).show();
                        }
                    }
                
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return location;
}




   
}