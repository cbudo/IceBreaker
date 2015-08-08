package asdf.icebreakers.icebreakers.model.ada;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import asdf.icebreakers.icebreakers.model.dao.SpotDAO;

public class DbAdapter {

    public static String DATABASE_NAME = "icebreaker.db";
    public static final int DATABASE_VERSION = 1; // Sat, August 8, 2:06 AM

    public static DbHelper dbHelper;
    public static Context dbAdapterContext;
    public static SQLiteDatabase sqLiteDatabase;

    boolean isConstructor = false;
    boolean isDb = false;

    public static final String SQL_SPOTS = "CREATE TABLE "
            + SpotDAO.TABLE_NAME
            + " ("

            + SpotDAO.PRIMARY_KEY
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "

            + SpotDAO.SPOT_TIME
            + " TEXT, "

            + SpotDAO.SPOT_NAME
            + " TEXT, "

            + SpotDAO.SPOT_ADDRESS
            + " TEXT, "

            + SpotDAO.SPOT_CITY
            + " TEXT, "

            + SpotDAO.SPOT_STATE
            + " TEXT, "

            + SpotDAO.SPOT_ZIP
            + " TEXT, "

            + SpotDAO.SPOT_PRECISION
            + " REAL, "

            + SpotDAO.SPOT_LATITUDE
            + " REAL, "

            + SpotDAO.SPOT_LONGITUDE
            + " REAL "

            + ");";


    /*
        Constructor
         */
    public DbAdapter(Context ctx){
        if(!isConstructor == true){
            dbAdapterContext = ctx;
            isConstructor = true;
        }
    }

    public DbAdapter open() throws SQLException {
        // See first if isDb has been set to true
        if(!isDb == true){

            // Create a new DbHelper object using the nested SQLiteOpenHelper class
            dbHelper = new DbHelper(dbAdapterContext);

            // Now set isDb to true
            isDb = true;
        }

        // Call the getWriteableDatabase() method on the dbHelper object
        sqLiteDatabase = dbHelper.getWritableDatabase();

        // Return this TheDbAdapter
        return this;
    }

    public void close(){

        if(sqLiteDatabase.isOpen())
            dbHelper.close();
    }

    /*
    Nested SQLiteOpenHelper class
     */
    static class DbHelper extends SQLiteOpenHelper {

        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(SQL_SPOTS);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {

            db.execSQL("DROP TABLE IF EXISTS " + SpotDAO.TABLE_NAME);


            onCreate(db);
        }

    }

}