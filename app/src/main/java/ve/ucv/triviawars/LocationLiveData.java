package ve.ucv.triviawars;

import android.content.Context;
import android.location.Location;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnSuccessListener;

public class LocationLiveData extends LiveData<Location> {
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 15000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    private final static int MIN_DISPLACEMENT_REQUEST_METRES = 5;
    private final String TAG = this.getClass().getName();

    private SettingsClient settingsClient;
    private LocationSettingsRequest locationSettingsRequest;


    private FusedLocationProviderClient fusedLocationProviderClient ;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    public LocationLiveData(Context context) {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        locationRequest = buildLocationRequest();
        locationCallback = buildLocationCallback();

        settingsClient = LocationServices.getSettingsClient(context);
        locationSettingsRequest = buildLocationSettingsRequest();
    }

    private LocationRequest buildLocationRequest() {
        return LocationRequest.create()
                .setInterval(UPDATE_INTERVAL_IN_MILLISECONDS)
                .setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS)
                .setSmallestDisplacement(MIN_DISPLACEMENT_REQUEST_METRES)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private LocationSettingsRequest buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder locationSettingsBuilder = new LocationSettingsRequest.Builder();
        locationSettingsBuilder.addLocationRequest(locationRequest);
        return locationSettingsBuilder.build();
    }

    private LocationCallback buildLocationCallback() {
        return new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Location location = locationResult.getLastLocation();

                Log.d(TAG, String.format("prev latitude: %s - prev longitude: %s",
                        location.getLatitude(),
                        location.getLongitude()));
                setValue(location);
            }
        };
    }

    @Override
    protected void onActive() {
        super.onActive();
        settingsClient.checkLocationSettings(locationSettingsRequest)
                .addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.i(TAG, "All location settings are satisfied.");
                        //noinspection MissingPermission
                        fusedLocationProviderClient.requestLocationUpdates(locationRequest,
                                locationCallback, Looper.myLooper());
                    }
                });
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }
}
