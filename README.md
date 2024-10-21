# Business Locator App

## Overview
The **Business Locator App** is an Android application designed to help users discover and explore businesses on a map using the Google Maps API. This app features dynamic location tracking, business filtering, and detailed business information displays. 

### Features:
- **User Location:** Automatically determines and displays the user’s current location on the map.
- **Business Search:** Displays nearby businesses within a certain radius.
- **Filters:** Allows filtering businesses by cuisine type or category.
- **Details View:** Shows business details like name, type, and location.
- **Dynamic Zoom:** Adjusts visible businesses based on the zoom level.

## Prerequisites
- **Android Studio:** Required to run the project.
- **Google Maps API Key:** Obtainable from Google Cloud Console.

## Setup Guide

1. **Google Maps API Key:**
    - Navigate to [Google Cloud Console](https://console.cloud.google.com/).
    - Create a project and enable the **Google Maps Android API** and **Google Places API**.
    - Obtain your API key.

2. **Configure API Key:**
    - Open the `local.properties` file and add your API key:
      ```properties
      MAPS_API_KEY=YOUR_API_KEY_HERE
      ```

3. **Permissions:**
    - Add location permissions in the `AndroidManifest.xml`:
      ```xml
      <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
      <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
      ```

4. **API Key in Manifest:**
    - Add the key to the `AndroidManifest.xml`:
      ```xml
      <application>
          ...
          <meta-data
              android:name="com.google.android.geo.API_KEY"
              android:value="${MAPS_API_KEY}" />
      </application>
      ```

## Usage Instructions
1. **Launch the App:**
    - Open the project in Android Studio and run it on a device or emulator.
2. **Explore Businesses:**
    - The map will show your location along with nearby businesses.
    - Use the filter feature to find businesses by cuisine.

## Contributions
Feel free to contribute by creating issues or submitting pull requests. 
