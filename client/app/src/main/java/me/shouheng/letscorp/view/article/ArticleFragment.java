package me.shouheng.letscorp.view.article;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import javax.inject.Inject;

import me.shouheng.commons.util.ToastUtils;
import me.shouheng.letscorp.R;
import me.shouheng.letscorp.common.AttachmentResolver;
import me.shouheng.letscorp.databinding.FragmentArticleBinding;
import me.shouheng.letscorp.model.article.Post;
import me.shouheng.letscorp.model.article.PostItem;
import me.shouheng.letscorp.view.CommonDaggerFragment;
import me.shouheng.letscorp.viewmodel.LetsCorpViewModel;

/**
 * @author shouh
 * @version $Id: ArticleFragment, v 0.1 2018/6/23 21:53 shouh Exp$
 */
public class ArticleFragment extends CommonDaggerFragment<FragmentArticleBinding> {

    private final static String EXTRA_POST_ITEM = "__key_extra_post_item";

    private PostItem postItem;

    private Post post;

    @Inject
    LetsCorpViewModel letsCorpViewModel;

    private PostAdapter adapter;

    public static ArticleFragment newInstance(PostItem postItem) {
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_POST_ITEM, postItem);
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

        fetchPostDetail();
    }

    private void handleArguments() {
        Bundle arguments = getArguments();
        assert arguments != null;
        postItem = arguments.getParcelable(EXTRA_POST_ITEM);
    }

    private void configList() {
        adapter = new PostAdapter();
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

        getBinding().rv.setAdapter(adapter);
        getBinding().rv.setLayoutManager(new LinearLayoutManager(getContext()));
        getBinding().rv.setEmptyView(getBinding().pb);
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
                break;
            case R.id.action_share:
                break;
            case R.id.action_download:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void favoriteArticle() {

    }

    private void shareArticle() {

    }

    private void downloadArticle() {

    }
}
