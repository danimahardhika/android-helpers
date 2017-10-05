package com.danimahardhika.android.helpers.core;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/*
 * Android Helpers
 *
 * Copyright (c) 2017 Dani Mahardhika
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class TimeHelper {

    public static final String FORMAT_MINUTE_SECOND = "%02d:%02d";

    public static int minuteToMilli(int minute) {
        return minute * 60 * 1000;
    }

    public static int milliToMinute(int milli) {
        return milli / 60 / 1000;
    }

    public static String getShortDateTime() {
        SimpleDateFormat dateFormat = getDefaultShortDateTimeFormat();
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getLongDateTime() {
        SimpleDateFormat dateFormat = getDefaultLongDateTimeFormat();
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getDateTime(@NonNull SimpleDateFormat simpleDateFormat) {
        Date date = new Date();
        return simpleDateFormat.format(date);
    }

    public static String getDisplayDateTime(String datetime, @NonNull SimpleDateFormat simpleDateFormat) {
        return getDisplayDate(datetime, simpleDateFormat) +", "+ getDisplayTime(datetime, simpleDateFormat);
    }

    private static String getDisplayDate(String datetime, @NonNull SimpleDateFormat simpleDateFormat) {
        Date date = parse(simpleDateFormat, datetime);
        SimpleDateFormat formatter =  new SimpleDateFormat(
                getDefaultDisplayDateFormat(), Locale.getDefault());
        return formatter.format(date);
    }

    private static String getDisplayTime(String datetime, @NonNull SimpleDateFormat simpleDateFormat) {
        Date date = parse(simpleDateFormat, datetime);
        SimpleDateFormat formatter =  new SimpleDateFormat(
                getDefaultDisplayTimeFormat(), Locale.getDefault());
        return formatter.format(date).replace("am", "AM").replace("pm", "PM");
    }

    @Nullable
    private static Date parse(SimpleDateFormat simpleDateFormat, String datetime) {
        try {
            return simpleDateFormat.parse(datetime);
        } catch(Exception e){
            Log.e("TimeHelper", Log.getStackTraceString(e));
            return null;
        }
    }

    private static String getDefaultDisplayDateFormat() {
        return "MMM dd yyyy";
    }

    private static String getDefaultDisplayTimeFormat() {
        return "hh:mm a";
    }

    @NonNull
    private static SimpleDateFormat getDefaultLongDateTimeFormat() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS", Locale.getDefault());
    }

    @NonNull
    private static SimpleDateFormat getDefaultShortDateTimeFormat() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    }

    public static String getFormattedTime(String format, int millis) {
        return String.format(Locale.getDefault(), format,
                TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
    }
}
