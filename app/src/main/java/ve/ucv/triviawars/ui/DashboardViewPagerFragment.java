package ve.ucv.triviawars.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import ve.ucv.triviawars.R;
import ve.ucv.triviawars.RankingFragment;
import ve.ucv.triviawars.TopicsFragment;
import ve.ucv.triviawars.adapters.DashboardPagerAdapter;

public class DashboardViewPagerFragment extends Fragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        MainActivity mainActivity = (MainActivity)getContext();
        if (mainActivity != null) {
            mainActivity.unlockDrawerNavigation();
        }

        return inflater.inflate(R.layout.fragment_dashboard_view_pager, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tabLayout = view.findViewById(R.id.dashboard_tab_layout);
        viewPager = view.findViewById(R.id.dashboard_pager);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewPager.setAdapter(buildPagerAdapter());
        tabLayout.setupWithViewPager(viewPager);
    }

    private DashboardPagerAdapter buildPagerAdapter() {
        DashboardPagerAdapter pagerAdapter = new DashboardPagerAdapter(getChildFragmentManager());

        pagerAdapter.addFragment(getString(R.string.dashboard_tabs_home), new HomeFragment());
        pagerAdapter.addFragment(getString(R.string.dashboard_tabs_topics), new TopicsFragment());
        pagerAdapter.addFragment(getString(R.string.dashboard_tabs_ranking), new RankingFragment());


        return pagerAdapter;
    }
}
