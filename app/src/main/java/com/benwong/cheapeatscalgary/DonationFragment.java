package com.benwong.cheapeatscalgary;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;


/**
 * A simple {@link Fragment} subclass.
 */
public class DonationFragment extends Fragment implements BillingProcessor.IBillingHandler{

    Button donationBtn;
    BillingProcessor bp;

    public DonationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_donation, container, false);



        bp = new BillingProcessor(getContext(), "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAm2wVDLv59tFraqWL+Eeqh2oAT5gOgKhm6MY+fnYFajOoMteRoPVF7yxUdqbSDalKmewgDycIaBffXrhpbivcda0SwupufJI7KrNRKRwV3Z4q4DfR0CpwQYlQ9gkd320tl7LvNawAJ1P3zFpgqvCE7jsEHdOPpOXCUXcQXt8jO+Yj3t4IiPIbY4uXHA72pV8NVZNm0RMh4N1qSDy2MNzmzoNLzuBZYKazk2N/flNdXJMEQxSrxBa5P4wrMiXeEQDPDmM3HyTeoM5UMWE/ENjYCinM+w0w5UfNbHDAOs2j/bxPD+lAUlIv86iQI3GAmAEj+8i40ubtDKXf3G5nFI187QIDAQAB", this);


        donationBtn = (Button)v.findViewById(R.id.donationBtn);

        donationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bp.purchase(getActivity(), "donation");

            }
        });


        return v;
    }

    @Override
    public void onProductPurchased(String productId, TransactionDetails details) {

    }

    @Override
    public void onPurchaseHistoryRestored() {
 /*
         * Called when purchase history was restored and the list of all owned PRODUCT ID's
         * was loaded from Google Play
         */
    }

    @Override
    public void onBillingError(int errorCode, Throwable error) {
        System.out.println(error);
 /*
         * Called when some error occurred. See Constants class for more details
         *
         * Note - this includes handling the case where the user canceled the buy dialog:
         * errorCode = Constants.BILLING_RESPONSE_RESULT_USER_CANCELED
         */
    }

    @Override
    public void onBillingInitialized() {
        System.out.println("Billing Initialized.");
      /*
         * Called when BillingProcessor was initialized and it's ready to purchase
         */
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data))
            super.onActivityResult(requestCode, resultCode, data);
    }
}
