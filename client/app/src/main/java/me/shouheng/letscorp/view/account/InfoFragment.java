package me.shouheng.letscorp.view.account;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import me.shouheng.commons.util.PalmUtils;
import me.shouheng.letscorp.BuildConfig;
import me.shouheng.letscorp.R;

/**
 * @author shouh
 * @version $Id: InfoFragment, v 0.1 2018/6/25 21:26 shouh Exp$
 */
public class InfoFragment extends PreferenceFragmentCompat {

    public static InfoFragment newInstance() {
        Bundle args = new Bundle();
        InfoFragment fragment = new InfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.info);

        findPreference(R.string.key_update_logs).setSummary(BuildConfig.FLAVOR  + " - " + BuildConfig.VERSION_NAME);

        findPreference(R.string.key_night_theme).setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                return true;
            }
        });

        findPreference(R.string.key_about_app).setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                return true;
            }
        });
        findPreference(R.string.key_about_developer).setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                return true;
            }
        });
        findPreference(R.string.key_associated_apps).setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                return true;
            }
        });
        findPreference(R.string.key_update_logs).setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                return true;
            }
        });
        findPreference(R.string.key_open_source_license).setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                return true;
            }
        });
    }

    public Preference findPreference(@StringRes int keyRes) {
        return super.findPreference(PalmUtils.getStringCompact(keyRes));
    }
}
