package com.gigasecintl.michaelvons.gigazonemain3;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class QuickReportActivity extends AppCompatActivity implements OnMapReadyCallback, ConnectionCallbacks, OnConnectionFailedListener, LocationListener, AdapterView.OnItemSelectedListener {

    private GoogleMap mMap;
    protected GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;
    protected static final String TAG = "QuickReportActivity";
    protected LocationRequest mLocationRequest;
    protected Location mCurrentLocation;
    public double userLatitude;
    public double userLongitude;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 1;
    public double longitude;
    private Spinner mSpinnerCaseType;
    private Button btnSubmit_SubmitReportActivity;

    private EditText mEditTextPhone;
    private EditText mEditTextLocation;
    private Double userLastLatitude;
    private Double userCurrentLatitude;
    private Double userCurrentLongitude;
    private Double userLastLongitude;
    public double lat;
    public double longi;
    public double longid;
    private Button btnSubmit2_SubmitReportActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_report);
        checkLocationPermission();

        mSpinnerCaseType = (Spinner) findViewById(R.id.spinnerCaseType);

        ArrayAdapter<CharSequence> caseTypeAdapter = ArrayAdapter.createFromResource(this,
                R.array.caseType_array, android.R.layout.simple_spinner_item);
        caseTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerCaseType.setAdapter(caseTypeAdapter);

        mSpinnerCaseType.setOnItemSelectedListener(this);

        mEditTextLocation = (EditText) findViewById(R.id.editTextLocation);
        mEditTextPhone = (EditText) findViewById(R.id.editTextPhone);
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        createLocationRequest();
        Log.i(TAG, "builGoogleApiClient: API BUILT");
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                //  mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), 14));

                userLatitude = mLastLocation.getLatitude();
                userLongitude = mLastLocation.getLongitude();
                Log.i(TAG, "onConnected: LAST LAT & LNG is ----- " + userLatitude + "---" + userLongitude);

                // tellLon();
            }
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mCurrentLocation == null) {
                startLocationUpdates();
            } else {
                Log.i(TAG, "onConnected: current LOC " + mCurrentLocation.getLatitude() + "***" + mCurrentLocation.getLongitude());

            }

        }


    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000);
        Log.i(TAG, "createLocationRequest: REQUESTING LOCATION");
    }

    private void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            Log.i(TAG, "startLocationUpdates: START LOCATION UPDATE");
        }

    }


    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()), 14));


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    public void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);

                Log.i(TAG, "checkLocationPermission: WE NEED PERMISSION");


            }

        } else {
            Log.i(TAG, "checkLocationPermission: NO PERMISSION REQUIRED");
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {

                if (mGoogleApiClient == null) {
                    buildGoogleApiClient();
                }
                // mMap.setMyLocationEnabled(true);
                mGoogleApiClient.connect();
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                        mGoogleApiClient.connect();
                    }
                    Log.i(TAG, "onRequestPermissionsResult: PERMISSION GRANTED");

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }


    public void submitReport(View view) {

        if(mEditTextLocation.getText().toString().matches("") || mEditTextPhone.getText().toString().matches("") || mSpinnerCaseType.getSelectedItem().toString().matches("Choose a category")){
            Toast.makeText(this,"Oops!. Enter text or choose category ",Toast.LENGTH_SHORT).show();
        }else {
            Report submitReport = new Report("null", (String) mSpinnerCaseType.getSelectedItem(), String.valueOf(mEditTextLocation.getText()), String.valueOf(mEditTextPhone.getText()), "congo meat joined a lorry that had kidnappers", getTime(), userLatitude, userLongitude);
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("reports").push();
            myRef.setValue(submitReport);
            mEditTextLocation.setText("");
            mEditTextPhone.setText("");

            Toast.makeText(this, "Report Sent Succesfully", Toast.LENGTH_SHORT).show();

    /*    MapsActivity mMapsActivity = new MapsActivity();
        mMapsActivity.retrieveReport();*/

            Log.i(TAG, "submitReport: to Be Submitted " + (String) mSpinnerCaseType.getSelectedItem() + " -- " + String.valueOf(mEditTextLocation.getText()) + " --- " + String.valueOf(mEditTextPhone.getText()) + " ---- " + getTime() + " -- " + userLatitude + " --- " + userLongitude);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private long getTime() {
        long invertedTimestamp = -1 * (System.currentTimeMillis());
        Log.i(TAG, "retrieveReport: Inverted time is " + invertedTimestamp);
        return invertedTimestamp;
    }

}
