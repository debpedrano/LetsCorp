package me.shouheng.letscorp.view.favorite;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import java.util.Objects;

import javax.inject.Inject;

import me.shouheng.commons.util.PalmUtils;
import me.shouheng.commons.util.ToastUtils;
import me.shouheng.commons.widget.DividerItemDecoration;
import me.shouheng.letscorp.R;
import me.shouheng.letscorp.common.PrefUtils;
import me.shouheng.letscorp.common.Util;
import me.shouheng.letscorp.databinding.FragmentFavoriteBinding;
import me.shouheng.letscorp.model.database.entity.Article;
import me.shouheng.letscorp.view.CommonDaggerFragment;
import me.shouheng.letscorp.view.article.ArticleActivity;
import me.shouheng.letscorp.viewmodel.FavoriteViewModel;

/**
 * @author shouh
 * @version $Id: FavoriteFragment, v 0.1 2018/6/23 13:10 shouh Exp$
 */
public class FavoriteFragment extends CommonDaggerFragment<FragmentFavoriteBinding> implements OnRefreshListener {

    @Inject
    FavoriteViewModel favoriteViewModel;

    private FavoriteAdapter adapter;

    public static FavoriteFragment newInstance() {
        Bundle args = new Bundle();
        FavoriteFragment fragment = new FavoriteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_favorite;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        configToolbar();

        configList();
    }

    private void configToolbar() {
        getBinding().toolbar.setTitle(R.string.nav_bottom_item_2);
        getBinding().toolbar.setTitleTextColor(PalmUtils.getColorCompact(R.color.colorAccent));
    }

    private void configList() {
        getBinding().srl.setOnRefreshListener(this);

        adapter = new FavoriteAdapter();
        adapter.setOnItemClickListener(((adapter1, view, position) -> {
            Article article = Objects.requireNonNull(adapter.getItem(position));
            ArticleActivity.start(FavoriteFragment.this, Util.getPostItem(article), Util.getPost(article));
        }));

        getBinding().rv.setAdapter(adapter);
        getBinding().rv.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()),
                DividerItemDecoration.VERTICAL_LIST, PrefUtils.getInstance().isNightTheme()));
        getBinding().rv.setLayoutManager(new LinearLayoutManager(getContext()));
        getBinding().rv.setEmptyView(getBinding().ev);
        if (PrefUtils.getInstance().isNightTheme()) {
            getBinding().ev.useDarkTheme();
        }
        getBinding().rv.getFastScrollDelegate().setThumbDrawable(PalmUtils.getDrawableCompact(
                isDarkTheme() ? R.drawable.fast_scroll_bar_dark : R.drawable.fast_scroll_bar_light));
        getBinding().rv.getFastScrollDelegate().setThumbSize(16, 40);
        getBinding().rv.getFastScrollDelegate().setThumbDynamicHeight(false);

        fetchData();
        getBinding().srl.setRefreshing(true);
    }

    public void fetchData() {
        favoriteViewModel.getAll().observe(this, listResource -> {
            getBinding().srl.setRefreshing(false);
            if (listResource == null) {
                return;
            }
            switch (listResource.status) {
                case SUCCESS:
                    adapter.setNewData(listResource.data);
                    break;
                case FAILED:
                    ToastUtils.makeToast(listResource.message);
                    break;
            }
        });
    }

    @Override
    public void onRefresh() {
        fetchData();
    }
}
