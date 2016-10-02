package com.swiftpot.swiftalertmain;

import org.apache.commons.lang.StringUtils;

/**
 * @author Ace Programmer Rbk
 *         <Rodney Kwabena Boachie at [rodney@swiftpot.com,rbk.unlimited@gmail.com]> on
 *         02-Oct-16 @ 9:45 PM
 */
public class TestNullOrDefault {

    public static void main(String[] args) {
        String book = "";
        System.out.println(StringUtils.defaultIfEmpty(book,"Hello"));
    }


}
