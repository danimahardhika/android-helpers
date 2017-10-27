package com.danimahardhika.android.helpers.core;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

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

public class WindowHelper {

    public enum NavigationBarTranslucent {
        PORTRAIT_LANDSCAPE,
        PORTRAIT_ONLY,
        LANDSCAPE_ONLY
    }

    public static void resetNavigationBarTranslucent(@NonNull Context context, @NonNull NavigationBarTranslucent navigationBarTranslucent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            boolean tabletMode = context.getResources().getBoolean(R.bool.android_helpers_tablet_mode);
            int orientation = context.getResources().getConfiguration().orientation;

            switch (navigationBarTranslucent) {
                case PORTRAIT_ONLY:
                    if (tabletMode || orientation == Configuration.ORIENTATION_PORTRAIT) {
                        ((AppCompatActivity) context).getWindow().addFlags(
                                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                    } else {
                        ((AppCompatActivity) context).getWindow().clearFlags(
                                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                        ColorHelper.setNavigationBarColor(context, Color.BLACK);
                    }
                    break;
                case LANDSCAPE_ONLY:
                    if (tabletMode || orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        ((AppCompatActivity) context).getWindow().addFlags(
                                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                    } else {
                        ((AppCompatActivity) context).getWindow().clearFlags(
                                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                        ColorHelper.setNavigationBarColor(context, Color.BLACK);
                    }
                    break;
                case PORTRAIT_LANDSCAPE:
                    ((AppCompatActivity) context).getWindow().addFlags(
                            WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                    break;
                default:
                    ((AppCompatActivity) context).getWindow().addFlags(
                            WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                    break;

            }
        }
    }

    public static void disableTranslucentNavigationBar(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ((AppCompatActivity) context).getWindow().clearFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    public static void setTranslucentStatusBar(Context context, boolean translucent) {
        if (context == null) {
            Log.e("WindowHelper", "context is null");
            return;
        }

        if (!(context instanceof Activity)) {
            Log.e("WindowHelper", "context must be instance of activity");
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = ((Activity) context).getWindow();
            if (translucent) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                return;
            }

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    public static int getStatusBarHeight(@NonNull Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return context.getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    public static int getNavigationBarHeight(@NonNull Context context) {
        Point appUsableSize = getAppUsableScreenSize(context);
        Point realScreenSize = getScreenSize(context);

        if (appUsableSize.x < realScreenSize.x) {
            Point point = new Point(realScreenSize.x - appUsableSize.x, appUsableSize.y);
            return point.x;
        }

        if (appUsableSize.y < realScreenSize.y) {
            Point point = new Point(appUsableSize.x, realScreenSize.y - appUsableSize.y);
            return point.y;
        }
        return 0;
    }

    private static Point getAppUsableScreenSize(@NonNull Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    public static Point getScreenSize(@NonNull Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            display.getRealSize(size);
        } else {
            try {
                size.x = (Integer) Display.class.getMethod("getRawWidth").invoke(display);
                size.y = (Integer) Display.class.getMethod("getRawHeight").invoke(display);
            } catch (Exception e) {
                size.x = display.getWidth();
                size.y = display.getHeight();
            }
        }
        return size;
    }

    public static void setupApplicationWindowColor(@NonNull Context context, String appName, @Nullable Drawable drawable, @ColorInt int color) {
        Bitmap bitmap = null;
        if (drawable != null) bitmap = BitmapHelper.toBitmap(drawable);
        setupApplicationWindowColor(context, appName, bitmap, color);
    }

    public static void setupApplicationWindowColor(@NonNull Context context, String appName, @DrawableRes int resId, @ColorInt int color) {
        Drawable drawable = DrawableHelper.get(context, resId);
        Bitmap bitmap = BitmapHelper.toBitmap(drawable);
        setupApplicationWindowColor(context, appName, bitmap, color);
    }

    public static void setupApplicationWindowColor(@NonNull Context context, String appName, @Nullable Bitmap bitmap, @ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ((AppCompatActivity) context).setTaskDescription(new ActivityManager.TaskDescription (
                    appName,
                    bitmap,
                    color));
        }
    }
}
