package asdf.icebreakers.icebreakers.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import asdf.icebreakers.icebreakers.R;
import asdf.icebreakers.icebreakers.utility.JserviceRestClient;
import asdf.icebreakers.icebreakers.utility.ToastUtil;

public class TestWebService extends BaseActivity {

    String TAG = this.getClass().getName();

    // Service

    // UI
    Button btnGetGoogle;
    Button btnGetIcebreakerPeople;
    Button btnGetJeopardy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_web_service);

        startServices();

        getData();

        buildView();

    }

    private void buildView() {

        btnGetGoogle = (Button) findViewById(R.id.buttonTestWebSvc_GetGoogle);

        btnGetJeopardy = (Button) findViewById(R.id.buttonTestWebSvc_GetJeopardyQuestion);

        btnGetIcebreakerPeople = (Button) findViewById(R.id.buttonTestWebSvc_GetIcebreakerPeople);

        btnGetGoogle.setOnClickListener(new View.OnClickListener() {

                                            @Override
                                            public void onClick(View v) {
                                                //When the button is clicked, call the method
                                                clickGetGoogle();
                                            }

                                        }
        );


        btnGetJeopardy.setOnClickListener(new View.OnClickListener() {

                                              @Override
                                              public void onClick(View v) {
                                                  //When the button is clicked, call the method
                                                  clickGetJeopardy();
                                              }

                                          }
        );

        btnGetIcebreakerPeople.setOnClickListener(new View.OnClickListener() {

                                              @Override
                                              public void onClick(View v) {
                                                  //When the button is clicked, call the method
                                                  clickGetIcebreakerPeople();
                                              }

                                          }
        );

    }

    private void clickGetIcebreakerPeople() {


        AsyncHttpClient client = new AsyncHttpClient();

        client.get("http://icebreakerapi.azurewebsites.net/v1", new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                Log.d(TAG, "client.onStart()");
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                Log.d(TAG, "client.onSuccess() - Status code: " + statusCode);
                // called when response HTTP status is "200 OK"

                for (Header header : headers) {
                    Log.d(TAG, header.toString());
                }

                // Make a String of the byte array
                Log.v(TAG, new String(response));

                ToastUtil.makeToast(getBaseContext(), new String(response));

//                for(byte b : response){
//                    Log.d(TAG, String.valueOf(b));
//                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                Log.d(TAG, "client.onFailure()");
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
            }

            @Override
            public void onRetry(int retryNo) {
                Log.d(TAG, "client.onRetry() - No: " + retryNo);
                // called when request is retried
            }
        });

    }

    private void clickGetJeopardy() {

        JserviceRestClient.get("api/random", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                Log.d(TAG, response.toString());
                ToastUtil.makeToast(getBaseContext(), response.toString());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray jarr) {

                Log.d(TAG, jarr.toString());
                ToastUtil.makeToast(getBaseContext(), jarr.toString());

                //QuestionDTO dto = new QuestionDTO();

                // Loop through all the objects in the JSONArray
                for (int i = 0; i < jarr.length(); i++) {
                    // New obj
                    JSONObject obj = new JSONObject();

                    // Try, or else
                    try {
                        // Get the first object in the array
                        obj = jarr.getJSONObject(i);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    // If all went well
                    if (obj != null) {
                        Log.d(TAG, obj.toString());

                        // Start mapping JSON stuff to local db stuff
                        try {

//                            {
//                                "id":144749,
//                                "category":{
//                                    "id":1949,
//                                    "clues_count":60,
//                                    "created_at":"2014-02-11T22:57:11.504Z",
//                                    "title":"tv",
//                                    "updated_at":"2014-02-11T22:57:11.504Z"
//                                    },
//                                "updated_at":"2015-01-22T02:31:40.965Z",
//                                "invalid_count":null,
//                                "game_id":4001,
//                                "value":400,
//                                "answer":"Food TV",
//                                "category_id":1949,
//                                "created_at":"2015-01-22T02:31:40.965Z",
//                                "question":"\"Have Fork, Will Travel\", \"Kid in a Candy Store\" & \"Glutton for Punishment\" were shows on this network",
//                                "airdate":"2012-10-29T12:00:00.000Z"
//                            }

                            int id;
                            int invalidCount;
                            String updatedAt;

                            id = obj.getInt("id");
                            updatedAt = obj.getString("updated_at");

                            if (!obj.isNull("invalid_count")) {
                                invalidCount = obj.getInt("invalid_count");
                            }

                            if (obj.get("category") instanceof JSONObject) {
                                JSONObject category = obj.getJSONObject("category");
                                Log.d(TAG, category.toString());
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                }

            }
        });



    }

    private void clickGetGoogle() {

        AsyncHttpClient client = new AsyncHttpClient();

        client.get("http://www.google.com", new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                Log.d(TAG, "client.onStart()");
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                Log.d(TAG, "client.onSuccess() - Status code: " + statusCode);
                // called when response HTTP status is "200 OK"

                for (Header header : headers) {
                    Log.d(TAG, header.toString());
                }

                // Make a String of the byte array
                Log.v(TAG, new String(response));

                ToastUtil.makeToast(getBaseContext(), new String(response));

//                for(byte b : response){
//                    Log.d(TAG, String.valueOf(b));
//                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                Log.d(TAG, "client.onFailure()");
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
            }

            @Override
            public void onRetry(int retryNo) {
                Log.d(TAG, "client.onRetry() - No: " + retryNo);
                // called when request is retried
            }
        });

    }

    private void getData() {

    }

    private void startServices() {

    }

}
