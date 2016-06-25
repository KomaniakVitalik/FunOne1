package com.www.funone.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by vitaliy.herasymchuk on 6/10/16.
 */
public class DateUtil {

    public static final String TAG = "DateUtil";

    public static final String DATE_PATTERN = "MM/dd/yyyy";
    public static final String TIME_PATTERN = "hh:mm a";

    private static DateUtil instance = null;

    private SimpleDateFormat mSDFormat;
    private SimpleDateFormat mSTFormat;

    private Date mDate;

    private DateUtil() {
        mSDFormat = new SimpleDateFormat(DATE_PATTERN, Locale.US);
        mSTFormat = new SimpleDateFormat(TIME_PATTERN, Locale.US);
        mDate = new Date();
    }

    public static DateUtil getInstance() {
        if (!Validator.isObjectValid(instance)) {
            instance = new DateUtil();
        }
        return instance;
    }

    public String getCurrentDate() {
        return mSDFormat.format(mDate);
    }

    public String getCurrentTime() {
        return mSTFormat.format(new Date());
    }


}
