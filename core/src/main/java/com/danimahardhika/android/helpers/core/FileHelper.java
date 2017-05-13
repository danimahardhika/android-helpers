package com.danimahardhika.android.helpers.core;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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

public class FileHelper {

    public static final int MB = (1024 * 1014);
    public static final int KB = 1024;

    public static long getDirectorySize(@NonNull File file) {
        if (file.exists()) {
            long result = 0;
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                if (aFileList.isDirectory()) {
                    result += getDirectorySize(aFileList);
                } else {
                    result += aFileList.length();
                }
            }
            return result;
        }
        return 0;
    }

    public static boolean clearDirectory(@NonNull File file) {
        boolean success = false;
        if (file.isDirectory())
            for (File child : file.listFiles())
                clearDirectory(child);
        success = file.delete();
        return success;
    }

    public static boolean copy(@NonNull File file, @NonNull File target) {
        try {
            if (!target.getParentFile().exists()) {
                if (!target.getParentFile().mkdirs()) return false;
            }

            InputStream inputStream = new FileInputStream(file);
            OutputStream outputStream = new FileOutputStream(target);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }

            inputStream.close();
            outputStream.flush();
            outputStream.close();
            return true;
        } catch (IOException ignored) {}
        return false;
    }

    @Nullable
    public static String createZip(@NonNull List<String> files, File file) {
        try {
            final int BUFFER = 2048;
            BufferedInputStream origin;
            FileOutputStream dest = new FileOutputStream(file);
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
                    dest));

            byte data[] = new byte[BUFFER];
            for (int i = 0; i < files.size(); i++) {
                FileInputStream fi = new FileInputStream(files.get(i));
                origin = new BufferedInputStream(fi, BUFFER);

                ZipEntry entry = new ZipEntry(files.get(i).substring(
                        files.get(i).lastIndexOf("/") + 1));
                out.putNextEntry(entry);
                int count;

                while ((count = origin.read(data, 0, BUFFER)) != -1) {
                    out.write(data, 0, count);
                }
                origin.close();
            }

            out.close();
            return file.toString();
        } catch (Exception ignored) {}
        return null;
    }

    @Nullable
    public static Uri getUriFromFile(@NonNull Context context, String applicationId, @NonNull File file) {
        try {
            return FileProvider.getUriForFile(context, applicationId + ".fileProvider", file);
        } catch (IllegalArgumentException ignored) {}
        return null;
    }
}
