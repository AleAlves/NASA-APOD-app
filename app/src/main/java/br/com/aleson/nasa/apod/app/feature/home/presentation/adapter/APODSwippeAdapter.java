package br.com.aleson.nasa.apod.app.feature.home.presentation.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import br.com.aleson.nasa.apod.app.feature.home.presentation.fragment.APODFragment;

public class APODSwippeAdapter  extends FragmentPagerAdapter {

    private static final int MAX_ITEMS = 2;
    private static final int FUND_INDEX = 0;
    private static final int FORM_INDEX = 1;

    public APODSwippeAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case FUND_INDEX:
                return APODFragment.newInstance();
            case FORM_INDEX:
                return APODFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return MAX_ITEMS;
    }
}