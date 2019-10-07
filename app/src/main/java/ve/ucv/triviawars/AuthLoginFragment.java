package ve.ucv.triviawars;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.google.android.material.button.MaterialButton;


public class AuthLoginFragment extends Fragment {

    private NavController navController;

    public AuthLoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_auth_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        MaterialButton loginBtn = view.findViewById(R.id.login_action_btn);
        loginBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        startActivity(intent);
                    }
                }
        );

        MaterialButton loginToSignUpBtn = view.findViewById(R.id.login_to_sign_up_btn);
        loginToSignUpBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        EditText username = view.findViewById(R.id.login_username);
//                        EditText password = view.findViewById(R.id.login_password);
//
//                        AuthLoginFragmentDirections.NextAction nextAction = AuthLoginFragmentDirections
//                                .nextAction(username.getText().toString(), password.getText().toString());
//                        navController.navigate(nextAction);
                        navController.navigate(R.id.next_action);
                    }
                }
        );
    }
}
