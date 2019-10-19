package com.example.codehack;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

class SectionsPagerAdapter extends FragmentPagerAdapter {
    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                UpcomingFragment upcomingFragment=new UpcomingFragment();
                return upcomingFragment;
            case 1:
                ReminderFragment reminderFragment=new ReminderFragment();
                return reminderFragment;
            default:return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
    public CharSequence getPageTitle(int position){
        switch (position){
            case 0:return "UPCOMING";
            case 1:return "REMINDERS";
            default:return null;
        }
    }
}
