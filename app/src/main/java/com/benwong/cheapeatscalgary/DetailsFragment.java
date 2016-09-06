package com.benwong.cheapeatscalgary;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {

    TextView gilChristPicks;
    TextView avenuePicks;

    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_details, container, false);

        gilChristPicks = (TextView)v.findViewById(R.id.gilChristPicks);
        avenuePicks = (TextView)v.findViewById(R.id.avenuePicks);

        gilChristPicks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://globalnews.ca/news/2804237/cheap-eats-in-calgary-where-to-dine-for-less-than-10/"));
                startActivity(browserIntent);
            }
        });

        avenuePicks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.avenuecalgary.com/Restaurants-Food/Dining-Out/Cheap-Eats/2016/7-Tasty-Dishes-For-Under-10/"));
                startActivity(browserIntent);
            }
        });

        return v;
    }


}
