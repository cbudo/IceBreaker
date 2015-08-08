package asdf.icebreakers.icebreakers.service.svc;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import asdf.icebreakers.icebreakers.model.dao.AccountDAO;
import asdf.icebreakers.icebreakers.model.def.IAccountDAO;
import asdf.icebreakers.icebreakers.model.dto.AccountDTO;
import asdf.icebreakers.icebreakers.service.def.IAccountService;
import asdf.icebreakers.icebreakers.service.def.IAuthenticationService;
import asdf.icebreakers.icebreakers.utility.IcebreakerRestClient;
import asdf.icebreakers.icebreakers.utility.WebSvcUtil;


public class WebService implements IAuthenticationService {

    String TAG = this.getClass().getName();

    private IAccountDAO dao;
    private Context context;
    private IAccountService svcAccounts;

    // Constructor
    public WebService(Context context){

        //Instantiate the DAO
        dao = new AccountDAO(context);
        this.context = context;
        startServices();

    }

    private void startServices() {
        //svcAccounts = new AccountService(context);
    }


    @Override
    public RequestHandle webLogin(String email, String password, AsyncHttpResponseHandler responseHandler) {

        AsyncHttpClient client = new AsyncHttpClient();

        RequestParams params = new RequestParams();
        params.put("username", email);
        params.put("password", password);

        RequestHandle rh = client.post("http://proximal.herokuapp.com/api/authenticate/userpass", params, responseHandler);

        return rh;
    }

    @Override
    public RequestHandle isAuthenticated(String token, AsyncHttpResponseHandler responseHandler) {
        // this could be any method that requires authentication
        AsyncHttpClient client2 = new AsyncHttpClient();
        client2.addHeader("X-Auth-Token", token);
        return client2.get(WebSvcUtil.BASE_URL + "v1/profile", responseHandler);
    }

    @Override
    public String getToken() throws Exception {
        return null;
    }

    @Override
    public String localLogin(String email, String password) throws Exception {

        String token;

        AccountDTO dto = dao.read(AccountDAO.EMAIL_ADDRESS, email);

        if(dto == null){
            token = null;
        }
        else {

            if ( password.equals( dto.getPassword().toString() ) ) {
                //String random = String.valueOf( new Date().getTime() * Math.random() );
                //token = random;
                // update user account with token
                //dto.setToken(token);
                //svcAccounts.updateAccount(dto);

                token = dto.getToken();
            } else {
                token = null;
            }
        }

        return token;
    }






    public void wGetQuestion(JsonHttpResponseHandler jsonHttpResponseHandler) throws Throwable {

        IcebreakerRestClient proximal = new IcebreakerRestClient();

        String url = "api/v1/questions";

        proximal.get(url, jsonHttpResponseHandler);

    }



    public void wGetProfile(JsonHttpResponseHandler jsonHttpResponseHandler, String token) throws Throwable {

        IcebreakerRestClient proximal = new IcebreakerRestClient();

        String url = "api/v1/profile";

        // Make the http client
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.addHeader("X-Auth-Token", token);

        proximal.get(url, jsonHttpResponseHandler, asyncHttpClient);

    }



}
