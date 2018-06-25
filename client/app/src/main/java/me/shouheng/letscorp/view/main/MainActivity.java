package me.shouheng.letscorp.view.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import me.shouheng.letscorp.R;
import me.shouheng.letscorp.common.Constants;
import me.shouheng.letscorp.databinding.ActivityMainBinding;
import me.shouheng.letscorp.view.CommonDaggerActivity;
import me.shouheng.letscorp.view.account.AccountFragment;
import me.shouheng.letscorp.view.favorite.FavoriteFragment;

public class MainActivity extends CommonDaggerActivity<ActivityMainBinding> {

    private final String FRAGMENT_KEY_PAGER = "__key_fragment_pager";
    private final String FRAGMENT_KEY_FAVORITE = "__key_fragment_favorite";
    private final String FRAGMENT_KEY_ACCOUNT = "__key_fragment_account";

    private PagerFragment pagerFragment;
    private FavoriteFragment favoriteFragment;
    private AccountFragment accountFragment;

    private FavoriteChangedReceiver favoriteChangedReceiver;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        initFragments(savedInstanceState);

        getBinding().nav.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.nav_main: showFragment(FRAGMENT_KEY_PAGER);break;
                case R.id.nav_favorite: showFragment(FRAGMENT_KEY_FAVORITE);break;
                case R.id.nav_info: showFragment(FRAGMENT_KEY_ACCOUNT);break;
            }
            return true;
        });

        showFragment(FRAGMENT_KEY_PAGER);

        regBroadcastReceivers();
    }

    private void initFragments(Bundle savedInstanceState) {
        FragmentManager fm = getSupportFragmentManager();
        if (savedInstanceState == null) {
            pagerFragment = PagerFragment.newInstance();
            favoriteFragment = FavoriteFragment.newInstance();
            accountFragment = AccountFragment.newInstance();
        } else {
            pagerFragment = (PagerFragment) fm.getFragment(savedInstanceState, FRAGMENT_KEY_PAGER);
            favoriteFragment = (FavoriteFragment) fm.getFragment(savedInstanceState, FRAGMENT_KEY_FAVORITE);
            accountFragment = (AccountFragment) fm.getFragment(savedInstanceState, FRAGMENT_KEY_ACCOUNT);
        }

        if (!pagerFragment.isAdded()) {
            fm.beginTransaction().add(R.id.fragment_container, pagerFragment, FRAGMENT_KEY_PAGER).commit();
        }
        if (!favoriteFragment.isAdded()) {
            fm.beginTransaction().add(R.id.fragment_container, favoriteFragment, FRAGMENT_KEY_FAVORITE).commit();
        }
        if (!accountFragment.isAdded()) {
            fm.beginTransaction().add(R.id.fragment_container, accountFragment, FRAGMENT_KEY_ACCOUNT).commit();
        }
    }

    private void showFragment(String key) {
        FragmentManager fm = getSupportFragmentManager();
        switch (key) {
            case FRAGMENT_KEY_PAGER:
                fm.beginTransaction()
                        .show(pagerFragment)
                        .hide(favoriteFragment)
                        .hide(accountFragment)
                        .commit();
                break;
            case FRAGMENT_KEY_FAVORITE:
                fm.beginTransaction()
                        .show(favoriteFragment)
                        .hide(pagerFragment)
                        .hide(accountFragment)
                        .commit();
                break;
            case FRAGMENT_KEY_ACCOUNT:
                fm.beginTransaction()
                        .show(accountFragment)
                        .hide(favoriteFragment)
                        .hide(pagerFragment
                        ).commit();
                break;
        }
    }

    private void regBroadcastReceivers() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.ACTION_ARTICLE_FAVORITE);
        favoriteChangedReceiver = new FavoriteChangedReceiver();
        registerReceiver(favoriteChangedReceiver, filter);
    }

    private class FavoriteChangedReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            notifyFavoriteChanged();
        }
    }

    private void notifyFavoriteChanged() {
        if (favoriteFragment != null) favoriteFragment.fetchData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(favoriteChangedReceiver);
    }
}
