package com.danimahardhika.android.helpers.core.utils;

import android.support.annotation.NonNull;
import android.util.Log;

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

public class LogUtil {

    private static boolean mIsLoggingEnabled = false;
    private static String mLoggingTag = "AndroidHelpers";

    public static void setLoggingEnabled(boolean enabled) {
        LogUtil.mIsLoggingEnabled = enabled;
    }

    public static void setLoggingTag(@NonNull String tag) {
        LogUtil.mLoggingTag = tag;
    }

    public static void d(String message) {
        if (LogUtil.mIsLoggingEnabled)
            Log.d(LogUtil.mLoggingTag, ""+ message);
    }

    public static void e(String message) {
        if (LogUtil.mIsLoggingEnabled)
            Log.e(LogUtil.mLoggingTag, ""+ message);
    }
}
