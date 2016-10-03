package com.swiftpot.swiftalertmain;

import com.swiftpot.swiftalertmain.helpers.CustomDateFormat;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

/**
 * @author Ace Programmer Rbk
 *         <Rodney Kwabena Boachie at [rodney@swiftpot.com,rbk.unlimited@gmail.com]> on
 *         02-Oct-16 @ 9:45 PM
 */
public class TestNullOrDefault {

    public static void main(String[] args) {
//        String book = "";
//        System.out.println(StringUtils.defaultIfEmpty(book,"Hello"));
//        String ee = "03677";
//        String ee2 = "03677";
//        boolean statusMe = isStringSame(ee,ee2);
//        System.out.println(String.valueOf(statusMe));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(CustomDateFormat.getDateFormat());
        String dateNow = simpleDateFormat.format(Date.from(Instant.now()));
        System.out.println(dateNow);
    }
    static boolean isStringSame(String s1,String s2){
        boolean statusMe =  false;

        if(s1 .equalsIgnoreCase( s2)){
            statusMe = true;
        }
        return statusMe;
    }

}
