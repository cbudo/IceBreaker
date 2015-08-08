package asdf.icebreakers.icebreakers.service.def;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;

import java.util.ArrayList;

import asdf.icebreakers.icebreakers.model.dto.AccountDTO;

public interface IAccountService {


    RequestHandle postAccount(String email, String password, AsyncHttpResponseHandler responseHandler);

    public long insertAccount(AccountDTO account) throws Exception;
    public AccountDTO selectAccountById(int accountId) throws Exception;

    AccountDTO selectAccountByToken(String value) throws Exception;

    public AccountDTO selectAccountByEmailAddress(String emailAddress) throws Exception;
    public int updateAccount(AccountDTO account) throws Exception;
    public int deleteAccount(AccountDTO account) throws Exception;
    public ArrayList<AccountDTO> selectAllAccounts();
//
    public boolean cacheLogin(String emailAddress, String password, String token) throws Exception;

    public boolean accountExists(String emailAddress) throws Exception;


}

