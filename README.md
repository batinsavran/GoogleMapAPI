# Business Locator App

The Business Locator App is an Android application that allows users to view, filter, and explore specific businesses on a map. The app uses the Google Maps API to determine the user's location and display nearby businesses.

## Features

- Determine and display the user's current location on the map
- Show businesses within a specific radius
- Filter businesses by cuisine type
- Display business details
- Show/hide businesses at a specific zoom level

## Requirements

- Android Studio
- Google Maps API Key

## Setup

1. **Obtain Google Maps API Key**

   - Create a project on the Google Cloud Console.
   - Enable the Google Maps Android API and Google Places API.
   - Obtain your API key.

2. **Add API Key to Project**

   - Add the following line to your `local.properties` file:
     ```
     MAPS_API_KEY=YOUR_API_KEY_HERE
     ```

3. **Add Required Permissions**

   - Add the necessary permissions to your `AndroidManifest.xml` file:
     ```xml
     <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
     <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
     ```

4. **Add Maps API Key to Manifest File**

   - Add your API key to the `AndroidManifest.xml` file:
     ```xml
     <application>
         ...
         <meta-data
             android:name="com.google.android.geo.API_KEY"
             android:value="${MAPS_API_KEY}" />
     </application>
     ```

## Usage

1. **Launch the App**

   - Open the project in Android Studio and run it on a device or emulator.

2. **Determine User Location**

   - When the app is launched, it determines and displays the user's location on the map.

3. **View and Filter Businesses**

   - View businesses on the map, explore business details, and filter businesses by cuisine type using the filtering options.
