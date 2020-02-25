package com.pnit.smartbag.ui.statistics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.pnit.smartbag.R;

public class StatisticsFragment extends Fragment {
import com.pnit.smartbag.ui.profile.daily.DailyFragment;
import com.pnit.smartbag.ui.profile.monthly.MonthlyFragment;
import com.pnit.smartbag.ui.profile.weekly.WeeklyFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

    private StatisticsViewModel statisticsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel = ViewModelProviders.of(this, new ProfileViewModel.Factory(Objects.requireNonNull(getContext()))).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        TabLayout tabLayout = root.findViewById(R.id.tabs);

        int tabCount = 3;
        for (int i = 0; i < tabCount; i++)
            tabLayout.addTab(tabLayout.newTab());

        final ViewPager viewPager = root.findViewById(R.id.viewpager);
        viewPager.setAdapter(new PagerAdapter(getFragmentManager(), tabLayout.getTabCount()));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        return root;
    }

    public class PagerAdapter extends FragmentStatePagerAdapter {
        int mNumOfTabs;
        private String[] tabTitles = new String[]{"Daily", "Weekly", "Monthly"};
        private Fragment[] tabFragment = new Fragment[]{new DailyFragment(), new WeeklyFragment(), new MonthlyFragment()};

        PagerAdapter(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }


        @NotNull
        @Override
        public Fragment getItem(int position) {
            return tabFragment[position];
        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }
    }
}