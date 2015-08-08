package asdf.icebreakers.icebreakers.utility;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by b on 1/15/15.
 */
public class AddressUtil {

    static final String TAG = AddressUtil.class.getClass().getName();

    public static void getAddressFromLocation(final Location location, final Context context, final Handler handler) {
        Log.d(TAG, "AddressUtil.getAddressFromLocation()");

        if(location == null){
            Log.d(TAG, "AddressUtil.getAddressFromLocation() - Location is null");
        }
        else{

            // Create the thread
            Thread thread = new Thread() {

                // This thread has a single run() method
                @Override public void run() {

                    boolean noNetwork = false;

                    Address address = null;

                    String adminArea = "";
                    String countryCode = "";
                    String featureName = "";
                    String locality = "";
                    //locale = bundle.getString("");
                    String phone = "";
                    String postalCode = "";
                    String premises = "";
                    String subAdminArea = "";
                    String subLocality = "";
                    String subThoroughfare = "";
                    String thoroughfare = "";
                    String url = "";

                    Geocoder geocoder = new Geocoder(context, Locale.getDefault());

                    try {
                        List<Address> list = geocoder.getFromLocation(
                                location.getLatitude(),
                                location.getLongitude(),
                                1);

                        if (list != null && list.size() > 0) {
                            address = list.get(0);

                            adminArea = address.getAdminArea();
                            countryCode = address.getCountryCode();
                            featureName = address.getFeatureName();
                            locality = address.getLocality();
                            //locale = bundle.getString("");
                            phone = address.getPhone();
                            postalCode = address.getPostalCode();
                            premises = address.getPremises();
                            subAdminArea = address.getSubAdminArea();
                            subLocality = address.getSubLocality();
                            subThoroughfare = address.getSubThoroughfare();
                            thoroughfare = address.getThoroughfare();
                            url = address.getUrl();

                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Unable to connect to Geocoder", e);
                        noNetwork = true;
                    } finally {
                        Message msg = Message.obtain();
                        msg.setTarget(handler);
                        if (address != null) {
                            msg.what = 1;

                            // Make a bundle
                            Bundle bundle = new Bundle();

                            // Put stuff in the bundle
                            bundle.putString("adminArea", adminArea);
                            bundle.putString("countryCode", countryCode);
                            bundle.putString("featureName", featureName);
                            bundle.putString("locality", locality);
                            //locale = bundle.putString("");
                            bundle.putString("phone", phone);
                            bundle.putString("postalCode", postalCode);
                            bundle.putString("premises", premises);
                            bundle.putString("subAdminArea", subAdminArea);
                            bundle.putString("subLocality", subLocality);
                            bundle.putString("subThoroughfare", subThoroughfare);
                            bundle.putString("thoroughfare", thoroughfare);
                            bundle.putString("url", url);

                            // Send the bundle
                            msg.setData(bundle);
                        } else if (noNetwork){
                                msg.what = 2; // 2 is an arbitrary value being used here to indicate this event: no internet
                                Bundle bundle = new Bundle();
                                bundle.putString("message", "network unavailable");
                                msg.setData(bundle);
                            
                        } else
                            msg.what = 0;
                        msg.sendToTarget();
                    }
                }
            };

            // Start the thread
            thread.start();
        }

    }

}
