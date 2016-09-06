package com.benwong.cheapeatscalgary;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

/**
 * Created by benwong on 2016-08-01.
 */
public class AvenueAdapter extends
        RecyclerView.Adapter<AvenueAdapter.ViewHolder> {

    private static List<Restaurant> mRestaurants;
    // Store the context for easy access
    private Context mContext;

    // Pass in the contact array into the constructor
    public AvenueAdapter(Context context, List<Restaurant> restaurants) {
        mRestaurants = restaurants;
        mContext = context;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }



    @Override
    public AvenueAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View restaurantView = inflater.inflate(R.layout.item_restaurant, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(restaurantView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AvenueAdapter.ViewHolder viewHolder, int position) {
        Restaurant restaurant = mRestaurants.get(position);

        // Set item views based on your views and data model
        CardView cv = viewHolder.cv;
        TextView nameTextView = viewHolder.nameTextView;
        nameTextView.setText(restaurant.getName());

        TextView addressTextView = viewHolder.addressTextView;
        addressTextView.setText(restaurant.getAddress());

        Double distance = restaurant.getDistance();

        String stringDistance;

        if(distance > 5000){
            stringDistance = "NA";
        } else {

            stringDistance = String.valueOf(String.format("%.1f", distance));
        }


        TextView distanceTextView = viewHolder.distanceTextView;
        distanceTextView.setText(stringDistance + " km");

        TextView cuisineTextView = viewHolder.cuisineTextView;
        cuisineTextView.setText("("+restaurant.getCuisine()+")");
    }

    @Override
    public int getItemCount() {
        return mRestaurants.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public CardView cv;
        public TextView nameTextView;
        public TextView addressTextView;
        public TextView distanceTextView;
        public TextView cuisineTextView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.contact_name);
            addressTextView = (TextView) itemView.findViewById(R.id.addressTV);
            distanceTextView = (TextView) itemView.findViewById(R.id.distanceTV);
            cuisineTextView = (TextView) itemView.findViewById(R.id.cuisineTV);
            cv = (CardView)itemView.findViewById(R.id.cv);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();

            Restaurant restaurant = mRestaurants.get(position);

            AvenueFragment.mMap.clear();
            LatLng calgary = new LatLng(restaurant.getLatitude(), restaurant.getLongitude());
            AvenueFragment.mMap.addMarker(new MarkerOptions().position(calgary).title(restaurant.getName()));
            AvenueFragment.mMap.moveCamera(CameraUpdateFactory.newLatLng(calgary));
            AvenueFragment.mMap.animateCamera(CameraUpdateFactory.zoomTo(14f));

        }
    }
}