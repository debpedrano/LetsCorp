package me.shouheng.letscorp.common;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.customtabs.CustomTabsIntent;
import android.text.TextUtils;
import android.util.TypedValue;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import me.shouheng.commons.util.ColorUtils;
import me.shouheng.commons.util.LogUtils;
import me.shouheng.commons.util.PalmUtils;
import me.shouheng.letscorp.R;
import me.shouheng.letscorp.model.article.Post;
import me.shouheng.letscorp.model.article.PostItem;
import me.shouheng.letscorp.model.database.entity.Article;

/**
 * @author shouh
 * @version $Id: Util, v 0.1 2018/6/23 18:52 shouh Exp$
 */
public class Util {

    private static final String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:sszzz";

    public static final String SPLIT = "@-@";

    public static int parseInt(String str) {
        StringBuilder v = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                v.append(str.charAt(i));
            } else {
                break;
            }
        }
        try {
            return Integer.parseInt(v.toString());
        } catch (Exception e) {
            return 0;
        }
    }

    public static long parseDateTime(String source) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_FORMAT, Locale.US);
            Date date = sdf.parse(source);
            return date.getTime();
        } catch (Exception e) {
            LogUtils.d("source:"+source + ", " + e);
        }
        return 0;
    }

    public static String connect(List<String> categories) {
        if (categories == null) return "";
        int len = categories.size();
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<len; i++) {
            sb.append(categories.get(i));
            if (i != len - 1) {
                sb.append(SPLIT);
            }
        }
        return sb.toString();
    }

    public static List<String> split(String string) {
        List<String> list = new LinkedList<>();
        if (TextUtils.isEmpty(string)) return list;
        String[] arr = string.split(SPLIT);
        list.addAll(Arrays.asList(arr));
        return list;
    }

    public static PostItem getPostItem(Article article) {
        return new PostItem(article.getArticleId(),
                article.getTitle(),
                article.getHref(),
                article.getImg(),
                article.getPreview(),
                0,
                article.getTimestamp());
    }

    public static Post getPost(Article article) {
        return new Post(article.getArticleId(),
                article.getHref(),
                article.getTitle(),
                article.getContent(),
                null,
                null,
                article.getTimestamp(),
                null,
                null);
    }

    public static File getTextExportDir() {
        File dir = new File(getExternalStoragePublicDir(), Constants.TEXT_EXPORT_DIR_NAME);
        if (!dir.exists()) dir.mkdirs();
        return dir;
    }

    public static File getExternalStoragePublicDir() {
        String path = Environment.getExternalStorageDirectory() + File.separator + Constants.EXTERNAL_STORAGE_FOLDER + File.separator;
        File dir = new File(path);
        if (!dir.exists()) dir.mkdirs();
        return dir;
    }

    public static void launchUrl(Context context, String url) {
        boolean isDarkTheme = PrefUtils.getInstance().isNightTheme();
        int primaryColor = PalmUtils.getColorCompact(isDarkTheme ? R.color.nightColorPrimary : R.color.colorPrimary);

        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder
                .setToolbarColor(primaryColor)
                .setSecondaryToolbarColor(ColorUtils.calStatusBarColor(primaryColor))
                .build();
        customTabsIntent.launchUrl(context, Uri.parse(url));
    }
}
