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
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import ve.ucv.triviawars.R;
import ve.ucv.triviawars.utilities.BackgroundPattern;
import ve.ucv.triviawars.viewmodels.DashboardViewModel;

public class DashboardFragment extends Fragment {

    private static final String TAG = DashboardFragment.class.getName();

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 100;

    private Context context;
    private FragmentActivity fragmentActivity;

    private DashboardViewModel viewModel;
    private FrameLayout frameLayout;
    private int backgroundPatternId;

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        this.context = getContext();
        MainActivity mainActivity = (MainActivity)getContext();
        if (mainActivity != null) {
            mainActivity.unlockDrawerNavigation();
        }

        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        frameLayout = view.findViewById(R.id.dashboard_main_layout);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);

        fragmentActivity = getActivity();
        invokeLocationAction();
    }

    private Observer<Location> setBackgroundOnLocationChange() {
        return new Observer<Location>() {
            @Override
            public void onChanged(@Nullable Location location) {
                backgroundPatternId = BackgroundPattern.getRandomPattern().getPatternId();
                Drawable backgroundPatternDrawable = ResourcesCompat.getDrawable(getResources(),
                        backgroundPatternId, null);
                frameLayout.setBackground(backgroundPatternDrawable);
            }
        };
    }

    private boolean isPermissionsGranted() {
        int accessFineLocation = ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION);

        return accessFineLocation == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(fragmentActivity,
                        Manifest.permission.ACCESS_FINE_LOCATION);
        if (!shouldProvideRationale) {
            Log.i(TAG, "Requesting permission");
            ActivityCompat.requestPermissions(fragmentActivity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "Permission granted, updates requested, starting location updates");
                invokeLocationAction();
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
