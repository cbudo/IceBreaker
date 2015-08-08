package asdf.icebreakers.icebreakers.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import asdf.icebreakers.icebreakers.model.ada.DbAdapter;
import asdf.icebreakers.icebreakers.model.dto.SpotDTO;

public class SpotDAO extends DbAdapter implements asdf.icebreakers.icebreakers.model.def.ISpotDAO {

    public static final String TABLE_NAME = "spots";
    public static final String PRIMARY_KEY = "spotId";

    public static final String SPOT_TIME = "spotTime";
    public static final String SPOT_NAME = "spotName";
    public static final String SPOT_ADDRESS = "spotAddress";
    public static final String SPOT_CITY = "spotCity";
    public static final String SPOT_STATE = "spotState";
    public static final String SPOT_ZIP = "spotZip";
    public static final String SPOT_PRECISION = "precision";
    public static final String SPOT_LATITUDE = "latitude";
    public static final String SPOT_LONGITUDE = "longitude";

    public SpotDAO(Context ctx) {
        super(ctx);
    }

    private SpotDTO populateObjectFromCursor(Cursor cursor) {

        // Create a new DTO
        SpotDTO dto = new SpotDTO();

        // Create variables for each cursor field
        int pky = cursor.getInt(cursor.getColumnIndex(PRIMARY_KEY) );

        String sti = cursor.getString(cursor.getColumnIndex(SPOT_TIME));
        String sna = cursor.getString(cursor.getColumnIndex(SPOT_NAME));
        String spa = cursor.getString(cursor.getColumnIndex(SPOT_ADDRESS));
        String cit = cursor.getString(cursor.getColumnIndex(SPOT_CITY));
        String sta = cursor.getString(cursor.getColumnIndex(SPOT_STATE));
        String zip = cursor.getString(cursor.getColumnIndex(SPOT_ZIP));
        float pre = cursor.getFloat(cursor.getColumnIndex(SPOT_PRECISION));
        double lat = cursor.getDouble(cursor.getColumnIndex(SPOT_LATITUDE));
        double lon = cursor.getDouble(cursor.getColumnIndex(SPOT_LONGITUDE));

        // Populate the DTO with the variables
        dto.setSpotId(pky);

        dto.setSpotCity(cit);
        dto.setLatitude(lat);
        dto.setLongitude(lon);
        dto.setPrecision(pre);
        dto.setSpotName(sna);
        dto.setSpotState(sta);
        dto.setSpotAddress(spa);
        dto.setSpotTime(sti);
        dto.setSpotZip(zip);

        // Close the database
        super.close();

        // Return the object
        return dto;
    }

    @Override
    public long create(SpotDTO dto) throws Exception {

        long result;

        // Create a blank CV object
        ContentValues cv = new ContentValues();

        // Add fields from DTO
        cv.put(SPOT_TIME, dto.getSpotTime() );
        cv.put(SPOT_NAME, dto.getSpotName() );
        cv.put(SPOT_ADDRESS, dto.getSpotAddress() );
        cv.put(SPOT_CITY, dto.getSpotCity() );
        cv.put(SPOT_STATE, dto.getSpotState() );
        cv.put(SPOT_ZIP, dto.getSpotZip() );
        cv.put(SPOT_PRECISION, dto.getPrecision() );
        cv.put(SPOT_LATITUDE, dto.getLatitude() );
        cv.put(SPOT_LONGITUDE, dto.getLongitude() );

        // Open the database
        super.open();

        // Insert the values into database
        result = sqLiteDatabase.insert(TABLE_NAME, PRIMARY_KEY, cv);

        // Close the database
        super.close();

        // Return the new record id
        return result;
    }

    @Override
    public SpotDTO read(String key, int value) throws Exception {

        // Declare
        SpotDTO dto;

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
    public SpotDTO read(String key, String value) throws Exception {

        // Declare
        SpotDTO dto;

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
    public int update(SpotDTO dto) throws Exception {

        int rows;

        String where = PRIMARY_KEY + "=" + dto.getSpotId();

        // Create a blank CV object
        ContentValues cv = new ContentValues();

        // Add fields from DTO
        if(dto.getSpotTime() != null){
            cv.put(SPOT_TIME, dto.getSpotTime() );

        }
        if(dto.getSpotName() != null){
            cv.put(SPOT_NAME, dto.getSpotName() );

        }
        if(dto.getSpotAddress() != null){
            cv.put(SPOT_ADDRESS, dto.getSpotAddress() );

        }
        if(dto.getSpotCity() != null){
            cv.put(SPOT_CITY, dto.getSpotCity() );

        }
        if(dto.getSpotState() != null){
            cv.put(SPOT_STATE, dto.getSpotState() );

        }
        if(dto.getSpotZip() != null){
            cv.put(SPOT_ZIP, dto.getSpotZip() );

        }
        if(dto.getPrecision() > 0){
            cv.put(SPOT_PRECISION, dto.getPrecision() );

        }
        if(dto.getLatitude() > 0){
            cv.put(SPOT_LATITUDE, dto.getLatitude() );

        }
        if(dto.getLongitude() > 0){
            cv.put(SPOT_LONGITUDE, dto.getLongitude() );

        }

        // Open the database
        super.open();

        // Run the query using the execSQL() method
        //sqLiteDatabase.execSQL(sql, null);
        rows = sqLiteDatabase.update(SpotDAO.TABLE_NAME, cv, where, null);

        // Close the database
        super.close();

        // Return int
        return rows;
    }

    @Override
    public int delete(SpotDTO dto) throws Exception {

        // Declarations
        int rows;
        String whereClause;
        String [] whereArgs = new String[1];

        // Get the primary key
        int key = dto.getSpotId();

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
    public ArrayList<SpotDTO> list() throws Exception {

        // Declare an ArrayList
        ArrayList<SpotDTO> collection = new ArrayList<SpotDTO>();

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
            SpotDTO dto = populateObjectFromCursor(cursor);

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
    public ArrayList<SpotDTO> list(String key, int value) throws Exception {

        // Declare an ArrayList
        ArrayList<SpotDTO> collection = new ArrayList<SpotDTO>();

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
            SpotDTO dto = populateObjectFromCursor(cursor);

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
    public ArrayList<SpotDTO> list(String key, String value) throws Exception {

        // Declare an ArrayList
        ArrayList<SpotDTO> collection = new ArrayList<SpotDTO>();

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
            SpotDTO dto = populateObjectFromCursor(cursor);

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
    public ArrayList<SpotDTO> list(String[] keys, String[] values) throws Exception {

        // Declare an ArrayList
        ArrayList<SpotDTO> collection = new ArrayList<SpotDTO>();

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
            SpotDTO dto = populateObjectFromCursor(cursor);

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
    public ArrayList<SpotDTO> list(String[] keys, int[] values) throws Exception {

        // Declare an ArrayList
        ArrayList<SpotDTO> collection = new ArrayList<SpotDTO>();

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
            SpotDTO dto = populateObjectFromCursor(cursor);

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
    public int count(String key, int value) throws Exception {

        // Declare a return value
        int counted;

        // Write a query
        String sql  = "select * from " + TABLE_NAME + " where "+ key +" = '" + value +"' ";

        // Open the database
        super.open();

        // Run the query
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        // Get the count from the cursor
        counted = cursor.getCount();

        // Close the database
        super.close();

        // Return the collection
        return counted;
    }
}
