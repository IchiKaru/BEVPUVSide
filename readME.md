# Project BEV Documentation for PUV Client
Other Documentations will be released in the next update. For periodical update cycle cadence
(every Monday at 12:00nn GMT+8)

## What's inside the Documentation

1. PUV Client Documentation

## Documentation Proper

### PUV Client Documentation

#### PUV Client App `build.gradle.kts` App directory

1. **Plugins**:
   ```groovy
   plugins {
       id("com.android.application")
       id("org.jetbrains.kotlin.android")
       id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
   }
   ```
   - This section defines the plugins that are applied to the Gradle project.
   - `com.android.application` is the Android application plugin, necessary for Android app development.
   - `org.jetbrains.kotlin.android` is the Kotlin Android plugin for Kotlin language support.
   - `com.google.android.libraries.mapsplatform.secrets-gradle-plugin` is a plugin for managing secrets, possibly including API keys or sensitive data.

2. **Android Configuration**:
   ```groovy
   android {
       namespace = "com.example.mymap"
       compileSdk = 34
   ```
   - This section configures various aspects of the Android project.
   - `namespace` specifies the package namespace for the app, such as `com.example.mymap`.
   - `compileSdk` sets the Android SDK version to compile against (API level 34).

3. **Default Configuration**:
   ```groovy
       defaultConfig {
           applicationId = "com.example.mymap"
           minSdk = 28
           targetSdk = 34
           versionCode = 1
           versionName = "1.0"
           testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
       }
   ```
   - This subsection defines default configurations for the app.
   - `applicationId` specifies the package name of the app.
   - `minSdk` defines the minimum Android API level required (API 28).
   - `targetSdk` sets the target Android API level (API 34).
   - `versionCode` and `versionName` are for version tracking and display.
   - `testInstrumentationRunner` specifies the test runner for running Android unit tests.

4. **Build Types**:
   ```groovy
       buildTypes {
           release {
               isMinifyEnabled = false
               proguardFiles(
                   getDefaultProguardFile("proguard-android-optimize.txt"),
                   "proguard-rules.pro"
               )
           }
       }
   ```
   - In the `buildTypes` section, the configuration for different build types is defined.
   - The `release` build type specifies that minification (code shrinking and obfuscation) is disabled (`isMinifyEnabled = false`).
   - It also references ProGuard configuration files for further optimization and obfuscation.

5. **Compile and Kotlin Options**:
   ```groovy
       compileOptions {
           sourceCompatibility = JavaVersion.VERSION_1_8
           targetCompatibility = JavaVersion.VERSION_1_8
       }
       kotlinOptions {
           jvmTarget = "1.8"
       }
   ```
   - These sections set Java and Kotlin compatibility options.
   - The code is configured to be compatible with Java 8 (`sourceCompatibility` and `targetCompatibility).
   - The Kotlin code is targeted for JVM 1.8.

6. **Build Features**:
   ```groovy
       buildFeatures {
           compose = true
       }
   ```
   - In this section, it's indicated that Jetpack Compose is enabled (`compose = true`). Jetpack Compose is a modern Android UI toolkit.

7. **Compose Options**:
   ```groovy
       composeOptions {
           kotlinCompilerExtensionVersion = "1.5.2"
       }
   ```
   - For Jetpack Compose, the Kotlin compiler extension version is specified as "1.5.2."

8. **Dependencies**:
   ```groovy
   dependencies {
       implementation("com.google.android.gms:play-services-maps:17.0.1")
       implementation("androidx.compose.material3:material3:1.1.1")
       // ... (other dependencies)
       implementation("com.google.android.gms:play-services-maps:18.1.0")
   }
   ```
   - The `dependencies` section lists the external libraries and dependencies used by the Android project.
   - For example, it includes Google Play Services Maps, Jetpack Compose Material3, AndroidX libraries, and other dependencies.

#### Main Activity Java File Documentation
**Java/Kotlin Code:**
```java
package com.example.puvclient;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.example.puvclient.databinding.ActivityMainBinding;
import okhttp3.*;
import java.io.IOException;

import android.widget.ToggleButton;
```

1. Import Statements:
   - These are import statements that include various classes and libraries required for the Android app. For instance, it includes classes for handling location, permissions, and networking with OkHttp.

2. `const` (Node.js JavaScript):
   ```javascript
   const { MongoClient, ServerApiVersion } = require('mongodb');
   const uri = "mongodb+srv://ClientSide:SDpJGwDBlEJLTtKZ@rt-location.ahaubf7.mongodb.net/?retryWrites=true&w=majority";
   ```
   - These lines are the JavaScript code for a Node.js application using the MongoDB Node.js driver. It imports `MongoClient` and `ServerApiVersion` and sets a MongoDB URI for connecting to a MongoDB database.

**Android Activity Class:**
```java
public class MapsActivity extends AppCompatActivity {
    // ...
}
```

3. Class Declaration:
   - This is the beginning of an Android activity class named `MapsActivity`. It extends `AppCompatActivity`.

**Member Variables:**
```java
    private LocationManager locationManager;
    private boolean isTracking = false;
    private final int locationPermissionCode = 123;
```

4. Member Variables:
   - These are class-level variables:
     - `locationManager`: An instance of `LocationManager` for managing location-related operations.
     - `isTracking`: A boolean variable to track whether the app is actively tracking location.
     - `locationPermissionCode`: An integer code used for requesting location-related permissions.

**`onCreate` Method:**
```java
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // ...
    }
```

5. `onCreate` Method:
   - This is the `onCreate` method, which is called when the activity is created.
   - It inflates a layout using `ActivityMainBinding` and sets it as the content view for the activity.

**ToggleButton Initialization:**
```java
        final ToggleButton toggleButton = binding.toggleButton;
```

6. ToggleButton Initialization:
   - It initializes a `ToggleButton` from the layout using data binding.

**ToggleButton Callback (Lambda Expression):**
```java
        toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isTracking = isChecked;
            if (isTracking) {
                startLocationUpdates();
            } else {
                stopLocationUpdates();
            }
        });
    }
```

7. ToggleButton Callback (Lambda Expression):
   - This code sets an `OnCheckedChangeListener` for the `ToggleButton` using a lambda expression.
   - It toggles the `isTracking` variable based on the button state and starts or stops location updates accordingly.

**LocationListener Implementation:**
```java
    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (isTracking) {
                sendLocationToServer(location.getLatitude(), location.getLongitude());
            }
        }
        // ...
    };
```

8. LocationListener Implementation:
   - This code defines a `LocationListener` implementation for handling location updates.
   - When a location change occurs, it checks if tracking is enabled and sends the location to the server.

**Location Update Methods:**
```java
    private void startLocationUpdates() {
        // ...
    }

    private void stopLocationUpdates() {
        // ...
    }
```

9. Location Update Methods:
   - These methods, `startLocationUpdates` and `stopLocationUpdates`, are used to start and stop location updates.

**Permission Check and Request Methods:**
```java
    private boolean checkPermission() {
        // ...
    }

    private void requestPermission() {
        // ...
    }
```

10. Permission Check and Request Methods:
    - These methods, `checkPermission` and `requestPermission`, are used to check and request location-related permissions.

**Permission Request Callback (`onRequestPermissionsResult`):**
```java
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // ...
    }
```

11. Permission Request Callback (`onRequestPermissionsResult`):
    - This method is called when the user responds to a permission request. It processes the result of the permission request.

**Sending Location Data to Server:**
```java
    private void sendLocationToServer(double latitude, double longitude) {
        // ...
    }
}
```

12. Sending Location Data to Server:
    - This method, `sendLocationToServer`, is responsible for sending the latitude and longitude to a server using OkHttp.
#### `AndroidManifest.xml` layout file
1. **XML Declaration and Manifest Root Element**:
   ```xml
   <?xml version="1.0" encoding="utf-8"?>
   <manifest xmlns:android="http://schemas.android.com/apk/res/android"
       xmlns:tools="http://schemas.android.com/tools">
   ```
   - The XML declaration specifies the version and encoding.
   - The `<manifest>` element is the root of the AndroidManifest.xml file and defines the Android app's manifest.

2. **Permissions**:
   ```xml
   <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
   <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
   ```
   - These `<uses-permission>` elements declare permissions required by the app.
   - `ACCESS_FINE_LOCATION` and `ACCESS_COARSE_LOCATION` permissions are necessary for accessing precise and coarse-grained location information.

3. **Application Element**:
   ```xml
   <application
       android:allowBackup="true"
       android:dataExtractionRules="@xml/data_extraction_rules"
       android:fullBackupContent="@xml/backup_rules"
       android:icon="@mipmap/ic_launcher"
       android:label="@string/app_name"
       android:roundIcon="@mipmap/ic_launcher_round"
       android:supportsRtl="true"
       android:theme="@style/Theme.MyMap"
       tools:targetApi="31">
   ```
   - The `<application>` element contains configuration and metadata for the Android app.
   - `android:allowBackup="true"` indicates that the app supports backup and restore functionality.
   - `android:dataExtractionRules="@xml/data_extraction_rules"` references data extraction rules, likely used for specifying how data is extracted or handled within the app.
   - `android:fullBackupContent="@xml/backup_rules"` points to a content descriptor file for full backups.
   - `android:icon="@mipmap/ic_launcher"` and `android:roundIcon="@mipmap/ic_launcher_round"` set the icons for the app.
   - `android:label="@string/app_name"` specifies the display name of the app, which is defined as a string resource.
   - `android:supportsRtl="true"` indicates that the app supports right-to-left (RTL) layout direction, essential for languages that read from right to left.
   - `android:theme="@style/Theme.MyMap"` sets the theme/style for the app.

4. **Activity Element**:
   ```xml
       <activity
           android:name=".MapsActivity"
           android:exported="true">
   ```
   - The `<activity>` element defines an activity component named "MapsActivity" for the app.
   - `android:name=".MapsActivity"` specifies the class name for the activity, using the relative package name.
   - `android:exported="true"` allows other applications to launch this activity.

5. **Intent Filter for Main Activity**:
   ```xml
           <intent-filter>
               <action android:name="android.intent.action.MAIN" />
               <category android:name="android.intent.category.LAUNCHER" />
           </intent-filter>
       </activity>
   ```
   - Inside the `<activity>` element, an `<intent-filter>` is defined to specify how the activity responds to incoming intents (requests).
   - `<action>` with `android:name="android.intent.action.MAIN"` designates this activity as the main entry point of the app.
   - `<category>` with `android:name="android.intent.category.LAUNCHER"` indicates that the activity should appear in the device's launcher as an app icon, making it the entry point for the app.

6. **Meta-Data for API Key**:
   ```xml
       <meta-data
           android:name="com.google.android.geo.API_KEY"
           android:value="@string/my_map_api_key" />
   ```
   - The `<meta-data>` element is used to include metadata in the manifest.
   - `android:name="com.google.android.geo.API_KEY"` specifies the name of the metadata.
   - `android:value="@string/my_map_api_key"` references a string resource named "my_map_api_key," which contains the API key for Google Maps.
