package me.shouheng.letscorp.view.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import me.shouheng.commons.util.PalmUtils;
import me.shouheng.letscorp.R;
import me.shouheng.letscorp.databinding.FragmentPagerBinding;
import me.shouheng.letscorp.view.CommonDaggerFragment;
import me.shouheng.letscorp.view.main.PageAdapter;

/**
 * @author shouh
 * @version $Id: PagerFragment, v 0.1 2018/6/23 13:08 shouh Exp$
 */
public class PagerFragment extends CommonDaggerFragment<FragmentPagerBinding> implements TabLayout.OnTabSelectedListener {

    public static PagerFragment newInstance() {
        Bundle args = new Bundle();
        PagerFragment fragment = new PagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_pager;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        configToolbar();

        configPager();
    }

    private void configToolbar() {
        getBinding().toolbar.setTitle(R.string.app_name);
        getBinding().toolbar.setTitleTextColor(PalmUtils.getColorCompact(R.color.colorAccent));
    }

    private void configPager() {
        PageAdapter pageAdapter = new PageAdapter(getFragmentManager());

        getBinding().vp.setAdapter(pageAdapter);
        getBinding().vp.setOffscreenPageLimit(3);

        getBinding().tabLayout.setupWithViewPager(getBinding().vp);
        getBinding().tabLayout.addOnTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) { }

    @Override
    public void onTabReselected(TabLayout.Tab tab) { }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
