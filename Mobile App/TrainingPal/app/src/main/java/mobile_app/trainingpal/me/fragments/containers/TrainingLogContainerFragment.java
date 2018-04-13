package mobile_app.trainingpal.me.fragments.containers;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mobile_app.trainingpal.me.R;
import mobile_app.trainingpal.me.fragments.containers.training_log.ViewDailyTrainingLogsFragment;
import mobile_app.trainingpal.me.fragments.containers.training_log.ViewMonthlyTrainingLogsFragment;

public class TrainingLogContainerFragment extends Fragment {

    private Toolbar toolbar;
    private View rootView;
    private TrainingLogFragmentAdapter trainingLogFragmentAdapter;
    private ViewPager trainingLogViewPager;
    private TabLayout trainingLogTabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_training_log_container, container, false);

        initialiseVariables();

        return rootView;
    }

    private void initialiseVariables() {
        toolbar = getActivity().findViewById(R.id.toolbar);
        trainingLogFragmentAdapter = new TrainingLogFragmentAdapter(getChildFragmentManager());
        trainingLogViewPager = rootView.findViewById(R.id.training_log_fragment_container);
        trainingLogViewPager.setAdapter(trainingLogFragmentAdapter);
        trainingLogTabLayout = toolbar.findViewById(R.id.nav_tab_profile_layout);
        trainingLogTabLayout.setupWithViewPager(trainingLogViewPager);
    }

    public class TrainingLogFragmentAdapter extends FragmentPagerAdapter {
        public TrainingLogFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new ViewDailyTrainingLogsFragment();
                case 1:
                    return new ViewMonthlyTrainingLogsFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:return "Daily Log";
                case 1:return "Monthly Log";
                default:return null;
            }
        }
    }
}
