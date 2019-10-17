package ve.ucv.triviawars.ui;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ve.ucv.triviawars.R;
import ve.ucv.triviawars.adapters.UserRankingRecyclerViewAdapter;
import ve.ucv.triviawars.databinding.FragmentTriviaDetailBinding;
import ve.ucv.triviawars.db.entity.TriviaEntity;
import ve.ucv.triviawars.viewmodels.TriviaDetailViewModel;
import ve.ucv.triviawars.viewmodels.TriviaDetailViewModelFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class TriviaDetailFragment extends Fragment {

    private TriviaDetailViewModel triviaDetailViewModel;
    private FragmentTriviaDetailBinding binding;
    private UserRankingRecyclerViewAdapter userRankingRecyclerViewAdapter;
    private Context context;

    private FloatingActionButton playFab;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        this.context = context;
        this.userRankingRecyclerViewAdapter = new UserRankingRecyclerViewAdapter(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_trivia_detail, container, false);
        binding.setLifecycleOwner(this.getViewLifecycleOwner());

        View view = binding.getRoot();

        RecyclerView recyclerView = view.findViewById(R.id.user_ranking_list_rv);
        recyclerView.setAdapter(userRankingRecyclerViewAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));

        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        layoutManager.setSmoothScrollbarEnabled(false);
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        playFab = view.findViewById(R.id.trivia_play_btn);

        playFab.setOnClickListener(view1 -> {
            TriviaDetailFragmentDirections.ActionToTriviaQuestions direction = TriviaDetailFragmentDirections
                    .actionToTriviaQuestions(triviaDetailViewModel.getTriviaId(),
                            triviaDetailViewModel.getTriviaTitle(), triviaDetailViewModel.getTriviaImageUrl(), triviaDetailViewModel.getTriviaBackgroundColor());
            Navigation.findNavController(view1).navigate(direction);
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final Bundle arguments = this.getArguments();
        Activity activity = getActivity();

        if (arguments != null && activity != null) {
            initDataBinding(activity.getApplication(),
                    TriviaDetailFragmentArgs.fromBundle(arguments).getTriviaId());

        }
    }

    private void initDataBinding(Application application, int triviaId) {

        triviaDetailViewModel = ViewModelProviders
                .of(this, new TriviaDetailViewModelFactory(application, triviaId))
                .get(TriviaDetailViewModel.class);

        binding.setTriviaViewModel(triviaDetailViewModel);

        triviaDetailViewModel.getObservableTrivia()
                .observe(this, triviaEntity -> triviaDetailViewModel.setTrivia(triviaEntity));

        triviaDetailViewModel.getUserRankingList()
                .observe(this, userEntities -> userRankingRecyclerViewAdapter.setUsersRankingList(userEntities));
    }
}
