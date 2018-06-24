package me.shouheng.letscorp.model.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import me.shouheng.letscorp.model.database.entity.Article;

/**
 * @author shouh
 * @version $Id: ArticleDao, v 0.1 2018/6/24 21:12 shouh Exp$
 */
@Dao
public interface ArticleDao {

    @Insert
    void save(Article article);

    @Query(value = "SELECT * FROM `Article`")
    List<Article> getAll();
}
