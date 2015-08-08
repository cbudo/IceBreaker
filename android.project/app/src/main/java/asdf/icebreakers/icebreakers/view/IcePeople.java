package asdf.icebreakers.icebreakers.view;

import android.os.Bundle;
import android.util.Log;

import asdf.icebreakers.icebreakers.R;

public class IcePeople extends BaseActivity {

    private final String TAG = this.getClass().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "People.onCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);

//        getActionBar().setDisplayHomeAsUpEnabled(true);

    }

}
