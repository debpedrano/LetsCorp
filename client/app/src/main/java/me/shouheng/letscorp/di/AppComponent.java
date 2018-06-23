package me.shouheng.letscorp.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import me.shouheng.letscorp.PalmApp;
import me.shouheng.letscorp.di.module.ActivityModule;
import me.shouheng.letscorp.di.module.AppModule;
import me.shouheng.letscorp.di.module.FragmentModule;
import me.shouheng.letscorp.di.module.ViewModelModule;

/**
 * @author shouh
 * @version $Id: AppComponent, v 0.1 2018/6/23 12:27 shouh Exp$
 */
@Singleton
@Component(modules = {ActivityModule.class,
        ViewModelModule.class,
        AppModule.class,
        FragmentModule.class
})
public interface AppComponent extends AndroidInjector<PalmApp> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}

