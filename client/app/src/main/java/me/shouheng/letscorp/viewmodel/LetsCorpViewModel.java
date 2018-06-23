package me.shouheng.letscorp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import org.jsoup.nodes.Document;

import java.util.List;

import javax.inject.Inject;

import me.shouheng.letscorp.model.CategoryInfo;
import me.shouheng.letscorp.model.PostItem;
import me.shouheng.letscorp.model.data.Resource;
import me.shouheng.letscorp.model.parser.Parser;
import me.shouheng.letscorp.model.request.Callback;
import me.shouheng.letscorp.model.request.LetsCorpRequest;

/**
 * @author shouh
 * @version $Id: LetsCorpViewModel, v 0.1 2018/6/23 14:24 shouh Exp$
 */
public class LetsCorpViewModel extends AndroidViewModel{

    private LetsCorpRequest letsCorpRequest;

    @Inject
    public LetsCorpViewModel(@NonNull Application application, LetsCorpRequest letsCorpRequest) {
        super(application);
        this.letsCorpRequest = letsCorpRequest;
    }

    public LiveData<Resource<List<PostItem>>> fetchPostItems(CategoryInfo categoryInfo, int page) {
        MutableLiveData<Resource<List<PostItem>>> result = new MutableLiveData<>();
        letsCorpRequest.get(categoryInfo.getPostListUrl(page), new Callback() {
            @Override
            public void onSuccess(Document doc) {
                List<PostItem> items = Parser.parsePostItems(doc, categoryInfo.id);
                result.setValue(Resource.success(items));
            }

            @Override
            public void onCancelled() {
                result.setValue(Resource.canceled());
            }

            @Override
            public void onError(Exception e) {
                result.setValue(Resource.error(e.getMessage(), null));
            }
        });
        return result;
    }
}
