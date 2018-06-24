package me.shouheng.letscorp.view.main.fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import me.shouheng.commons.util.PalmUtils;
import me.shouheng.letscorp.R;
import me.shouheng.letscorp.databinding.FragmentFavoriteBinding;
import me.shouheng.letscorp.view.CommonDaggerFragment;
import me.shouheng.letscorp.viewmodel.FavoriteViewModel;

/**
 * @author shouh
 * @version $Id: FavoriteFragment, v 0.1 2018/6/23 13:10 shouh Exp$
 */
public class FavoriteFragment extends CommonDaggerFragment<FragmentFavoriteBinding> {

    @Inject
    FavoriteViewModel favoriteViewModel;

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
        assert getActivity() != null;
        ((AppCompatActivity) getActivity()).setSupportActionBar(getBinding().toolbar);
    }

    private void configList() {
        favoriteViewModel.aasdsa();
    }
}
