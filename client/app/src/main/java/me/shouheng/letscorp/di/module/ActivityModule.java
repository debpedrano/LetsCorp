package me.shouheng.letscorp.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import me.shouheng.letscorp.di.annotation.ActivityScoped;
import me.shouheng.letscorp.view.article.ArticleActivity;
import me.shouheng.letscorp.view.main.MainActivity;

/**
 * @author shouh
 * @version $Id: ActivityModule, v 0.1 2018/6/23 12:29 shouh Exp$
 */
@Module
public abstract class ActivityModule {

    @ActivityScoped
    @ContributesAndroidInjector
    abstract MainActivity mainActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract ArticleActivity articleActivity();
}
