package asdf.icebreakers.icebreakers.service.def;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;

/**
 * Created by silbermm on 10/26/14.
 */
public interface IAuthenticationService {

    RequestHandle webLogin(String email, String password, AsyncHttpResponseHandler responseHandler) throws Exception;
    RequestHandle isAuthenticated(String token, AsyncHttpResponseHandler responseHandler) throws Exception;

    String getToken() throws Exception;

    String localLogin(String email, String password) throws Exception;

}
