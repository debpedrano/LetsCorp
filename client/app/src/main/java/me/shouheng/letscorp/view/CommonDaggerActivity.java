package me.shouheng.letscorp.view;

import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Bundle;

import dagger.android.AndroidInjection;
import me.shouheng.commons.activity.CommonActivity;
import me.shouheng.commons.util.ThemeUtils;
import me.shouheng.letscorp.R;
import me.shouheng.letscorp.common.PrefUtils;

/**
 * @author shouh
 * @version $Id: CommonDaggerActivity, v 0.1 2018/6/6 22:46 shouh Exp$
 */
public abstract class CommonDaggerActivity<T extends ViewDataBinding> extends CommonActivity<T> {

    @Override
    protected void beforeCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        boolean isNightTheme = PrefUtils.getInstance().isNightTheme();
        setTheme(isNightTheme ? R.style.AppThemeDark : R.style.AppTheme);

        if (PrefUtils.getInstance().isNightTheme()) {
            ThemeUtils.setStatusBarColor(this, Color.parseColor("#2a3a4b"));
            getWindow().setBackgroundDrawableResource(R.color.dark_theme_background);
        }
    }

    protected boolean isDarkTheme() {
        return PrefUtils.getInstance().isNightTheme();
    }
}
