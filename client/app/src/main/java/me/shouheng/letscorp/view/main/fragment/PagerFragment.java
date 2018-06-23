package me.shouheng.letscorp.view.main.fragment;

import android.os.Bundle;

import me.shouheng.letscorp.R;
import me.shouheng.letscorp.databinding.FragmentPagerBinding;
import me.shouheng.letscorp.view.CommonDaggerFragment;

/**
 * @author shouh
 * @version $Id: PagerFragment, v 0.1 2018/6/23 13:08 shouh Exp$
 */
public class PagerFragment extends CommonDaggerFragment<FragmentPagerBinding> {

    public static PagerFragment newInstance() {
        Bundle args = new Bundle();
        PagerFragment fragment = new PagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_pager;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {

    }
}
