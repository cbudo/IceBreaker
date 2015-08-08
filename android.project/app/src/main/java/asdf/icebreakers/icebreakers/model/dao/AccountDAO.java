package asdf.icebreakers.icebreakers.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import asdf.icebreakers.icebreakers.model.ada.DbAdapter;
import asdf.icebreakers.icebreakers.model.def.IAccountDAO;
import asdf.icebreakers.icebreakers.model.dto.AccountDTO;

public class AccountDAO extends DbAdapter implements IAccountDAO {

    String TAG = this.getClass().getName();

    public static final String TABLE_NAME = "accounts";
    public static final String PRIMARY_KEY = "accountId";

    public static final String WEB_ID = "webId";
    public static final String EMAIL_ADDRESS = "emailAddress";
    public static final String PASSWORD = "password";
    public static final String WEB_PERSON_ID = "webPersonId";
    public static final String STATUS = "status";
    public static final String TOKEN = "token";


    public AccountDAO(Context c) {
        super(c);
    }

    @Override
    public AccountDTO populateObjectFromCursor(Cursor cursor) {

        // Create a new DTO
        AccountDTO dto = new AccountDTO();

        // Create variables for each cursor field
        int pky = cursor.getInt(cursor.getColumnIndex(PRIMARY_KEY) );

        int web = cursor.getInt(cursor.getColumnIndex(WEB_ID));
        String ema = cursor.getString(cursor.getColumnIndex(EMAIL_ADDRESS));
        String pas = cursor.getString(cursor.getColumnIndex(PASSWORD));
        int per = cursor.getInt(cursor.getColumnIndex(WEB_PERSON_ID));
        String sta = cursor.getString(cursor.getColumnIndex(STATUS));
        String tok = cursor.getString(cursor.getColumnIndex(TOKEN));

        // Populate the DTO with the variables
        dto.setAccountId(pky);

        dto.setWebId(web);
        dto.setEmailAddress(ema);
        dto.setPassword(pas);
        dto.setWebPersonId(per);
        dto.setStatus(sta);
        dto.setToken(tok);

        // Return the object
        return dto;

    }

    @Override
    public long create(AccountDTO dto) throws Exception {

        long result;

        // Create a blank CV object
        ContentValues cv = new ContentValues();

        // Add fields from DTO
        cv.put(EMAIL_ADDRESS, dto.getEmailAddress() );
        cv.put(WEB_ID, dto.getWebId() );
        cv.put(PASSWORD, dto.getPassword() );
        cv.put(WEB_PERSON_ID, dto.getWebPersonId() );
        cv.put(STATUS, dto.getStatus() );
        cv.put(TOKEN, dto.getToken() );

        // Open the database
        super.open();

        // Insert the values into database
        result = sqLiteDatabase.insert(TABLE_NAME, PRIMARY_KEY, cv);

        // Close the database
        super.close();

        // Return a long value
        return result;

    }

    @Override
    public AccountDTO read(String key, int value) throws Exception {

        // Return the object
        AccountDTO dto;

        // Write the query
        String sql  = "select * from " + TABLE_NAME + " where "+ key +" = '" + value +"' ";

        // Open the database
        super.open();

        // Use the rawQuery() method to make a Cursor
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        // If the Cursor has only one
        if (cursor.getCount() == 1) {
            // Go to the first record
            cursor.moveToFirst();

            // Make an object from the cursor
            dto = populateObjectFromCursor(cursor);

            // Close the database
            super.close();

        } else if (cursor.getCount() > 1){

            int count = cursor.getCount();
            // Close the database
            super.close();
            //
            throw new Exception("The query returned too many results.  Needed 1, got " + count );

        } else {
            // Close the database
            super.close();
            //
            return null;
        }

        // Return the object
        return dto;

    }

    @Override
    public AccountDTO read(String key, String value) throws Exception {

        AccountDTO dto;

        // Write the query
        String sql  = "select * from " + TABLE_NAME + " where "+ key +" = '" + value +"' ";

        // Open the database
        super.open();

        // Use the rawQuery() method to make a Cursor
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        // If the Cursor has only one
        if (cursor.getCount() == 1) {
            // Go to the first record
            cursor.moveToFirst();

            // Make an object from the cursor
            dto = populateObjectFromCursor(cursor);

            // Close the database
            super.close();

        } else if (cursor.getCount() > 1){

            int count = cursor.getCount();
            // Close the database
            super.close();
            //
            throw new Exception("The query returned too many results.  Needed 1, got " + count );
        } else {
            // Close the database
            super.close();
            //
            return null;
        }

        // Return the object
        return dto;

    }


    @Override
    public int update(AccountDTO dto) throws Exception {

        int rows;

        String where = PRIMARY_KEY + "=" + dto.getAccountId();

        // Create a blank CV object
        ContentValues cv = new ContentValues();

        // Add fields from DTO
        if(dto.getWebId() > 0){
            cv.put(WEB_ID, dto.getWebId() );

        }
        if(dto.getEmailAddress() != null){
            cv.put(EMAIL_ADDRESS, dto.getEmailAddress() );

        }
        if(dto.getPassword() != null){
            cv.put(PASSWORD, dto.getPassword() );

        }
        if(dto.getWebPersonId() > -1 ){
            cv.put(WEB_PERSON_ID, dto.getWebPersonId() );

        }
        if(dto.getStatus() != null ){
            cv.put(STATUS, dto.getStatus() );

        }
        if(dto.getToken() != null ){
            cv.put(TOKEN, dto.getToken() );

        }

        // Open the database
        super.open();

        // Run the query using the execSQL() method
        //sqLiteDatabase.execSQL(sql, null);
        rows = sqLiteDatabase.update(AccountDAO.TABLE_NAME, cv, where, null);

        // Close the database
        super.close();

        // Return int
        return rows;

    }

    @Override
    public int delete(AccountDTO dto) throws Exception {

        // Declarations
        int rows;
        String whereClause;
        String [] whereArgs = new String[1];

        // Get the primary key
        int key = dto.getAccountId();

        // Write the where clause with question mark placeholder
        whereClause = PRIMARY_KEY + "= ? ";

        // Write the where arguments
        whereArgs[0] = String.valueOf(key);

        // Open the database
        super.open();

        // Run the query
        rows = sqLiteDatabase.delete(TABLE_NAME, whereClause, whereArgs);

        // Close the database
        super.close();

        // Return the number of rows affected
        return rows;
    }

    @Override
    public ArrayList<AccountDTO> list() {

        // Declare an ArrayList
        ArrayList<AccountDTO> collection = new ArrayList<AccountDTO>();

        // Write a query
        String sql  = "select * from " + TABLE_NAME;

        // Open the database
        super.open();

        // Run the query
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        // Move the cursor to the first result
        cursor.moveToFirst();

        // While the cursor is not after the last
        while(!cursor.isAfterLast()) {

            // Populate DTO from cursor
            AccountDTO dto = populateObjectFromCursor(cursor);

            // Add to collection
            collection.add(dto);

            // Move to next
            cursor.moveToNext();
        }

        // Close the database
        super.close();

        // Return the collection
        return collection;

    }

    @Override
    public ArrayList<AccountDTO> list(String key, int value) throws Exception {

        // Declare an ArrayList
        ArrayList<AccountDTO> collection = new ArrayList<AccountDTO>();

        // Write a query
        String sql  = "select * from " + TABLE_NAME + " where "+ key +" = '" + value +"' ";

        // Open the database
        super.open();

        // Run the query
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        // Move the cursor to the first result
        cursor.moveToFirst();

        // While the cursor is not after the last
        while(!cursor.isAfterLast()) {

            // Populate DTO from cursor
            AccountDTO dto = populateObjectFromCursor(cursor);

            // Add to collection
            collection.add(dto);

            // Move to next
            cursor.moveToNext();
        }

        // Close the database
        super.close();

        // Return the collection
        return collection;

    }

    @Override
    public ArrayList<AccountDTO> list(String key, String value) throws Exception {


        // Declare an ArrayList
        ArrayList<AccountDTO> collection = new ArrayList<AccountDTO>();

        // Write a query
        String sql  = "select * from " + TABLE_NAME + " where "+ key +" = '" + value +"' ";

        // Open the database
        super.open();

        // Run the query
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        // Move the cursor to the first result
        cursor.moveToFirst();

        // While the cursor is not after the last
        while(!cursor.isAfterLast()) {

            // Populate DTO from cursor
            AccountDTO dto = populateObjectFromCursor(cursor);

            // Add to collection
            collection.add(dto);

            // Move to next
            cursor.moveToNext();
        }

        // Close the database
        super.close();

        // Return the collection
        return collection;
    }

    @Override
    public ArrayList<AccountDTO> list(String[] keys, String[] values) throws Exception {

        // Declare an ArrayList
        ArrayList<AccountDTO> collection = new ArrayList<AccountDTO>();


        // Declare a StringBuilder
        StringBuilder sb = new StringBuilder();

        // Write a query
        sb.append("select * from " + TABLE_NAME + " where ");

        for (int i=0;i<keys.length;i++){

            sb.append( keys[i] +" = '" + values[i] +"' ");

            // Append an AND if this is not the last String in the keys Array
            if(keys.length > i+1){
                sb.append(" and ");
            }

        }

        // Write the StringBuilder to a single string
        String sql = sb.toString();

        // Open the database
        super.open();

        // Run the query
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        // Move the cursor to the first result
        cursor.moveToFirst();

        // While the cursor is not after the last
        while(!cursor.isAfterLast()) {

            // Populate DTO from cursor
            AccountDTO dto = populateObjectFromCursor(cursor);

            // Add to collection
            collection.add(dto);

            // Move to next
            cursor.moveToNext();
        }

        // Close the database
        super.close();

        // Return the collection
        return collection;
    }
        


}
