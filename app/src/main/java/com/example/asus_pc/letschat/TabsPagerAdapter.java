package com.example.asus_pc.letschat;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                RequestFragment reqFrag = new RequestFragment();
                return reqFrag;
            case 1:
                ChatFragment chatFrag = new ChatFragment();
                return chatFrag;
            case 2:
                FriendsFragment friendFrag = new FriendsFragment();
                return friendFrag;
            default:
                return null;

        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return "Requests";
            case 1:
                return "Chat";
            case 2:
                return "Friends";
            default:
                return null;

        }

    }
}
