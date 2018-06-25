package me.shouheng.letscorp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import me.shouheng.letscorp.common.async.NormalAsyncTask;
import me.shouheng.letscorp.model.data.Resource;
import me.shouheng.letscorp.model.database.dao.ArticleDao;
import me.shouheng.letscorp.model.database.entity.Article;

/**
 * @author shouh
 * @version $Id: FavoriteViewModel, v 0.1 2018/6/24 21:29 shouh Exp$
 */
public class FavoriteViewModel extends AndroidViewModel {

    private ArticleDao articleDao;

    @Inject
    public FavoriteViewModel(@NonNull Application application, ArticleDao articleDao) {
        super(application);
        this.articleDao = articleDao;
    }

    public LiveData<Resource<List<Article>>> getAll() {
        MutableLiveData<Resource<List<Article>>> result = new MutableLiveData<>();
        new NormalAsyncTask<>(result, () -> articleDao.getAll()).execute();
        return result;
    }

    public LiveData<Resource<Article>> favorite(Article article) {
        MutableLiveData<Resource<Article>> result = new MutableLiveData<>();
        new NormalAsyncTask<>(result, () -> {
            articleDao.save(article);
            return null;
        }).execute();
        return result;
    }
}
