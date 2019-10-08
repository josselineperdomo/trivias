package ve.ucv.triviawars.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import ve.ucv.triviawars.data.LocationLiveData;

public class HomeViewModel extends AndroidViewModel {
    private LocationLiveData locationLiveData;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        locationLiveData = new LocationLiveData(application.getApplicationContext());
    }

    public LocationLiveData getLocationLiveData() {
        return locationLiveData;
    }

}
