package edwy.lugo.ninjachallenge.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import edwy.lugo.ninjachallenge.ui.fragments.WriteNewPostFragment;
import edwy.lugo.ninjachallenge.ui.fragments.AllPostsFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int numOfTabs;

    public PagerAdapter(FragmentManager fragmentManager, int numOfTabs) {
        super(fragmentManager);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                AllPostsFragment allPostsFragment = new AllPostsFragment();
                return allPostsFragment;
            case 1:
                WriteNewPostFragment writeNewPostFragment = new WriteNewPostFragment();
                return writeNewPostFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}