package asdf.icebreakers.icebreakers.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import asdf.icebreakers.icebreakers.R;

public class BaseActivity extends Activity {

    final String TAG = this.getClass().getName();

    //Define static fields for the int value of the subclass Activities
    protected static final int MENU_OPTION_1 = 1;
    protected static final int MENU_OPTION_2 = 2;
    protected static final int MENU_OPTION_3 = 3;
    protected static final int MENU_OPTION_4 = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "BaseActivity.onCreate()");

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_base);

        getActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void onStart() {
        Log.d(TAG, "BaseActivity.onStart()");

        super.onStart();

    }

    @Override
    protected void onStop() {
        Log.d(TAG, "BaseActivity.onStop()");

        super.onStop();
    }

    @Override
    protected void onResume(){
        Log.d(TAG, "BaseActivity.onResume()");

        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "BaseActivity.onPause()");

        super.onPause();
    }
    /*
    Menu items inherited by subclasses
    */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // (groupId, itemId, order, title)
//        menu.add(0, MENU_OPTION_1, Menu.NONE, "one");
//        menu.add(0, MENU_OPTION_2, Menu.NONE, "two" );
//        menu.add(0, MENU_OPTION_3, Menu.NONE, "three" );
//        menu.add(0, MENU_OPTION_4, Menu.NONE, "four" );
//
//        return true;

        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.base_activity_action_bar, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        // Handle presses on the action bar items
        switch (item.getItemId()) {

            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;

            case R.id.action_bar_compass: //defined in base_activity_action_bar.xml
                openMyLocation();
                return true;

            case R.id.action_bar_people:
                openMyPeople();
                return true;

            case R.id.action_bar_test:
                openTest();
                return true;
//
//            case R.id.:
//                openMyLocation();
//                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void openMyLocation() {
        // declare the intent
        Intent intent = new Intent(BaseActivity.this, IceLocation.class);
        // act on the intent
        startActivity(intent);
    }

    private void openMyPeople() {
        // declare the intent
        Intent intent = new Intent(BaseActivity.this, IcePeople.class);
        // act on the intent
        startActivity(intent);
    }

    private void openTest() {
        // declare the intent
        Intent intent = new Intent(BaseActivity.this, TestWebService.class);
        // act on the intent
        startActivity(intent);
    }


}