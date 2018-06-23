package me.shouheng.letscorp.view.main.fragment;

import android.os.Bundle;

import me.shouheng.letscorp.R;
import me.shouheng.letscorp.databinding.FragmentFavoriteBinding;
import me.shouheng.letscorp.view.CommonDaggerFragment;

/**
 * @author shouh
 * @version $Id: FavoriteFragment, v 0.1 2018/6/23 13:10 shouh Exp$
 */
public class FavoriteFragment extends CommonDaggerFragment<FragmentFavoriteBinding> {

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

    }
}
