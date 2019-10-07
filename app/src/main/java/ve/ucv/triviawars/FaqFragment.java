package ve.ucv.triviawars;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import ve.ucv.triviawars.ui.MainActivity;


public class FaqFragment extends Fragment {


    public FaqFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MainActivity mainActivity = (MainActivity)getContext();
        if (mainActivity != null) {
            mainActivity.lockDrawerNavigation();
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_faq, container, false);
    }

}
