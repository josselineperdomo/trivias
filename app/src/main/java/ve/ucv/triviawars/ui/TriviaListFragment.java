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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ve.ucv.triviawars.R;
import ve.ucv.triviawars.adapters.TriviaRecyclerViewAdapter;
import ve.ucv.triviawars.viewmodels.TriviaListViewModel;

import static ve.ucv.triviawars.utilities.Constants.DEFAULT_GRID_COLUMNS;


/**
 * A simple {@link Fragment} subclass.
 */
public class TriviaListFragment extends Fragment {

    private TriviaListViewModel triviaListViewModel;
    private TriviaRecyclerViewAdapter triviaRecyclerViewAdapter;
    private Context context;
    private Integer gridColumnSize;

    public TriviaListFragment() {}

    public TriviaListFragment(int gridColumnSize) {
        this.gridColumnSize = gridColumnSize;
    }

    public static TriviaListFragment newInstance(int gridColumnSize) {
        return new TriviaListFragment(gridColumnSize);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        this.context = context;
        triviaRecyclerViewAdapter = new TriviaRecyclerViewAdapter(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_trivia_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.trivia_list_rv);
        recyclerView.setAdapter(triviaRecyclerViewAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(context, gridColumnSize));

        return view;
    }

    private void initData() {
        triviaListViewModel = ViewModelProviders.of(this).get(TriviaListViewModel.class);
        triviaListViewModel.getTriviasList().observe(this, trivia -> triviaRecyclerViewAdapter.setTriviaList(trivia));

        if(gridColumnSize == null) {
            gridColumnSize = DEFAULT_GRID_COLUMNS;
        }
    }

}
