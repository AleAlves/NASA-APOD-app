package br.com.aleson.nasa.apod.app.common.view;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import br.com.aleson.nasa.apod.app.feature.apod.presentation.fragment.APODFragment;

public class UiPagerAdapter extends FragmentStatePagerAdapter {

    private List<APODFragment> fragments = new ArrayList<>();

    public UiPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void add(APODFragment fragment) {
        fragments.add(fragment);
    }

    @Override
    public APODFragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void clear() {
        this.fragments.clear();
    }
}