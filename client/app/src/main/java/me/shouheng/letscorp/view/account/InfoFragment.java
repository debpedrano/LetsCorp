package me.shouheng.letscorp.view.account;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.Objects;

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

        findPreference(R.string.key_night_theme).setOnPreferenceChangeListener((preference, newValue) -> {
            Activity activity = getActivity();
            if (activity != null) {
                getActivity().recreate();
            }
            return true;
        });

        findPreference(R.string.key_about_app).setOnPreferenceClickListener(preference -> {
            showAppInfoDialog();
            return true;
        });
        findPreference(R.string.key_about_developer).setOnPreferenceClickListener(preference -> {
            showDeveloperInfoDialog();
            return true;
        });
        findPreference(R.string.key_associated_apps).setOnPreferenceClickListener(preference -> {
            showAssociatedAppsDialog();
            return true;
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

    private void showAppInfoDialog() {
        AlertDialog dialog = new AlertDialog.Builder(Objects.requireNonNull(getContext()))
                .setTitle(R.string.pref_about_app)
                .setMessage(Html.fromHtml(PalmUtils.getStringCompact(R.string.pref_about_app_details)))
                .setPositiveButton(R.string.text_confirm, null)
                .create();
        dialog.show();
        enableLinkClick(dialog);
    }

    private void showDeveloperInfoDialog() {
        AlertDialog dialog = new AlertDialog.Builder(Objects.requireNonNull(getContext()))
                .setTitle(R.string.pref_about_developer)
                .setMessage(Html.fromHtml(PalmUtils.getStringCompact(R.string.pref_about_developer_details)))
                .setPositiveButton(R.string.text_confirm, null)
                .create();
        dialog.show();
        enableLinkClick(dialog);
    }

    private void showAssociatedAppsDialog() {
        AlertDialog dialog = new AlertDialog.Builder(Objects.requireNonNull(getContext()))
                .setTitle(R.string.pref_associated_apps)
                .setMessage(Html.fromHtml(PalmUtils.getStringCompact(R.string.pref_associated_apps_details)))
                .setPositiveButton(R.string.text_confirm, null)
                .create();
        dialog.show();
        enableLinkClick(dialog);
    }

    private void enableLinkClick(AlertDialog dialog) {
        try {
            Field field = dialog.getClass().getDeclaredField("mAlert");
            field.setAccessible(true);
            Field field2 = (field.get(dialog)).getClass().getDeclaredField("mMessageView");
            field2.setAccessible(true);
            TextView mMessageView = (TextView) field2.get(field.get(dialog));
            mMessageView.setMovementMethod(LinkMovementMethod.getInstance());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public Preference findPreference(@StringRes int keyRes) {
        return super.findPreference(PalmUtils.getStringCompact(keyRes));
    }
}
