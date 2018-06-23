package me.shouheng.letscorp.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import me.shouheng.letscorp.view.main.fragment.AccountFragment;
import me.shouheng.letscorp.view.main.fragment.FavoriteFragment;
import me.shouheng.letscorp.view.main.fragment.PagerFragment;

/**
 * @author shouh
 * @version $Id: FragmentModule, v 0.1 2018/6/23 12:30 shouh Exp$
 */
@Module
public abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract PagerFragment contributePagerFragment();

    @ContributesAndroidInjector
    abstract FavoriteFragment contributeFavoriteFragment();

    @ContributesAndroidInjector
    abstract AccountFragment contributeAccountFragment();
}
