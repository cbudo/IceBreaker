package asdf.icebreakers.icebreakers.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import asdf.icebreakers.icebreakers.R;
import asdf.icebreakers.icebreakers.service.svc.SpotService;
import asdf.icebreakers.icebreakers.service.def.ISpotService;
import asdf.icebreakers.icebreakers.utility.AddressUtil;
import asdf.icebreakers.icebreakers.utility.StateUtil;

public class IceLocation extends BaseActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    private final String TAG = this.getClass().getName();

    // Services
    private ISpotService svcSpots;

    // Location
    private LocationManager mLocManager;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private boolean mRequestingLocationUpdates;
    private Location mCurrentLocation;
    private Address mCurrentAddress;
    private String mLastUpdateTime;

    // Data

    private String mAddress;
    private String mCity;
    private String mState;
    private String mZip;

    private String mLatitude = "";
    private String mLongitude = "";
    private String mAccuracy = "";
    private String mAltitude = "";
    private String mBearing = "";
    private String mProvider = "";
    private String mSpeed = "";

    private String mAdminArea = "";
    private String mCountryCode = "";
    private String mFeatureName = "";
    private String mLocality = "";
    private String mPhone = "";
    private String mPostalCode = "";
    private String mPremises = "";
    private String mSubAdminArea = "";
    private String mSubLocality = "";
    private String mSubThoroughfare = "";
    private String mThoroughfare = "";
    private String mUrl = "";

    // Execution
    private boolean mGotData;

    // UI
    private ListView lstAddress;
    private ListView lstLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Run.onCreate()");

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ice_location);

        startServices(this);

        try {
            getData(getIntent().getExtras());
        } catch (Exception e) {
            e.printStackTrace();
        }

        buildView();

    }

    @Override
    protected void onStart() {
        Log.d(TAG, "Run.onStart()");

        super.onStart();


        buildGoogleApiClient();

        mGoogleApiClient.connect();
        Log.d(TAG, "Run.onStart() - mGoogleApiClient.connect()");


    }

    @Override
    protected void onPause() {
        Log.d(TAG, "Stop.onPause()");

        super.onPause();

        saveData();

        mGotData = false;

        stopLocationUpdates();

    }

    @Override
    protected void onResume(){
        Log.d(TAG, "Stop.onResume()");

        super.onResume();

        if(!mGotData){  //This check prevents onResume calling getData on first load
            try {
                getData(getIntent().getExtras());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        buildView();

    }

    @Override
    protected void onStop(){
        Log.d(TAG, "Stop.onStop()");

        super.onStop();

    }

    /*
    The methods createLocationRequest() and startLocationUpdates() should only be called from
    onConnected()
     */
    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "Stop.onConnected()");

        createLocationRequest();

        startLocationUpdates();

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "Stop.onConnectionSuspended()");

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "Stop.onConnectionFailed()");

        AlertDialog.Builder builder;

        builder = new AlertDialog.Builder(this);
        builder.setTitle("Connection Failed");
        builder.setMessage(connectionResult.toString() );

        // Set the positive button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Stop location updates to preserve battery
                stopLocationUpdates();

                dialog.cancel();

                // Go back to the Run screen
                finish();

            }
        });


        builder.show();

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "Stop.onLocationChanged()");

        resetAddressFields();


//        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        mCurrentLocation = location;

        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());

        if (mCurrentLocation != null) {
            mAccuracy = String.valueOf(mCurrentLocation.getAccuracy());
            mAltitude = String.valueOf(mCurrentLocation.getAltitude());
            mBearing = String.valueOf(mCurrentLocation.getBearing());
            mLatitude = String.valueOf(mCurrentLocation.getLatitude());
            mLongitude = String.valueOf(mCurrentLocation.getLongitude());
            mProvider = String.valueOf(mCurrentLocation.getProvider());
            mSpeed = String.valueOf(mCurrentLocation.getSpeed());
            mLastUpdateTime = String.valueOf(mCurrentLocation.getTime());

            updateLocationList();

            // Reverse geocode the location in a separate thread
            AddressUtil.getAddressFromLocation(mCurrentLocation, getBaseContext(), new GeocodeLocationHandler(this));
        }

        // Stop location updates. One update is enough.
        stopLocationUpdates();

    }

    private void resetAddressFields(){

        mCurrentAddress = new Address(new Locale("en"));

        mAdminArea = "";
        mCountryCode = "";
        mFeatureName = "";
        mLocality = "";
//        Locale locale = null;
        mPhone = "";
        mPostalCode = "";
        mPremises = "";
        mSubAdminArea = "";
        mSubLocality = "";
        mSubThoroughfare = "";
        mThoroughfare = "";
        mUrl = "";

        mCurrentAddress.setAdminArea(mAdminArea);
        mCurrentAddress.setCountryCode(mCountryCode);
        mCurrentAddress.setFeatureName(mFeatureName);
        mCurrentAddress.setLocality(mLocality);
        mCurrentAddress.setPhone(mPhone);
        mCurrentAddress.setPostalCode(mPostalCode);
        mCurrentAddress.setPremises(mPremises);
        mCurrentAddress.setSubAdminArea(mSubAdminArea);
        mCurrentAddress.setSubLocality(mSubLocality);
        mCurrentAddress.setSubThoroughfare(mSubThoroughfare);
        mCurrentAddress.setThoroughfare(mThoroughfare);
        mCurrentAddress.setUrl(mUrl);

    }

    private void updateLocationList(){

        ArrayList<String> locationDetails = new ArrayList<String>();

        locationDetails.add("Latitude: " + mLatitude);
        locationDetails.add("Longitude: " + mLongitude);
        locationDetails.add("Accuracy: " + mAccuracy);
        locationDetails.add("Altitude: " + mAltitude);
        locationDetails.add("Bearing: " + mBearing);
        locationDetails.add("Provider: " + mProvider);
        locationDetails.add("Speed: " + mSpeed);
        locationDetails.add("Time: " + mLastUpdateTime);

        // Build an adapter
        ArrayAdapterLocationInfo adapter = new ArrayAdapterLocationInfo(
                this,
                locationDetails
        );

        // Set the adapter
        lstLocation.setAdapter(adapter);

    }

    private void updateAddressListView(){

        ArrayList<String> addressDetails = new ArrayList<String>();

        addressDetails.add("Address: " + mAddress );
        addressDetails.add("City: " + mCity);
        addressDetails.add("State: " + mState);
        addressDetails.add("Zip: " + mZip);
//        addressDetails.add("Country: " + mCountryCode);


        // Build an adapter
        ArrayAdapterAddressInfo adapter = new ArrayAdapterAddressInfo(
                this,
                addressDetails
        );

        // Set the adapter
        lstAddress.setAdapter(adapter);

    }

    private static class GeocodeLocationHandler extends Handler {

        private final WeakReference<IceLocation> mTarget;

        public GeocodeLocationHandler(IceLocation context)
        {
            mTarget = new WeakReference<IceLocation>(context);
        }

        @Override
        public void handleMessage(Message message) {
            IceLocation activity = mTarget.get();
            if (activity != null) {

                Log.d(activity.TAG, "LastLocation.GeocodeLocationHandler.handleMessage()");

                switch (message.what) {
                    case 1:
                        Bundle bundle = message.getData();

                        activity.mAdminArea = bundle.getString("adminArea");
                        activity.mCountryCode = bundle.getString("countryCode");
                        activity.mFeatureName = bundle.getString("featureName");
                        activity.mLocality = bundle.getString("locality");
                        //locale = bundle.getString("");
                        activity.mPhone = bundle.getString("phone");
                        activity.mPostalCode = bundle.getString("postalCode");
                        activity.mPremises = bundle.getString("premises");
                        activity.mSubAdminArea = bundle.getString("subAdminArea");
                        activity.mSubLocality = bundle.getString("subLocality");
                        activity.mSubThoroughfare = bundle.getString("subThoroughfare");
                        activity.mThoroughfare = bundle.getString("thoroughfare");
                        activity.mUrl = bundle.getString("url");

                        activity.mAddress = (activity.mSubThoroughfare == null ? "" : activity.mSubThoroughfare) + " " + (activity.mThoroughfare == null ? "" : activity.mThoroughfare);
                        activity.mCity = (activity.mLocality == null ? "" : activity.mLocality) ;
                        StateUtil states = new StateUtil();
                        activity.mState = (activity.mAdminArea == null ? "" : states.getStates().get(activity.mAdminArea) );
                        activity.mZip = (activity.mPostalCode == null ? "" : activity.mPostalCode);

                        activity.updateAddressListView();

                        break;
                    case 2: // Arbitrary value indicating no network

                        activity.handleAddressUnavailable();

                        break;
                    default:
                        // this shouldn't happen
                }

            }
        }
    }

    private void handleAddressUnavailable(){
        ArrayList<String> message = new ArrayList<String>();

        message.add("Address unavailable");

        // Build an adapter
        ArrayAdapterAddressInfo adapter = new ArrayAdapterAddressInfo(
                this,
                message
        );

        // Set the adapter
        lstAddress.setAdapter(adapter);

    }

    synchronized void buildGoogleApiClient() {
        Log.d(TAG, "IceLocation.buildGoogleApiClient()");

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

    }

    void createLocationRequest() {
        Log.d(TAG, "IceLocation.createLocationRequest()");

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    void startLocationUpdates() {
        Log.d(TAG, "IceLocation.startLocationUpdates()");

        if (mGoogleApiClient != null && mGoogleApiClient.isConnected() && !mRequestingLocationUpdates) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            mRequestingLocationUpdates = true;
        }

    }

    void stopLocationUpdates() {
        Log.d(TAG, "IceLocation.stopLocationUpdates()");

        if (mGoogleApiClient != null && mGoogleApiClient.isConnected() ) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mRequestingLocationUpdates = false;
        }

        if(mGoogleApiClient != null){

            mGoogleApiClient.unregisterConnectionCallbacks(this);
            mGoogleApiClient.disconnect();
            mGoogleApiClient = null;
        }

    }

    private void startServices(Context ctx) {
        Log.d(TAG, "IceLocation.startServices()");

        svcSpots = new SpotService(ctx);

    }

    private void buildView() {
        Log.d(TAG, "IceLocation.buildView()");


        lstAddress = (ListView) findViewById(R.id.listLastLocationAddress);
        lstLocation = (ListView) findViewById(R.id.listLastLocationLocation);



    }


    private void saveData() {
        Log.d(TAG, "IceLocation.saveData()");





    }

    /*
    Only call this method during onCreate()
     */
    private void getData(Bundle extras) throws Exception {
        Log.d(TAG, "IceLocation.getData()");

        boolean bundleNewStop;
        int bundleRouteId;
        int bundleRunId;
        int bundleStopId;

        if (extras != null) {



        }

        // Reset the intent before onResume() is called or onCreate() is called again
        getIntent().removeExtra("NEW_SPOT");

        mGotData = true;

    }




    /*
    This is a private inner class.
    This class extends ArrayAdapter for the purpose of customizing the way that the ArrayAdapter
        creates each row in the ListView.
    This adapter is defined with a Generic. In this case the Generic is <String>.
    That means that the Adapter expects to be using String objects.
    Generics allow "a type or method to operate on objects of various types while providing compile-time type safety."
    http://en.wikipedia.org/wiki/Generics_in_Java

    In this ArrayAdapter, we override the getView() method.
    The getView() method is given an integer, View, and ViewGroup.
    The getView() method returns a View.

    The View object returned by the getView() method becomes the View displayed in the row of the
        ListView at position of the given integer.

    The position integer also refers to the position in the list of String objects of a specific
    String. That is the String we want to work with in the logic of the getView() method.

    */
    private class ArrayAdapterAddressInfo extends ArrayAdapter<String> {

        // Declare a local ArrayList
        private final ArrayList<String> adapterList;

        // Private class constructor
        public ArrayAdapterAddressInfo(Context context, ArrayList<String> list) {

            // Call the super() constructor
            super(context, R.layout.list_view_item_spot_details, list);

            this.adapterList = list;

        }

        // Override the getView() method of the ArrayAdapter
        @Override
        public View getView(int position, View listViewRow, ViewGroup listView) {
            //Log.d(TAG, "LastLocation.ArrayAdapterAddressInfo.getView()");

            // Use the position integer given to get() a GenericDTO from the list of GenericDTO objects
            // defined in the constructor of this ArrayAdapter.
            String value = adapterList.get(position);

            //Declare a View object to be customized and returned
            View customView = listViewRow;

            //If the local view is null, inflate XML
            if (customView == null) {

                // Make an inflater
                LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                // Instantiate a layout XML file into its corresponding View objects
                customView = inflater.inflate(R.layout.list_view_item_spot_details, listView, false);

            }

            // Instantiate UI components from the inflated xml resource
            TextView textView = (TextView) customView.findViewById(R.id.textCustomListItemRowLastLocation);

            // Set the properties of the UI components
            textView.setText(value);

            // Give a View back, because that's what the getView() method does
            return customView;

        }
    }

    private class ArrayAdapterLocationInfo extends ArrayAdapter<String> {

        // Declare a local ArrayList
        private final ArrayList<String> adapterList;

        // Private class constructor
        public ArrayAdapterLocationInfo(Context context, ArrayList<String> list) {

            // Call the super() constructor
            super(context, R.layout.list_view_item_row_last_location, list);

            this.adapterList = list;

        }

        // Override the getView() method of the ArrayAdapter
        @Override
        public View getView(int position, View listViewRow, ViewGroup listView) {
            //Log.d(TAG, "LastLocation.ArrayAdapterLocationInfo.getView()");

            // Use the position integer given to get() a GenericDTO from the list of GenericDTO objects
            // defined in the constructor of this ArrayAdapter.
            String value = adapterList.get(position);

            //Declare a View object to be customized and returned
            View customView = listViewRow;

            //If the local view is null, inflate XML
            if (customView == null) {

                // Make an inflater
                LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                // Instantiate a layout XML file into its corresponding View objects
                customView = inflater.inflate(R.layout.list_view_item_row_last_location, listView, false);

            }

            // Instantiate UI components from the inflated xml resource
            TextView textView = (TextView) customView.findViewById(R.id.textCustomListItemRowLastLocation);

            // Set the properties of the UI components
            textView.setText(value);

            // Give a View back, because that's what the getView() method does
            return customView;

        }

    }
}
