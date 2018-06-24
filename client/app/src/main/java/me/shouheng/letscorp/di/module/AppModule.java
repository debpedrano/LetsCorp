package me.shouheng.letscorp.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.shouheng.letscorp.common.Constants;
import me.shouheng.letscorp.model.database.AppDatabase;
import me.shouheng.letscorp.model.database.dao.ArticleDao;
import okhttp3.OkHttpClient;

/**
 * @author shouh
 * @version $Id: AppModule, v 0.1 2018/6/23 12:30 shouh Exp$
 */
@Module
public class AppModule {

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
                .followRedirects(true)
                .followSslRedirects(true)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .build();
    }


    @Singleton
    @Provides
    AppDatabase provideDb(Application app) {
        return Room.databaseBuilder(app, AppDatabase.class, Constants.DATABASE_NAME).build();
    }

    @Singleton
    @Provides
    ArticleDao provideArticleDao(AppDatabase db) {
        return db.articleDao();
    }
}
