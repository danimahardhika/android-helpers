package com.danimahardhika.android.helpers.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import java.io.ByteArrayOutputStream;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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

public class BitmapHelper {

    @Nullable
    public static Bitmap getTintedBitmap(@NonNull Context context, @DrawableRes int resId, @ColorInt int color) {
        Drawable drawable = DrawableHelper.get(context, resId);
        return getTintedBitmap(drawable, color);
    }

    @Nullable
    public static Bitmap getTintedBitmap(@NonNull Drawable drawable, @ColorInt int color) {
        Drawable tintedDrawable = DrawableHelper.getTintedDrawable(drawable, color);
        if (tintedDrawable != null) return toBitmap(drawable);
        return null;
    }

    @Nullable
    public static Bitmap getTintedBitmap(@NonNull Bitmap bitmap, @ColorInt int color) {
        try {
            Paint paint = new Paint();
            paint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
            Bitmap tintedBitmap = Bitmap.createBitmap(
                    bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(tintedBitmap);
            canvas.drawBitmap(bitmap, 0, 0, paint);
            return tintedBitmap;
        } catch (OutOfMemoryError e) {
            return null;
        }
    }

    @Nullable
    public static byte[] getByteArray(@NonNull Bitmap bitmap) {
        return getByteArray(bitmap, 50);
    }

    @Nullable
    public static byte[] getByteArray(@NonNull Bitmap bitmap, @IntRange(from = 0, to = 100) int quality) {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, quality, stream);
            return stream.toByteArray();
        } catch (OutOfMemoryError e) {
            return null;
        }
    }

    @Nullable
    public static Bitmap toBitmap(@NonNull Drawable drawable) {
        try {
            return ((BitmapDrawable) drawable).getBitmap();
        } catch (OutOfMemoryError e) {
            return null;
        }
    }

    @Nullable
    public static Bitmap toBitmap(@NonNull Context context, @DrawableRes int resId) {
        Drawable drawable = DrawableHelper.get(context, resId);
        return toBitmap(drawable);
    }
}
