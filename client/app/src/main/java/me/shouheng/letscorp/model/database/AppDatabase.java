package me.shouheng.letscorp.model.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import me.shouheng.letscorp.model.database.dao.ArticleDao;
import me.shouheng.letscorp.model.database.entity.Article;

/**
 * @author shouh
 * @version $Id: AppDatabase, v 0.1 2018/6/24 13:12 shouh Exp$
 */
@Database(entities = {Article.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract ArticleDao articleDao();

}
