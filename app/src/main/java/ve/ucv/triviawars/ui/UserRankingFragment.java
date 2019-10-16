package ve.ucv.triviawars.ui;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ve.ucv.triviawars.R;
import ve.ucv.triviawars.adapters.UserRankingRecyclerViewAdapter;
import ve.ucv.triviawars.viewmodels.UserRankingViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserRankingFragment extends Fragment {

    private UserRankingViewModel userRankingViewModel;
    private UserRankingRecyclerViewAdapter userRankingRecyclerViewAdapter;
    private Context context;

    public static UserRankingFragment newInstance() {
        return new UserRankingFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        this.context = context;
        this.userRankingRecyclerViewAdapter = new UserRankingRecyclerViewAdapter(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_user_ranking_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.user_ranking_list_rv);
        recyclerView.setAdapter(userRankingRecyclerViewAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        return view;
    }

    private void initData() {
        userRankingViewModel = ViewModelProviders.of(this).get(UserRankingViewModel.class);
        userRankingViewModel.getUsersList().observe(this, userEntities -> userRankingRecyclerViewAdapter.setUsersRankingList(userEntities));
    }

}
