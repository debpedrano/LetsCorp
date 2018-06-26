package me.shouheng.letscorp.common;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.StringRes;

import me.shouheng.commons.util.PalmUtils;
import me.shouheng.letscorp.PalmApp;
import me.shouheng.letscorp.R;

/**
 * @author shouh
 * @version $Id: PrefUtils, v 0.1 2018/6/25 22:28 shouh Exp$
 */
public class PrefUtils {

    private static SharedPreferences mPreferences;

    private static PrefUtils sInstance;

    private PrefUtils() {
        mPreferences = PreferenceManager.getDefaultSharedPreferences(PalmApp.getContext());
    }

    public static PrefUtils getInstance() {
        if (sInstance == null) {
            synchronized (PrefUtils.class) {
                if (sInstance == null){
                    sInstance = new PrefUtils();
                }
            }
        }
        return sInstance;
    }

    public boolean isNightTheme() {
        return mPreferences.getBoolean(getKeyString(R.string.key_night_theme), false);
    }

    private String getKeyString(@StringRes int keyRes) {
        return PalmUtils.getStringCompact(keyRes);
    }
}
