package com.demo.healthconnect.GetNearbyPlacesData;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.Log;

import com.demo.healthconnect.Adapters.BottomList;
import com.demo.healthconnect.Adapters.MyAdapter;
import com.demo.healthconnect.MapsActivity.MapsActivity;
import com.demo.healthconnect.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
/**
 * @author Ganesh
 */

public class GetNearbyPlacesData extends AsyncTask<Object, String, String> {

    private String googlePlacesData;
    private GoogleMap mMap;
    String url;
    MapsActivity context;
//    public static String distance;

    public GetNearbyPlacesData(MapsActivity context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Object... objects){
        mMap = (GoogleMap)objects[0];
        url = (String)objects[1];

        DownloadURL downloadURL = new DownloadURL();
        try {
            googlePlacesData = downloadURL.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String s){

        List<HashMap<String, String>> nearbyPlaceList;
        DataParser parser = new DataParser();
        nearbyPlaceList = parser.parse(s);
        Log.d("nearbyplacesdata","called parse method");
        showNearbyPlaces(nearbyPlaceList);
    }

    private void showNearbyPlaces(List<HashMap<String, String>> nearbyPlaceList)
    {

        Log.e("showNearbyPlaces", String.valueOf(nearbyPlaceList));
        for(int i = 0; i < nearbyPlaceList.size(); i++)
        {
            MarkerOptions markerOptions = new MarkerOptions();
            HashMap<String, String> googlePlace = nearbyPlaceList.get(i);

            final String placeName = googlePlace.get("place_name");
            String vicinity = googlePlace.get("vicinity");
            double lat = Double.parseDouble( googlePlace.get("lat"));
            double lng = Double.parseDouble( googlePlace.get("lng"));

//            LatLng origin = new LatLng(MapsActivity.latitude,MapsActivity.longitude);
//            LatLng dest = new LatLng(lat,lng);
//            String url = getUrlForHospitalDistance(origin, dest);
//            Log.d("onMapClick", url.toString());
            // Start downloading json data from Google Directions API
//            FetchUrl FetchUrl = new FetchUrl();
//            FetchUrl.execute(url);

            LatLng latLng = new LatLng(lat, lng);
            markerOptions.position(latLng);
            markerOptions.title(placeName + " : "+ vicinity);

            int height = 100;
            int width = 100;
            BitmapDrawable bitmapdraw = (BitmapDrawable) context.getResources().getDrawable(R.drawable.hospital_marker);
            Bitmap b = bitmapdraw.getBitmap();
            Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));

            MapsActivity.hospitalLocationArrayList.add(latLng);

            double distance = MapsActivity.checkDistance(MapsActivity.latitude, MapsActivity.longitude, lat,lng);
            distance = Double.parseDouble(String.format("%.2f", distance));

                BottomList bottomList;
                bottomList = new BottomList(placeName, vicinity,String.valueOf(distance) + "Km");
                MapsActivity.list.add(bottomList);

            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

            }
            MyAdapter adapter = new MyAdapter(MapsActivity.list, context.getApplication());
                MapsActivity.listView.setAdapter(adapter);
                MapsActivity.stopLoader();



            /*mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    Toast.makeText(context,marker.getTitle().subSequence(0,marker.getTitle().length()-1),Toast.LENGTH_LONG).show();
                    return true;
                }
            });*/
            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
//                    context.sendinfo(marker.getTitle().substring(0,marker.getTitle().indexOf(':')));
                   // Toast.makeText(context,marker.getTitle().subSequence(0,marker.getTitle().length()-1),Toast.LENGTH_LONG).show();
                }
            });
    }

    private String getUrlForHospitalDistance(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = dest.latitude + "," + dest.longitude;

        String url = "https://maps.googleapis.com/maps/api/directions/json?origin=" + str_origin
                + "&destination=" + str_dest + "&key=" + context.getResources().getString(R.string.google_maps_key) + "&sensor=false";

        return url;
    }

}
