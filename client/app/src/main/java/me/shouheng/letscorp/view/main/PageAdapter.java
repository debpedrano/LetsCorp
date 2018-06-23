package me.shouheng.letscorp.view.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import me.shouheng.commons.util.PalmUtils;
import me.shouheng.letscorp.model.CategoryInfo;
import me.shouheng.letscorp.view.main.fragment.PostListFragment;

/**
 * @author shouh
 * @version $Id: PageAdapter, v 0.1 2018/6/23 13:44 shouh Exp$
 */
public class PageAdapter extends FragmentPagerAdapter {

    public PageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return PostListFragment.newInstance(CategoryInfo.getCategoryInfoByOrdinal(position));
    }

    @Override
    public int getCount() {
        return CategoryInfo.values().length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return PalmUtils.getStringCompact(CategoryInfo.getCategoryInfoByOrdinal(position).categoryNameRes);
    }
}
