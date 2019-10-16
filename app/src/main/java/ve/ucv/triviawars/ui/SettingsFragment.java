package ve.ucv.triviawars.ui;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ve.ucv.triviawars.R;

public class SettingsFragment extends Fragment {

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        MainActivity mainActivity = (MainActivity)getContext();
        if (mainActivity != null) {
            mainActivity.lockDrawerNavigation();
        }
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

}
