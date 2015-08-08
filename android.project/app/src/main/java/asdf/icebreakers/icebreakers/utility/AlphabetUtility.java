package asdf.icebreakers.icebreakers.utility;

import java.util.Random;

/**
 * Created by b on 3/22/15.
 */
public class AlphabetUtility {

    final static String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    /*
    General method for making a ra
    */
    public static String randomString(int number){

        Random r = new Random();

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < number; i++) {

            sb.append( ALPHABET.charAt( r.nextInt( ALPHABET.length() )));

        }

        return sb.toString();

    }

}
