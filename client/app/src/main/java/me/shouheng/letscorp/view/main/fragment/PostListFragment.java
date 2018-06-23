package me.shouheng.letscorp.view.main.fragment;

import android.os.Bundle;

import me.shouheng.letscorp.R;
import me.shouheng.letscorp.databinding.FragmentPostListBinding;
import me.shouheng.letscorp.model.CategoryInfo;
import me.shouheng.letscorp.view.CommonDaggerFragment;

/**
 * @author shouh
 * @version $Id: PostListFragment, v 0.1 2018/6/23 13:46 shouh Exp$
 */
public class PostListFragment extends CommonDaggerFragment<FragmentPostListBinding> {

    private final static String EXTRA_CATEGORY_INFO = "__key_extra_category_info";

    private CategoryInfo categoryInfo;

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

    }
}
