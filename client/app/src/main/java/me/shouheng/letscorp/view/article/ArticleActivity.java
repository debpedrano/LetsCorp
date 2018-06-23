package me.shouheng.letscorp.view.article;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

import me.shouheng.commons.helper.FragmentHelper;
import me.shouheng.commons.util.PalmUtils;
import me.shouheng.letscorp.R;
import me.shouheng.letscorp.databinding.ActivityArticleBinding;
import me.shouheng.letscorp.model.article.PostItem;
import me.shouheng.letscorp.view.CommonDaggerActivity;

/**
 * @author shouh
 * @version $Id: ArticleActivity, v 0.1 2018/6/23 21:34 shouh Exp$
 */
public class ArticleActivity extends CommonDaggerActivity<ActivityArticleBinding> {

    private final static String EXTRA_POST_ITEM = "__key_extra_post_item";

    private PostItem postItem;

    public static void start(Fragment fragment, PostItem postItem) {
        Intent intent = new Intent(fragment.getContext(), ArticleActivity.class);
        intent.putExtra(EXTRA_POST_ITEM, postItem);
        fragment.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_article;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        handleIntent();

        configToolbar();

        toFragment(ArticleFragment.newInstance(postItem));
    }

    private void handleIntent() {
        Intent intent = getIntent();
        postItem = intent.getParcelableExtra(EXTRA_POST_ITEM);
    }

    private void configToolbar() {
        getBinding().toolbar.setTitle(postItem.getTitle());
        getBinding().toolbar.setTitleTextColor(PalmUtils.getColorCompact(R.color.colorAccent));
        setSupportActionBar(getBinding().toolbar);
    }

    private void toFragment(Fragment fragment) {
        FragmentHelper.replace(this, fragment, R.id.fragment_container);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
