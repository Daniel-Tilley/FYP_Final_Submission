package mobile_app.trainingpal.me.fragments.containers;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;

import mobile_app.trainingpal.me.R;
import mobile_app.trainingpal.me.fragments.containers.profile.ViewProfileFragment;

public class MyProfileContainerFragment extends Fragment {

    private Toolbar toolbar;
    private View rootView;
    private ProfileFragmentAdapter profileFragmentAdapter;
    private ViewPager profileViewPager;
    private TabLayout profileTabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_my_profile_container, container, false);

        initialiseVariables();

        return rootView;
    }

    private void initialiseVariables() {
        toolbar = getActivity().findViewById(R.id.toolbar);
        profileFragmentAdapter = new ProfileFragmentAdapter(getChildFragmentManager());
        profileViewPager = rootView.findViewById(R.id.my_profile_fragment_container);
        profileViewPager.setAdapter(profileFragmentAdapter);
        profileTabLayout = toolbar.findViewById(R.id.nav_tab_profile_layout);
        profileTabLayout.setupWithViewPager(profileViewPager);
    }

    public class ProfileFragmentAdapter extends FragmentPagerAdapter {
        public ProfileFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new ViewProfileFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:return "My Profile";
                default:return null;
            }
        }
    }
}
