package asdf.icebreakers.icebreakers.service.svc;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;

import java.util.ArrayList;

import asdf.icebreakers.icebreakers.model.dao.AccountDAO;
import asdf.icebreakers.icebreakers.model.def.IAccountDAO;
import asdf.icebreakers.icebreakers.model.dto.AccountDTO;
import asdf.icebreakers.icebreakers.service.def.IAccountService;


public class AccountService implements IAccountService {

    String TAG = this.getClass().getName();

    //Declare an instance of the data access object
    IAccountDAO dao;
    Context context;

    /*
    Constructor
     */
    public AccountService(Context context){

        //Instantiate the data access object
        dao = new AccountDAO(context);

        this.context = context;

        startServices();
    }

    private void startServices() {

    }

    @Override
    public RequestHandle postAccount(String email, String password, AsyncHttpResponseHandler responseHandler) {
        return null;
    }

    @Override
    public long insertAccount(AccountDTO dto) throws Exception {

        // Declare local variables
        long accountId;
        long personId;
        AccountDTO account;


        // Check for duplicates
        // Get a list of accounts with the same email address
        ArrayList<AccountDTO> accounts = dao.list(AccountDAO.EMAIL_ADDRESS, dto.getEmailAddress() );

        if (accounts.size() > 0) {
            return -1;
        }

        // The create method returns a long value representing the pk of the new record
        accountId = dao.create(dto);

        // Refresh the DTO so that it has the new accountID
        dto = this.selectAccountById( (int) accountId);

//        // At this point, we have to create a Person record to correspond to the Account record
//        person = new PersonDTO();
//
//        // We don't know First Name yet, unless we get it from Google account
//        person.setFirstName("");
//
//        // We don't know last name yet
//        person.setLastName("");
//
//        // Set the accountId of the person
//        person.setWebAccountId((int) accountId);
//
//        // Make a person record for this account
//        personId = svcPersons.insertPerson(person);

//        // Set the personId of the DTO to the id of the new person record
//        dto.setWebPersonId((int) personId);

        // Update the Account record just created with the PersonID of the new Person record
        this.updateAccount(dto);

        return accountId;

    }

    @Override
    public AccountDTO selectAccountById(int value) throws Exception {
        AccountDTO account = dao.read(AccountDAO.PRIMARY_KEY, value);
        return account;
    }

    @Override
    public AccountDTO selectAccountByToken(String value) throws Exception {
        AccountDTO account = dao.read(AccountDAO.TOKEN, value);
        return account;
    }

    @Override
    public AccountDTO selectAccountByEmailAddress(String emailAddress) throws Exception {

        AccountDTO account = dao.read(AccountDAO.EMAIL_ADDRESS, emailAddress);
        return account;

    }

    @Override
    public int updateAccount(AccountDTO dto) throws Exception {
        int rows = -1;

        try {
            rows = dao.update(dto);

        } catch (Exception e) {
            e.printStackTrace();

        }

        return rows;

    }

    @Override
    public int deleteAccount(AccountDTO account) throws Exception {

        return dao.delete(account);

    }

    @Override
    public ArrayList<AccountDTO> selectAllAccounts() {
        ArrayList<AccountDTO> accounts = dao.list();
        return accounts;
    }

    @Override
    public boolean cacheLogin(String emailAddress, String password, String token) throws Exception {

        /*
        TODO: This needs to be cleaned up.
        Need to put some thought into how authentication should work through the web
        and what we should be storing locally, probably not password.
        Web service should send back not just a token, but also a primary key for the account.
        */

        int accountId;

        AccountDTO dto = new AccountDTO();
        dto.setEmailAddress(emailAddress); //username
        dto.setPassword(password); //password
        dto.setToken(token);

        // Check to see if a record exists for this account
        AccountDTO existing = dao.read(AccountDAO.EMAIL_ADDRESS, emailAddress);

        // Create a record if none exists
        if (existing == null){
            accountId = (int) dao.create(dto); // This happens the first time someone logs in - It should happen only once
            Log.d(TAG,"New account: " + accountId);
        }
        else {
            // Compare tokens
            String token1 = existing.getToken();
            String token2 = dto.getToken();

            if (token1 == null){
                Log.d(TAG, "Existing token 1 is null"); // Should happen once
            }
            else if (token2 == null) {
                Log.d(TAG, "New token 2 is null"); //Should never happen
            }
            else if( token1.equals(token2) ){
                Log.d(TAG, "The tokens are the same"); // Probably should never happen
            }
            else{
                Log.d(TAG, "The tokens are different"); // Will happen often

                // Update the record
                //dto.setAccountId( existing.getAccountId() );
                existing.setToken(token);

//                dto.setWebId(-1); // Setting this value to -1 causes it NOT to be updated in the dao update method
//                dto.setWebPersonId(-1); // Setting this value to -1 causes it NOT to be updated in the dao update method

                dao.update(existing);
                Log.d(TAG, "Updated account [accountId: " + existing.getAccountId() + "; webId:" + existing.getWebId() +"]" );

            }

        }
        /*
        We aren't doing authentication locally. No need for this logic
         */
        //        if(dao.list().contains(emailAddress)
        //                && dao.read(emailAddress).getEmailAddress().equals(temp.getEmailAddress())
        //                && dao.selectAccountById(emailAddress).getPassword().equals(temp.getPassword())
        //           ){
        //            return true;
        //        }else{
        //            return false;
        //        }

        return false;

    }

    @Override
    public boolean accountExists(String emailAddress) throws Exception {

        int count;
        boolean exists;

        ArrayList<AccountDTO> accounts = dao.list(AccountDAO.EMAIL_ADDRESS, emailAddress);

        count = accounts.size();

        if(count > 0){
            exists = true;
        }
        else{
            exists = false;
        }

        return exists;

    }
}
