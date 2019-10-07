package ve.ucv.triviawars;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class DashboardViewModel extends AndroidViewModel {
    private LocationLiveData locationLiveData;

    public DashboardViewModel(@NonNull Application application) {
        super(application);
        locationLiveData = new LocationLiveData(application.getApplicationContext());
    }

    public LocationLiveData getLocationLiveData() {
        return locationLiveData;
    }

}
