package com.demo.healthconnect.MapsActivity;

import static android.content.ContentValues.TAG;
//import static com.example.googlemapdemo.SplashScreen.SplashScreen.MyPref;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.demo.healthconnect.Adapters.BottomList;
import com.demo.healthconnect.Adapters.MyAdapter;
import com.demo.healthconnect.GetAddressTask.GetAddressTask;
import com.demo.healthconnect.GetNearbyPlacesData.GetNearbyPlacesData;
import com.demo.healthconnect.R;
import com.demo.healthconnect.databinding.ActivityMapsBinding;
//import com.example.googlemapdemo.BottomSheet.Adapters.BottomList;
//import com.example.googlemapdemo.BottomSheet.Adapters.MyAdapter;
//import com.example.googlemapdemo.GetAddressTask.GetAddressTask;
//import com.example.googlemapdemo.GetNearbyPlacesData.GetNearbyPlacesData;
//import com.example.googlemapdemo.Login.LoginAndRegisterActivity;
//import com.example.googlemapdemo.databinding.ActivityMapsBinding;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
//import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, LocationListener, GoogleApiClient.OnConnectionFailedListener {

    private static final int PROXIMITY_RADIUS = 10000;
    public static GoogleMap mMap;
    private ActivityMapsBinding binding;
    public ArrayList<LatLng> MarkerPoints = new ArrayList<LatLng>();
    private ArrayList<LatLng> ambulanceLocationArrayList = new ArrayList<LatLng>();
    public static ArrayList<LatLng> hospitalLocationArrayList = new ArrayList<LatLng>();
    private ArrayList<LatLng> hospitalNameLocationArrayList = new ArrayList<LatLng>();
    private ArrayList<LatLng> hospitalAddressLocationArrayList = new ArrayList<LatLng>();
//    private ArrayList<LatLng> hospitalLocationArrayList = new ArrayList<LatLng>();

    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    public static Geocoder geocoder;
    public static List<Address> addresses;
    public static double latitude, longitude;
    private static final int REQUEST_CODE = 101;
    MarkerOptions options = new MarkerOptions();
    TextView km;
    TextView book, find_near;
    ImageView close;

    public static int min_index = 0;
    Button button;
    public  static  double[] minArray = new double[100];
    boolean firstTimeUpdate = true;
//    public static AVLoadingIndicatorView avLoadingIndicatorView, avLoadingIndicatorView2;
    public double minValue;
    public static MarkerOptions userMarker = new MarkerOptions();

    private DatabaseReference databaseReference;
    private LocationListener locationListener;
    private LocationManager locationManager;
    private final long MIN_TIME = 1000;
    private final long MIN_DIST = 5;
    private LatLng latLng, removeLatLng;
    Marker removeMarker = null;
    boolean firstRemoveMarker = true;
    ImageView logout;
    SharedPreferences sharedPreferences;

    //bottom
    TextView find_ambulance, find_hospital, header;
    public static ListView listView;
    public static List<BottomList> list;
    LinearLayout listViewl;
    public static boolean ambORhospital;
    String deiver_name[]={"Ganesh","Ramesh","Suresh","Ganesh","Ramesh","Suresh","Ganesh","Ramesh","Suresh","Ganesh"};
    String vehicle_no[]={"MH 02 FF 5814","MH 02 FF 5814","MH 02 FF 5814","MH 02 FF 5814","MH 02 FF 5814","MH 02 FF 5814","MH 02 FF 5814","MH 02 FF 5814","MH 02 FF 5814","MH 02 FF 5814"};

    TextView confirm;

    LatLng Kohinoor = new LatLng(19.07616315094619, 72.88605515192715);
    LatLng Niron = new LatLng(19.081052572884623, 72.8580892784897);
    LatLng City = new LatLng(19.08181974468215, 72.87968215738996);
    LatLng church = new LatLng(19.079670195846067, 72.86753711455783);
    LatLng Mithibai = new LatLng(19.076303865309107, 72.87702140578426);
    LatLng Gate = new LatLng(18.92354227245684, 72.83804360603875);
    LatLng Nigdi1 = new LatLng(18.639518764161267, 73.75859023169271);
    LatLng Nigdi2 = new LatLng(18.647734104022593, 73.75515076890292);
    LatLng Nigdi3 = new LatLng(18.64922775947708, 73.77055669598217);
    LatLng Nigdi4 = new LatLng(18.66339306600028, 73.75525764336605);
    LatLng Nigdi5 = new LatLng(18.66737756562269, 73.7810926810055);
    LatLng Pune = new LatLng(18.527163602800893, 73.84597934215152);
    LatLng Andheri = new LatLng(19.119359786552018, 72.86441887884013);
    LatLng Borivali = new LatLng(19.23012811037985, 72.8564927998908);
    LatLng NaviMumbai = new LatLng(19.04114964612934, 73.02596412983812);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fetchLocationPermission();
//        avLoadingIndicatorView = (AVLoadingIndicatorView) findViewById(R.id.avi);
        showLoader();
        list = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Location");
        geocoder = new Geocoder(this, Locale.getDefault());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        logout = (ImageView) findViewById(R.id.logout);
        book = (TextView) findViewById(R.id.book);
        find_near = (TextView) findViewById(R.id.find_near);
        km = (TextView) findViewById(R.id.km);
        find_ambulance = (TextView) findViewById(R.id.find_ambulance);
        find_hospital = (TextView) findViewById(R.id.find_hospital);
        header = (TextView) findViewById(R.id.header);
        listView = (ListView) findViewById(R.id.listView);
        listViewl = (LinearLayout) findViewById(R.id.listViewl);
        confirm = (TextView) findViewById(R.id.book);
        close = (ImageView) findViewById(R.id.close);

        @SuppressLint("ResourceType") View locationButton = ((View) binding.getRoot().findViewById(1).getParent()).findViewById(2);
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        rlp.setMargins(0, 0, 500, 500);

        ambulanceLocationArrayList.add(Gate);
        ambulanceLocationArrayList.add(Niron);
        ambulanceLocationArrayList.add(City);
        ambulanceLocationArrayList.add(church);
        ambulanceLocationArrayList.add(Mithibai);
        ambulanceLocationArrayList.add(Nigdi1);
        ambulanceLocationArrayList.add(Nigdi2);
        ambulanceLocationArrayList.add(Nigdi3);
        ambulanceLocationArrayList.add(Nigdi4);
        ambulanceLocationArrayList.add(Nigdi5);
        ambulanceLocationArrayList.add(Pune);
        ambulanceLocationArrayList.add(Andheri);
        ambulanceLocationArrayList.add(Borivali);
        ambulanceLocationArrayList.add(NaviMumbai);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();


                        String manufacturer = Build.MANUFACTURER;
                        String model = Build.MODEL;
                        databaseReference.child("token").push().setValue(manufacturer + " "+ model +" " + token);
                        // Log and toast
//                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d("FCM Token", token);
                        Toast.makeText(MapsActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LatLng tempOrigin = new LatLng(latitude, longitude);
                LatLng tempDest = new LatLng(ambulanceLocationArrayList.get(min_index).latitude, ambulanceLocationArrayList.get(min_index).longitude);

                MarkerPoints.add(tempOrigin);
                MarkerPoints.add(tempDest);

                LatLng origin = MarkerPoints.get(0);
                LatLng dest = MarkerPoints.get(1);

                // Getting URL to the Google Directions API
                String url = getUrl(origin, dest);
                Log.d("onMapClick", url.toString());

                // Start downloading json data from Google Directions API
                FetchUrl FetchUrl = new FetchUrl();
                FetchUrl.execute(url);

            }
        });

        find_ambulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                BottomSheetDialog bottomSheet = new BottomSheetDialog();
//                bottomSheet.show(getSupportFragmentManager(),
//                        "ModalBottomSheet");
                ambORhospital = true;

                list.clear();
                for (int i = 0; i < 10; i++) {
                    BottomList bottomList;
                    bottomList = new BottomList("Ganesh kamble", "MH 02 FF 5814","12.5 Km");
                    list.add(bottomList);
                }

                listViewl.setVisibility(View.VISIBLE);
                header.setText("Ambulance Near You");
                header.setVisibility(View.VISIBLE);
                find_ambulance.setVisibility(View.GONE);
                find_hospital.setVisibility(View.VISIBLE);

                //creating custom adapter object
                MyAdapter adapter = new MyAdapter(list, getApplicationContext());

                //adding the adapter to listview
                listView.setAdapter(adapter);


            }
        });

        find_hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showLoader();

                list.clear();
                ambORhospital = false;
                listViewl.setVisibility(View.VISIBLE);
                header.setText("Hospital Near You");
                header.setVisibility(View.VISIBLE);
                find_hospital.setVisibility(View.GONE);
//                find_ambulance.setVisibility(View.VISIBLE);
//                Toast.makeText(MapsActivity.this, "Working", Toast.LENGTH_SHORT).show();

                Object dataTransfer[] = new Object[2];
                GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData(MapsActivity.this);
                mMap.clear();
                String hospital = "hospital";
                String url = getUrlForHospital(latitude, longitude, hospital);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;

                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(MapsActivity.this, "Showing Nearby Hospitals", Toast.LENGTH_SHORT).show();




            }
        });

        find_near.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                find_near.setVisibility(View.GONE);
//                km.setText("Nearest Ambulance Distance : " +minValue + " Km");
                book.setVisibility(View.VISIBLE);


                LatLng tempOrigin = new LatLng(latitude, longitude);
                LatLng tempDest = new LatLng(ambulanceLocationArrayList.get(min_index).latitude, ambulanceLocationArrayList.get(min_index).longitude);

//                    double d = distanceBetween(tempOrigin, tempDest);
//                    Log.d("distanceBetween", String.valueOf(d));

                MarkerPoints.add(tempOrigin);
                MarkerPoints.add(tempDest);

                LatLng origin = MarkerPoints.get(0);
                LatLng dest = MarkerPoints.get(1);

                // Getting URL to the Google Directions API
                String url = getUrl(origin, dest);
                Log.d("onMapClick", url.toString());

                // Start downloading json data from Google Directions API
                FetchUrl FetchUrl = new FetchUrl();
                FetchUrl.execute(url);

                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                builder.include(origin);
                builder.include(dest);
                LatLngBounds bounds = builder.build();
                int width = getResources().getDisplayMetrics().widthPixels;
                int height = getResources().getDisplayMetrics().heightPixels;
                int padding = (int) (width * 0.10); // offset from edges of the map 10% of screen
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
                mMap.animateCamera(cu);
            }
        });

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
//                        showLoader();
////                    findMin(minArray);
//                    LatLng tempOrigin = new LatLng(latitude, longitude);
//                    LatLng tempDest = new LatLng(ambulanceLocationArrayList.get(min_index).latitude, ambulanceLocationArrayList.get(min_index).longitude);
//
////                    double d = distanceBetween(tempOrigin, tempDest);
////                    Log.d("distanceBetween", String.valueOf(d));
//
//                    MarkerPoints.add(tempOrigin);
//                    MarkerPoints.add(tempDest);
//
//                    LatLng origin = MarkerPoints.get(0);
//                    LatLng dest = MarkerPoints.get(1);
//
//                    // Getting URL to the Google Directions API
//                    String url = getUrl(origin, dest);
//                    Log.d("onMapClick", url.toString());
//
//                    // Start downloading json data from Google Directions API
//                    FetchUrl FetchUrl = new FetchUrl();
//                    FetchUrl.execute(url);

                    //move map camera
//                    mMap.moveCamera(CameraUpdateFactory.newLatLng(origin));
//                    mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

                    //BothMarkerInscreen
//                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
//                    builder.include(origin);
//                    builder.include(dest);
//                    LatLngBounds bounds = builder.build();
//                    int width = getResources().getDisplayMetrics().widthPixels;
//                    int height = getResources().getDisplayMetrics().heightPixels;
//                    int padding = (int) (width * 0.10); // offset from edges of the map 10% of screen
//                    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
//                    mMap.animateCamera(cu);
//
//                    stopLoader();

                    book.setVisibility(View.GONE);

                    Toast.makeText(MapsActivity.this, "Ambulance Booked will Arrive in Time", Toast.LENGTH_SHORT).show();

                    databaseReference.removeValue();
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            try {

                                String databaseLatitudeString = dataSnapshot.child("latitude").getValue().toString().substring(1, dataSnapshot.child("latitude").getValue().toString().length()-1);
                                String databaseLongitudedeString = dataSnapshot.child("longitude").getValue().toString().substring(1, dataSnapshot.child("longitude").getValue().toString().length()-1);

                                String[] stringLat = databaseLatitudeString.split(", ");
                                Arrays.sort(stringLat);
                                String latitude = stringLat[stringLat.length-1].split("=")[1];

                                String[] stringLong = databaseLongitudedeString.split(", ");
                                Arrays.sort(stringLong);
                                String longitude = stringLong[stringLong.length-1].split("=")[1];


                                LatLng latLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                                if (firstRemoveMarker){
                                    firstRemoveMarker = false;
                                }else {
                                    removeMarker.remove();
                                }
                                removeMarker =  mMap.addMarker(new MarkerOptions().position(latLng).title("FirebaseDatabase")
                                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ecare_marker)));

//                                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//                                mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

                                removeLatLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));

                                double distance = checkDistance(MapsActivity.latitude,MapsActivity.longitude , ambulanceLocationArrayList.get(min_index).latitude ,
                                        ambulanceLocationArrayList.get(min_index).longitude);

                                distance = Double.parseDouble(String.format("%.2f", distance));
                                km.setText("Ambulance is " +distance + " Km away from you");

                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });




                }catch (Exception e){
//                    book.performClick();
                    Toast.makeText(MapsActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
                    Log.e("abc",e.toString());
                }
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
                builder.setMessage("Are you sure, want to Logout ?")
                        .setTitle("Logout")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

//                                sharedPreferences = getSharedPreferences(MyPref, Context.MODE_PRIVATE);
//                                SharedPreferences.Editor editor = sharedPreferences.edit();
//                                editor.clear();
//                                editor.commit();
//                                Intent intent = new Intent(MapsActivity.this, LoginAndRegisterActivity.class);
//                                startActivity(intent);
//                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //do things
                            }
                        });

                AlertDialog alert = builder.create();
                alert.show();

            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listViewl.setVisibility(View.GONE);
                header.setVisibility(View.GONE);
                find_hospital.setVisibility(View.VISIBLE);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Log.e("Hello! "+position, String.valueOf(id));
                listViewl.setVisibility(View.GONE);
                header.setVisibility(View.GONE);
                find_hospital.setVisibility(View.VISIBLE);

                LatLng tempOrigin = new LatLng(latitude, longitude);
                LatLng tempDest = new LatLng(hospitalLocationArrayList.get(position).latitude, hospitalLocationArrayList.get(position).longitude);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(tempOrigin));

                String url = getUrl(tempOrigin, tempDest);
                Log.d("onMapClick", url.toString());

                // Start downloading json data from Google Directions API
                FetchUrl FetchUrl = new FetchUrl();
                FetchUrl.execute(url);
                Toast.makeText(MapsActivity.this, "Direction Started", Toast.LENGTH_SHORT).show();

                try {
                    databaseReference.removeValue();
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            try {

                                String databaseLatitudeString = dataSnapshot.child("latitude").getValue().toString().substring(1, dataSnapshot.child("latitude").getValue().toString().length()-1);
                                String databaseLongitudedeString = dataSnapshot.child("longitude").getValue().toString().substring(1, dataSnapshot.child("longitude").getValue().toString().length()-1);

                                String[] stringLat = databaseLatitudeString.split(", ");
                                Arrays.sort(stringLat);
                                String latitude = stringLat[stringLat.length-1].split("=")[1];

                                String[] stringLong = databaseLongitudedeString.split(", ");
                                Arrays.sort(stringLong);
                                String longitude = stringLong[stringLong.length-1].split("=")[1];


                                LatLng latLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                                if (firstRemoveMarker){
                                    firstRemoveMarker = false;
                                }else {
                                    removeMarker.remove();
                                }
                                removeMarker =  mMap.addMarker(new MarkerOptions().position(latLng).title("FirebaseDatabase")
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital_marker)));

//                                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//                                mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

                                removeLatLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));

                                double distance = checkDistance(MapsActivity.latitude,MapsActivity.longitude , hospitalLocationArrayList.get(position).latitude ,
                                        hospitalLocationArrayList.get(position).longitude);

                                distance = Double.parseDouble(String.format("%.2f", distance));
                                km.setText("Ambulance is " +distance + " Km away from you");

                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }catch (Exception e){
//                    book.performClick();
                    Toast.makeText(MapsActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
                    Log.e("abc",e.toString());
                }

            }
        });


    }

    @SuppressLint("LongLogTag")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        buildGoogleApiClient();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

//        if (!(latitude == 0.0)){
//            addDefaultMarkers();
//        }

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
//                // Already two locations
//                if (MarkerPoints.size() > 1) {
//                    MarkerPoints.clear();
//                    mMap.clear();
//                }
//
//                // Adding new item to the ArrayList
//                MarkerPoints.add(point);
//
//                // Creating MarkerOptions
//                MarkerOptions options = new MarkerOptions();
//
//                // Setting the position of the marker
//                options.position(point);
//
//                /**
//                 * For the start location, the color of marker is GREEN and
//                 * for the end location, the color of marker is RED.
//                 */
//                if (MarkerPoints.size() == 1) {
//                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
//                } else if (MarkerPoints.size() == 2) {
//                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
//                }
//
//
//                // Add new marker to the Google Map Android API V2
//                mMap.addMarker(options);
//
//                // Checks, whether start and end locations are captured
//                if (MarkerPoints.size() >= 2) {
//                    LatLng origin = MarkerPoints.get(0);
//                    LatLng dest = MarkerPoints.get(1);
//
//                    // Getting URL to the Google Directions API
//                    String url = getUrl(origin, dest);
//                    Log.d("onMapClick", url.toString());
//                    FetchUrl FetchUrl = new FetchUrl();
//
//                    // Start downloading json data from Google Directions API
//                    FetchUrl.execute(url);
//                    //move map camera
//                    mMap.moveCamera(CameraUpdateFactory.newLatLng(origin));
//                    mMap.animateCamera(CameraUpdateFactory.zoomTo(18));
//                }



            }
        });

    }

    private void addDefaultMarkers() {
        minArray = new double[ambulanceLocationArrayList.size()];
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        for (int i = 0; i < ambulanceLocationArrayList.size(); i++) {

            options.position(ambulanceLocationArrayList.get(i));
            options.icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital_marker));
            mMap.addMarker(options);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
            LatLng tempDest = ambulanceLocationArrayList.get(i);
            Log.e("tempDest", String.valueOf(tempDest));

//            double d = distanceBetween(tempOrigin, tempDest);
//            Log.e("distanceBetween", String.valueOf(d));
//            d = Double.parseDouble(String.format("%.2f", d));
//            minArray[i] = d;

            double distance = checkDistance(latitude,longitude , ambulanceLocationArrayList.get(i).latitude , ambulanceLocationArrayList.get(i).longitude);
//            Log.e("CHECK latitude longitude : "+String.valueOf(latitude) , String.valueOf(longitude));
//            Log.e("CHECK DISTANCE : "+i , String.valueOf(distance));
            distance = Double.parseDouble(String.format("%.2f", distance));
            Log.e("CHECK DISTANCE : "+i , String.valueOf(distance));
            minArray[i] = distance;
            Log.e("minArray SIZE : " , String.valueOf(minArray.length));
            minValue = findMin(minArray);


            builder.include(ambulanceLocationArrayList.get(i));



//            km.setText("Nearest Ambulance Distance : " +minValue + " Km");
//            MarkerOptions userMarker = new MarkerOptions();

            stopLoader();
        }
        LatLngBounds bounds = builder.build();
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.10); // offset from edges of the map 10% of screen
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
        mMap.animateCamera(cu);

    }

    private static void userLocationUpdate(){
        Log.e("USERMARKER", String.valueOf(userMarker.getPosition()));
        LatLng tempOrigin = new LatLng(latitude, longitude);
        LatLng latLng = new LatLng(latitude,longitude);
        userMarker.position(latLng);
        userMarker.title("User Location");
        mMap.addMarker(userMarker).showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(tempOrigin));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(13));
    }

    private String getUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = dest.latitude + "," + dest.longitude;

        String url = "https://maps.googleapis.com/maps/api/directions/json?origin=" + str_origin
                + "&destination=" + str_dest + "&key=" + getResources().getString(R.string.google_maps_key) + "&sensor=false";

        return url;
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setInterval(MIN_TIME);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
//            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DIST, this);
        } else {

        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Connection Failed", Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onLocationChanged(@NonNull Location location) {

//        editTextLatitude.setText(Double.toString(location.getLatitude()));
//        editTextLongitude.setText(Double.toString(location.getLongitude()));


        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
//        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        latLng = new LatLng(latitude,longitude);
        databaseReference.child("latitude").push().setValue(latitude);
        databaseReference.child("longitude").push().setValue(longitude);

        if (firstTimeUpdate) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            addDefaultMarkers();
            firstTimeUpdate = false;
//            userLocationUpdate();
        }

//

//        if (firstTimeUpdate == true){
//            for (int i = 0; i<ambulanceLocationArrayList.size(); i++) {
//                double distance = checkDistance(latitude, longitude, ambulanceLocationArrayList.get(i).latitude, ambulanceLocationArrayList.get(i).longitude);
////            Log.e("CHECK latitude longitude : "+String.valueOf(latitude) , String.valueOf(longitude));
////            Log.e("CHECK DISTANCE : "+i , String.valueOf(distance));
//                distance = Double.parseDouble(String.format("%.2f", distance));
//
////            Log.e("CHECK DISTANCE : "+i , String.valueOf(distance));
//                minArray[i] = distance;
//
//                double minValue = findMin(minArray);
//                km.setText("Nearest Ambulance Distance : " + minValue);
//                Log.e("Nearest Ambulance Distance : " , String.valueOf(minValue));
//
//            }
//            min_index = 0;
//            firstTimeUpdate = false;
//        }



        Log.e("onLocationChanged" +String.valueOf(latitude), String.valueOf(longitude));

//        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));

        GetAddressTask task = new GetAddressTask(getApplicationContext());
        task.execute();

    }


    private void fetchLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    mMap.setMyLocationEnabled(true);
                    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

                    for (int i = 0; i < ambulanceLocationArrayList.size(); i++) {

                        options.position(ambulanceLocationArrayList.get(i));
                        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital_marker));

                        mMap.addMarker(options);
//                        mMap.moveCamera(CameraUpdateFactory.newLatLng(Mithibai));

                        if (latitude == 0.0)
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                    }
                }
                break;
        }
    }

    public static double checkDistance(double latA, double langA, double latB, double langB) {
        LatLng latLngA = new LatLng(latA, langA);
        LatLng latLngB = new LatLng(latB, langB);

        Location locationA = new Location("point A");
        locationA.setLatitude(latLngA.latitude);
        locationA.setLongitude(latLngA.longitude);
        Location locationB = new Location("point B");
        locationB.setLatitude(latLngB.latitude);
        locationB.setLongitude(latLngB.longitude);

        double distance = locationA.distanceTo(locationB)/1000;


        return distance;
    }

    private static double findMin(double[] array) {
        double min = array[0];
                min_index = 0;
        for(int i=1;i<array.length;i++) {
            if(min > array[i]) {
                min = array[i];
                min_index = i;
            }
        }
        Log.e("MINIMUM INDEX VALUE"+String.valueOf(min), String.valueOf(min_index));
        return min;
    }

    public  static void showLoader(){
//        avLoadingIndicatorView.setVisibility(View.VISIBLE);
//        avLoadingIndicatorView.smoothToShow();

    }

    public  static void stopLoader(){
//        avLoadingIndicatorView.setVisibility(View.GONE);
//        avLoadingIndicatorView.smoothToHide();
    }

    public void updateButtonOnclick(View view){

//        databaseReference.child("latitude").push().setValue(editTextLatitude.getText().toString());
//        databaseReference.child("longitude").push().setValue(editTextLongitude.getText().toString());

    }

    private String getUrlForHospital(double latitude , double longitude , String nearbyPlace)
    {

        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("location="+latitude+","+longitude);
        googlePlaceUrl.append("&radius="+PROXIMITY_RADIUS);
        googlePlaceUrl.append("&type="+nearbyPlace);
        googlePlaceUrl.append("&sensor=true");
        googlePlaceUrl.append("&key="+ getResources().getString(R.string.google_maps_key));

        Log.d("MapsActivity", "url = "+googlePlaceUrl.toString());

        return googlePlaceUrl.toString();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}