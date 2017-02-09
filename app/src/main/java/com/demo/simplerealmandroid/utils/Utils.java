package com.demo.simplerealmandroid.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by emb-sadabir on 7/2/17.
 */
public class Utils {

    private static final String DATE_PATTERN = "dd/MM/yyyy";

    public static String convertDateToString(Date date) {
        return new SimpleDateFormat(DATE_PATTERN, Locale.getDefault()).format(date);
    }
}
