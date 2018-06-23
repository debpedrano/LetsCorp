package me.shouheng.letscorp.di.module;

import android.arch.lifecycle.ViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import me.shouheng.letscorp.di.annotation.ViewModelKey;
import me.shouheng.letscorp.viewmodel.LetsCorpViewModel;

/**
 * @author shouh
 * @version $Id: ViewModelModule, v 0.1 2018/6/23 12:30 shouh Exp$
 */
@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LetsCorpViewModel.class)
    abstract ViewModel bindLetsCorpViewModell(LetsCorpViewModel letsCorpViewModel);
}
