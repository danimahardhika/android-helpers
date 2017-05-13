package com.danimahardhika.android.helpers.license;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.vending.licensing.AESObfuscator;
import com.google.android.vending.licensing.LicenseChecker;
import com.google.android.vending.licensing.LicenseCheckerCallback;
import com.google.android.vending.licensing.Policy;
import com.google.android.vending.licensing.ServerManagedPolicy;

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

public class LicenseHelper implements LicenseCheckerCallback {

    private Context context;
    private LicenseCallback callback;
    private LicenseChecker licenseChecker;

    public LicenseHelper(@NonNull Context context) {
        this.context = context;
    }

    public void run(String licenseKey, byte[] salt, @NonNull LicenseCallback callback) {
        if (isReadyToCheckLicense(salt)) {
            this.callback = callback;

            @SuppressLint("HardwareIds")
            String deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

            licenseChecker = new LicenseChecker(context,
                    new ServerManagedPolicy(context,
                            new AESObfuscator(salt, context.getPackageName(), deviceId)),
                    licenseKey);
            licenseChecker.checkAccess(this);

            this.callback.onLicenseCheckStart();
        } else {
            Log.e("LicenseHelper", "Unable to check license, wrong salt");
        }
    }

    public void destroy() {
        if (licenseChecker != null) {
            licenseChecker.onDestroy();
            licenseChecker = null;
        }
    }

    private boolean isReadyToCheckLicense(byte[] salt) {
        if (salt != null) {
            if (salt.length == 20) return true;
        }
        return false;
    }

    @Override
    public void allow(int reason) {
        callback.onLicenseCheckFinished(Status.SUCCESS);
    }

    @Override
    public void dontAllow(int reason) {
        Status status = Status.RETRY;
        if (reason == Policy.NOT_LICENSED) {
            status = Status.FAILED;
        }

        callback.onLicenseCheckFinished(status);
    }

    @Override
    public void applicationError(int errorCode) {
        Status status = Status.RETRY;
        if (errorCode == ERROR_NOT_MARKET_MANAGED) {
            status = Status.FAILED;
        }

        callback.onLicenseCheckFinished(status);
    }

    public enum Status {
        SUCCESS,
        FAILED,
        RETRY
    }
}
