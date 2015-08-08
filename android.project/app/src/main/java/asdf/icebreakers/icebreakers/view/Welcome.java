package asdf.icebreakers.icebreakers.view;

import android.os.Bundle;
import android.util.Log;

import asdf.icebreakers.icebreakers.R;


public class Welcome extends BaseActivity {

    private final String TAG = this.getClass().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Welcome.onCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        getActionBar().setDisplayHomeAsUpEnabled(false);

    }


}
