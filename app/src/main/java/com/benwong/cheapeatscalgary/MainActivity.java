package com.benwong.cheapeatscalgary;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocationListener {

    public LocationManager locationManager;
    public String provider;
    Location location;
    public static double userLat;
    public static double userLon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager(),
                MainActivity.this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        // check to see if location is turned on
        boolean enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if(enabled){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 0.0f, this);
        }else {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1, 0.0f, this);
        }

        provider = locationManager.getBestProvider(new Criteria(), false);


        location = locationManager.getLastKnownLocation(provider);

        onLocationChanged(location);


    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            System.out.println("Geolocation from activity: " + location.getLatitude() + location.getLongitude());
            userLat = location.getLatitude();
            userLon = location.getLongitude();
            locationManager.removeUpdates(this);
        } else {
            Toast.makeText(getApplicationContext(), "Location cannot be found", Toast.LENGTH_LONG).show();
            System.out.println("Location cannot be found");
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

}
