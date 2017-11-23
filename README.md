# Android Helpers
[![](https://jitpack.io/v/danimahardhika/android-helpers.svg)](https://jitpack.io/#danimahardhika/android-helpers) [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0) 

Android helpers collection that used for my projects.

# Gradle Dependency
Add JitPack repository to root ```build.gradle```
```Gradle
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```
Add the dependency
```Gradle
dependencies {
    implementation 'com.github.danimahardhika.android-helpers:core:$versionNumber'
    implementation 'com.github.danimahardhika.android-helpers:animation:$versionNumber'
    implementation 'com.github.danimahardhika.android-helpers:license:$versionNumber'
    implementation 'com.github.danimahardhika.android-helpers:permission:$versionNumber'
}
```
Or use a snapshot
```Gradle
dependencies {
    implementation('com.github.danimahardhika.android-helpers:core:-SNAPSHOT') {
        changing = true
    }
    implementation('com.github.danimahardhika.android-helpers:animation:-SNAPSHOT') {
        changing = true
    }
    implementation('com.github.danimahardhika.android-helpers:license:-SNAPSHOT') {
        changing = true
    }
    implementation('com.github.danimahardhika.android-helpers:permission:-SNAPSHOT') {
        changing = true
    }
}
```

# Core
Every method can be accessed in static way
* BitmapHelper
* ColorHelper
* ContextHelper
* DrawableHelper
* FileHelper
* ListHelper
* SoftKeyboardHelper
* TimeHelper
* UnitHelper
* ViewHelper
* WindowHelper

# Animation
Every method can be accessed in static way
* AnimationHelper
```java
AnimationHelper.fade(view)
    .interpolator(new LinearOutSlowInInterpolator())
    .duration(500)
    .callback(new AnimationHelper.Callback() {
    
        @Override
        public void onAnimationStart() {
            //Do something
        }

        @Override
        public void onAnimationEnd() {
            //Do something
        }
    })
    .start();
```

# License Checker
* LicenseHelper
```java
LicenseHelper helper = new LicenseHelper(context);
helper.run(licenseKey, salt, callback);
```
Don't forget to destroy license helper inside `onDestroy()` method
```java
@Override
protected void onDestroy() {
    helper.destroy();
    super.onDestroy();
}
```

You can see LicenseCallback sample from [here](https://github.com/danimahardhika/wallpaperboard/blob/master/library/src/main/java/com/dm/wallpaper/board/helpers/LicenseCallbackHelper.java).

# Permission
Every method can be accessed in static way
* PermissionHelper

# License
```
Copyright (c) 2017 Dani Mahardhika

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
