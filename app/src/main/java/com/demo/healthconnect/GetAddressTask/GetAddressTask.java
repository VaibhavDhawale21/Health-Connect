package com.demo.healthconnect.GetAddressTask;

import static com.demo.healthconnect.MapsActivity.MapsActivity.addresses;
import static com.demo.healthconnect.MapsActivity.MapsActivity.geocoder;
import static com.demo.healthconnect.MapsActivity.MapsActivity.latitude;
import static com.demo.healthconnect.MapsActivity.MapsActivity.longitude;

import android.content.Context;
import android.location.Address;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.List;

public class GetAddressTask extends AsyncTask<Location, Void, List<Address>> {
        Context localContext;

        public GetAddressTask(Context context) {
            super();
            localContext = context;
        }

        @Override
        protected List<Address> doInBackground(Location... params) {
            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return addresses;
        }

        @Override
        protected void onPostExecute(List<Address> address) {
            getAddressByLatLong();
        }


    public void getAddressByLatLong() {

        if (addresses != null && addresses.size() > 0) {
            String address = addresses.get(0).getAddressLine(0);
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();
            Log.e("LOCATION DATA", knownName + " " + address + " " + city + postalCode + " " + " " + state + " " + country);
        }
    }
}