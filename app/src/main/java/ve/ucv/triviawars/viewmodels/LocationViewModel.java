package ve.ucv.triviawars.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import ve.ucv.triviawars.livedata.LocationLiveData;

public class LocationViewModel extends AndroidViewModel {
    private LocationLiveData locationLiveData;

    public LocationViewModel(@NonNull Application application) {
        super(application);
        locationLiveData = new LocationLiveData(application.getApplicationContext());
    }

    public LocationLiveData getLocationLiveData() {
        return locationLiveData;
    }

}
