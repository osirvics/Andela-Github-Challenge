package com.album.andela_github_challenge.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.album.andela_github_challenge.fragments.PlaceholderFragment;
import com.album.andela_github_challenge.models.Item;

import java.util.ArrayList;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
    public ArrayList<Item> items = new ArrayList<>();
    public SectionsPagerAdapter(FragmentManager fm, ArrayList<Item> users) {
        super(fm);
        this.items = users;
        users.remove(items.size()-1);
    }

    @Override
    public Fragment getItem(int position) {
        Item current = items.get(position);
        return PlaceholderFragment.newInstance(position, current.getAvatarUrl(),
               current.getHtml_url(), current.getLogin());
    }

    @Override
    public int getCount() {
        return items.size();

    }
}