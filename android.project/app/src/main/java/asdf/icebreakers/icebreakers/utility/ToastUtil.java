package asdf.icebreakers.icebreakers.utility;

import android.content.Context;
import android.widget.Toast;

import asdf.icebreakers.icebreakers.view.BaseActivity;

public class ToastUtil extends BaseActivity {

    /*
    General method for making toast
    */
    public static void makeToast(Context ctx, String toast){

        final Toast tst;

        tst = Toast.makeText(ctx, toast, Toast.LENGTH_SHORT);

        tst.show();

//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                tst.cancel();
//            }
//        }, 10000);

    }
}