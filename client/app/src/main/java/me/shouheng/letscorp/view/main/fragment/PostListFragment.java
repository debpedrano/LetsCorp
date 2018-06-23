package me.shouheng.letscorp.view.main.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import java.util.Objects;

import javax.inject.Inject;

import me.shouheng.commons.util.ToastUtils;
import me.shouheng.commons.widget.DividerItemDecoration;
import me.shouheng.letscorp.R;
import me.shouheng.letscorp.databinding.FragmentPostListBinding;
import me.shouheng.letscorp.model.CategoryInfo;
import me.shouheng.letscorp.model.PostItem;
import me.shouheng.letscorp.view.CommonDaggerFragment;
import me.shouheng.letscorp.view.article.ArticleActivity;
import me.shouheng.letscorp.view.main.ArticleAdapter;
import me.shouheng.letscorp.viewmodel.LetsCorpViewModel;

/**
 * @author shouh
 * @version $Id: PostListFragment, v 0.1 2018/6/23 13:46 shouh Exp$
 */
public class PostListFragment extends CommonDaggerFragment<FragmentPostListBinding> implements SwipeRefreshLayout.OnRefreshListener {

    private final static String EXTRA_CATEGORY_INFO = "__key_extra_category_info";

    private int currentPage = 0;

    private boolean loading = false;

    private CategoryInfo categoryInfo;

    private ArticleAdapter adapter;

    @Inject
    LetsCorpViewModel letsCorpViewModel;

    public static PostListFragment newInstance(CategoryInfo categoryInfo) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CATEGORY_INFO, categoryInfo);
        PostListFragment fragment = new PostListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_post_list;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        handleArguments();

        configList();
    }

    private void handleArguments() {
        Bundle arguments = getArguments();
        assert arguments != null;
        categoryInfo = (CategoryInfo) arguments.getSerializable(EXTRA_CATEGORY_INFO);
    }

    private void configList() {
        getBinding().srl.setOnRefreshListener(this);

        adapter = new ArticleAdapter();
        adapter.setOnItemClickListener((adapter, view, position) ->
                ArticleActivity.start(PostListFragment.this, (PostItem) adapter.getItem(position)));
        adapter.setEnableLoadMore(true);
        adapter.setOnLoadMoreListener(() -> {
            if (loading) return;
            currentPage++;
            fetchPostItems(false);
        }, getBinding().rv);

        getBinding().rv.setAdapter(adapter);
        getBinding().rv.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()),
                DividerItemDecoration.VERTICAL_LIST, false));
        getBinding().rv.setLayoutManager(new LinearLayoutManager(getContext()));

        fetchPostItems(false);
    }

    private void fetchPostItems(boolean refresh) {
        loading = true;
        letsCorpViewModel.fetchPostItems(categoryInfo, currentPage).observe(this, listResource -> {
            loading = false;
            getBinding().srl.setRefreshing(false);
            if (listResource == null) {
                return;
            }
            switch (listResource.status) {
                case SUCCESS:
                    assert listResource.data != null;
                    if (refresh) {
                        adapter.setNewData(listResource.data);
                    } else {
                        adapter.addData(listResource.data);
                    }
                    adapter.notifyDataSetChanged();
                    break;
                case FAILED:
                    ToastUtils.makeToast(listResource.message);
                    break;
                case CANCELED:
                    ToastUtils.makeToast(R.string.request_cancelled);
                    break;
            }
        });
    }

    @Override
    public void onRefresh() {
        currentPage = 0;
        fetchPostItems(true);
    }
}
