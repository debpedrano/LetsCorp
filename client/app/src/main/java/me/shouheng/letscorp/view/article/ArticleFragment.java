package me.shouheng.letscorp.view.article;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.Date;
import java.util.LinkedList;
import java.util.Objects;

import javax.inject.Inject;

import me.shouheng.commons.util.ToastUtils;
import me.shouheng.letscorp.R;
import me.shouheng.letscorp.common.AttachmentResolver;
import me.shouheng.letscorp.common.Constants;
import me.shouheng.letscorp.common.Util;
import me.shouheng.letscorp.databinding.FragmentArticleBinding;
import me.shouheng.letscorp.model.article.Post;
import me.shouheng.letscorp.model.article.PostItem;
import me.shouheng.letscorp.model.database.entity.Article;
import me.shouheng.letscorp.view.CommonDaggerFragment;
import me.shouheng.letscorp.viewmodel.FavoriteViewModel;
import me.shouheng.letscorp.viewmodel.LetsCorpViewModel;

/**
 * @author shouh
 * @version $Id: ArticleFragment, v 0.1 2018/6/23 21:53 shouh Exp$
 */
public class ArticleFragment extends CommonDaggerFragment<FragmentArticleBinding> {

    private final static String EXTRA_POST_ITEM = "__key_extra_post_item";

    private final static String EXTRA_POST = "__key_extra_post";

    private PostItem postItem;

    private Post post;

    @Inject
    LetsCorpViewModel letsCorpViewModel;

    @Inject
    FavoriteViewModel favoriteViewModel;

    private PostAdapter adapter;

    public static ArticleFragment newInstance(PostItem postItem, Post post) {
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_POST_ITEM, postItem);
        if (post != null) args.putSerializable(EXTRA_POST, post);
        ArticleFragment fragment = new ArticleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_article;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        handleArguments();

        configList();

        if (post == null) fetchPostDetail();
    }

    private void handleArguments() {
        Bundle arguments = getArguments();
        assert arguments != null;
        postItem = arguments.getParcelable(EXTRA_POST_ITEM);
        if (arguments.containsKey(EXTRA_POST)) {
            post = (Post) arguments.getSerializable(EXTRA_POST);
        }
    }

    private void configList() {
        adapter = new PostAdapter(post == null ? new LinkedList<>() : PostAdapter.wrap(post.getContent()));
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            PostAdapter.Segment segment = adapter.getItem(position);
            assert segment != null;
            if (segment.type == PostAdapter.SegmentType.IMAGE) {
                AttachmentResolver.resolveClickEvent(getContext(),
                        PostAdapter.getAttachmentFile(segment),
                        PostAdapter.getAttachmentFilss(adapter.getImageSegments()),
                        postItem.getTitle());
            }
        });

        getBinding().rv.setEmptyView(getBinding().pb);
        getBinding().rv.setAdapter(adapter);
        getBinding().rv.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void fetchPostDetail() {
        letsCorpViewModel.fetchPostDetail(postItem.getHref()).observe(this, postResource -> {
            if (postResource == null) {
                return;
            }
            switch (postResource.status) {
                case SUCCESS:
                    assert postResource.data != null;
                    this.post = postResource.data;
                    adapter.setNewData(PostAdapter.wrap(post.getContent()));
                    break;
                case CANCELED:
                    ToastUtils.makeToast(R.string.request_cancelled);
                    break;
                case FAILED:
                    ToastUtils.makeToast(postResource.message);
                    break;
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_article, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:
                favoriteArticle();
                break;
            case R.id.action_share:
                shareArticle();
                break;
            case R.id.action_download:
                downloadArticle();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void favoriteArticle() {
        Article article = new Article(postItem.getId(),
                postItem.getTitle(),
                postItem.getHref(),
                postItem.getImg(),
                postItem.getContent(),
                post.getContent(),
                post.getAuthor(),
                Util.connect(post.getTags()),
                Util.connect(post.getCategories()),
                post.getTimestamp(),
                new Date());
        favoriteViewModel.favorite(article).observe(this, articleResource -> {
            if (articleResource == null) {
                return;
            }
            switch (articleResource.status) {
                case SUCCESS:
                    ToastUtils.makeToast(R.string.favorite_successfully);
                    notifyFavoriteChanged();
                    break;
                case FAILED:
                    ToastUtils.makeToast(articleResource.message);
                    break;
            }
        });
    }

    private void shareArticle() {

    }

    private void downloadArticle() {

    }

    private void notifyFavoriteChanged() {
        Intent intent = new Intent(Constants.ACTION_ARTICLE_FAVORITE);
        Objects.requireNonNull(getActivity()).sendBroadcast(intent);
    }
}
