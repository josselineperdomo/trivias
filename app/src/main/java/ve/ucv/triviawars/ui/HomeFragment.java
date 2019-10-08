package ve.ucv.triviawars.ui;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import ve.ucv.triviawars.R;
import ve.ucv.triviawars.utilities.BackgroundPattern;
import ve.ucv.triviawars.viewmodels.HomeViewModel;


public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getName();
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 100;
    private Context context;

    private HomeViewModel viewModel;
    private LinearLayout rootLayout;
    private int backgroundPatternId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getContext();
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rootLayout = view.findViewById(R.id.home_main_layout);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        invokeLocationAction();
    }

    private Observer<Location> setBackgroundOnLocationChange() {
        return new Observer<Location>() {
            @Override
            public void onChanged(@Nullable Location location) {
                if (location != null){
                    backgroundPatternId = BackgroundPattern.getRandomPattern().getPatternId();
                    Drawable backgroundPatternDrawable = ResourcesCompat.getDrawable(getResources(),
                            backgroundPatternId, null);
                    rootLayout.setBackground(backgroundPatternDrawable);
                    Log.d(TAG, String.format("Latitude: %s - Longitude: %s",
                            location.getLatitude(),
                            location.getLongitude()));
                }
            }
        };
    }

    private boolean isPermissionsGranted() {
        int accessFineLocation = ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION);

        return accessFineLocation == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        boolean shouldProvideRationale = shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION);

        if (!shouldProvideRationale) {
            Log.i(TAG, "Solicitando permiso");
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i(TAG, "onRequestPermissionResult");

        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                Log.i(TAG, "User interaction fue cancelada.");

            } else {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "Permiso concedido, comenzando actualizaciones");
                    invokeLocationAction();
                }
            }
        }
    }

    private void invokeLocationAction() {
        if (isPermissionsGranted()) {
            viewModel.getLocationLiveData().observe(this, setBackgroundOnLocationChange());
        } else {
            requestPermissions();
        }
    }
}
