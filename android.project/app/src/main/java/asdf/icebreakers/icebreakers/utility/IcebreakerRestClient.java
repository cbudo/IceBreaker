package asdf.icebreakers.icebreakers.utility;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.entity.StringEntity;

public class IcebreakerRestClient {

    private static AsyncHttpClient mClient = new AsyncHttpClient();

    //TODO: Add the base url
    private static final String BASE_URL = "http://URL_HERE/";

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    /*
    GET
     */
    public static void get(String url, AsyncHttpResponseHandler handler, AsyncHttpClient xClient, RequestParams params) {
        String absolute = getAbsoluteUrl(url);
        // (url, params, handler)
        xClient.get(absolute, params, handler);
    }

    public static void get(String url, AsyncHttpResponseHandler handler, AsyncHttpClient xClient) {
        String absolute = getAbsoluteUrl(url);
        // (url, params, handler)
        xClient.get(absolute, null, handler);
    }

    public static void get(String url, AsyncHttpResponseHandler handler, RequestParams params ){
        String absolute = getAbsoluteUrl(url);
        // (url, params, handler)
        mClient.get(absolute, params, handler);
    }

    public static void get(String url, AsyncHttpResponseHandler handler){
        String absolute = getAbsoluteUrl(url);
        // (url, params, handler)
        mClient.get(absolute, null, handler);
    }

    /*
    POST
     */
    public static void post(String url, AsyncHttpResponseHandler handler, AsyncHttpClient xClient, RequestParams params) {
        String absolute = getAbsoluteUrl(url);
        // (url, params, handler)
        xClient.post(absolute, params, handler);
    }

    public static void post(String url, AsyncHttpResponseHandler handler, AsyncHttpClient xClient) {
        String absolute = getAbsoluteUrl(url);
        // (url, params, handler)
        xClient.post(absolute, null, handler);
    }

    public static void post(String url, AsyncHttpResponseHandler handler, RequestParams params ){
        String absolute = getAbsoluteUrl(url);
        // (url, params, handler)
        mClient.post(absolute, params, handler);
    }

    public static void post(String url, AsyncHttpResponseHandler handler){
        String absolute = getAbsoluteUrl(url);
        // (url, params, handler)
        mClient.post(absolute, null, handler);
    }


    public void post(Context ctx, String url, JsonHttpResponseHandler jsonHttpResponseHandler, AsyncHttpClient asyncHttpClient, StringEntity entity, String s) {
        String absolute = getAbsoluteUrl(url);
        // post(android.content.Context context, java.lang.String url, org.apache.http.HttpEntity entity, java.lang.String contentType, ResponseHandlerInterface responseHandler)
        asyncHttpClient.post(ctx, absolute, entity, "application/json", jsonHttpResponseHandler);
    }
}