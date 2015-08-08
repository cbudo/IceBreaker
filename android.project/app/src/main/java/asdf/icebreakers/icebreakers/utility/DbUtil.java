package asdf.icebreakers.icebreakers.utility;

import android.app.Activity;
import android.content.Context;

import java.io.File;

import asdf.icebreakers.icebreakers.model.ada.DbAdapter;
import asdf.icebreakers.icebreakers.model.ada.DbCreate;

/**
 * Created by b on 1/6/15.
 */

    public class DbUtil {

        public boolean exists(Context ctx){

            boolean value = false;

            File database = ctx.getApplicationContext().getDatabasePath(DbAdapter.DATABASE_NAME);

            if (database.exists()) {
                value = true;
            }

            return value;
        }

        public boolean create(Activity act){

            boolean value = false;

            DbCreate adapter = new DbCreate(act);

            //Calling open() causes the database to be created on first run
            adapter.open();


            File database = act.getApplicationContext().getDatabasePath(DbAdapter.DATABASE_NAME);

            if (database.exists()) {
                value = true;
            }

            adapter.close();

            return value;

        }

        public boolean delete(Context ctx) {
            boolean result = false;

            try {

                ctx.deleteDatabase(DbAdapter.DATABASE_NAME);
                result = true;


            } catch (Exception e) {

                e.printStackTrace();
            }

            return result;

        }



    }