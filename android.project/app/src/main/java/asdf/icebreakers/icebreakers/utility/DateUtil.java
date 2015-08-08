package asdf.icebreakers.icebreakers.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by b on 1/15/15.
 */
public class DateUtil {

    public static String timestamp(){

        Date date = new Date();

        String timeStamp = new SimpleDateFormat("HH:mm").format( date );

        return timeStamp;

    }

}
