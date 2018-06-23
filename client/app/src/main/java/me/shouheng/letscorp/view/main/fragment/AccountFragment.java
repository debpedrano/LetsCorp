package me.shouheng.letscorp.view.main.fragment;

import android.os.Bundle;

import me.shouheng.letscorp.R;
import me.shouheng.letscorp.databinding.FragmentAccountBinding;
import me.shouheng.letscorp.view.CommonDaggerFragment;

/**
 * @author shouh
 * @version $Id: AccountFragment, v 0.1 2018/6/23 13:10 shouh Exp$
 */
public class AccountFragment extends CommonDaggerFragment<FragmentAccountBinding> {

    public static AccountFragment newInstance() {
        Bundle args = new Bundle();
        AccountFragment fragment = new AccountFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_account;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {

    }
}
