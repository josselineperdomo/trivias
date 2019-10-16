package ve.ucv.triviawars.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import ve.ucv.triviawars.R;

public class ProfileFragment extends Fragment {

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        MainActivity mainActivity = (MainActivity)getContext();
        if (mainActivity != null) {
            mainActivity.lockDrawerNavigation();
        }
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

}
