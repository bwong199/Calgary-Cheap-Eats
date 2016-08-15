package com.benwong.cheapeatscalgary;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by benwong on 2016-07-25.
 */
public class AvenueFragment extends Fragment implements OnMapReadyCallback {

    ArrayList<Restaurant> restaurants;

    public static AvenueAdapter adapter;

    public static RecyclerView rvContacts;

    public static GoogleMap mMap;

    SupportMapFragment mapFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_avenue, container, false);

        System.out.println("Geolocation in fragment " + MainActivity.userLat + "  " + MainActivity.userLon);


// ...
        // Lookup the recyclerview in activity layout
        rvContacts = (RecyclerView) v.findViewById(R.id.avenueRV);

        mapFragment  = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        restaurants = new ArrayList<Restaurant>();

        // Create adapter passing in the sample user data
//        RestaurantsAdapter adapter = new RestaurantsAdapter(getContext(), restaurants);
        // Attach the adapter to the recyclerview to populate items
//        contacts = Contact.createContactsList(20);
//        // Create adapter passing in the sample user data
//        ContactsAdapter adapter = new ContactsAdapter(getContext(), contacts);

        adapter = new AvenueAdapter(getContext(), restaurants);
        rvContacts.setAdapter(adapter);
        // Set layout manager to position the items
        rvContacts.setLayoutManager(new LinearLayoutManager(getContext()));
        // That's all!



        try {

            DownloadTask task = new DownloadTask();

            String query = "https://avenue-picks.firebaseio.com/.json";

            task.execute(query);

        } catch (Exception e) {
            e.printStackTrace();

            System.out.println("Connection error " );
        }

        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(MainActivity.userLat, MainActivity.userLon);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16f));
    }


    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();

                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while (data != -1) {

                    char current = (char) data;

                    result += current;

                    data = reader.read();

                }

                return result;


            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
//
                JSONObject jsonObject = new JSONObject(result);


//
                String jsonInfo = jsonObject.get("results").toString();

//
                JSONArray jsonArray = new JSONArray(jsonInfo);

                System.out.println("Array length " + jsonArray.length());

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonPart = jsonArray.getJSONObject(i);

                    Location loc1 = new Location("");

                    JSONObject coordinates = (JSONObject) jsonPart.get("Coordinates");
//                    System.out.println(coordinates.get("Latitude") + " " + coordinates.get("Longitude"));
                    loc1.setLatitude((Double) coordinates.get("Latitude"));
                    loc1.setLongitude((Double) coordinates.get("Longitude"));

                    Location loc2 = new Location("");
                    loc2.setLatitude(MainActivity.userLat);
                    loc2.setLongitude( MainActivity.userLon);

                    float distanceInMeters = loc1.distanceTo(loc2);

                    Restaurant newRestaurant = new Restaurant();
                    newRestaurant.setName(String.valueOf(jsonPart.get("Name")));
                    newRestaurant.setAddress(String.valueOf(jsonPart.get("Address")));
                    newRestaurant.setDistance(distanceInMeters/1000);
                    newRestaurant.setCuisine(String.valueOf(jsonPart.get("Cuisine")));
//                    System.out.println("new Restaurant: " + i  + newRestaurant);


                    newRestaurant.setLatitude(jsonPart.getJSONObject("Coordinates").getDouble("Latitude"));
                    newRestaurant.setLongitude(jsonPart.getJSONObject("Coordinates").getDouble("Longitude"));

                    System.out.println(newRestaurant.getLatitude());
                    System.out.println(newRestaurant.getLongitude());

                    restaurants.add(newRestaurant);
                    Collections.sort(restaurants);
                    adapter.notifyDataSetChanged();
                    System.out.println(jsonPart.get("Name") + " " + jsonPart.get("Phone") + " " + distanceInMeters/1000);
                }


                for (Restaurant x : restaurants){
                    System.out.println(x.getName());
                }


            }catch(Exception e){
                e.printStackTrace();
            }



        }
    }
}

