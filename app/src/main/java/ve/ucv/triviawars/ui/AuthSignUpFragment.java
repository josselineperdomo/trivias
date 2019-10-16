package ve.ucv.triviawars.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ve.ucv.triviawars.R;

public class AuthSignUpFragment extends Fragment {

    public static AuthSignUpFragment newInstance() {
        return new AuthSignUpFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_auth_sign_up, container, false);
    }

}
