package me.shouheng.letscorp.model.article;

import android.support.annotation.StringRes;

import me.shouheng.letscorp.R;
import me.shouheng.letscorp.common.Constants;

/**
 * @author shouh
 * @version $Id: CategoryInfo, v 0.1 2018/6/23 13:35 shouh Exp$
 */
public enum CategoryInfo {
    LETS_CORP_CATEGORY_ALL(0, "", R.string.lets_corp_category_all),
    LETS_CORP_CATEGORY_ECONOMIC(1, "/economics", R.string.lets_corp_category_economic),
    LETS_CORP_CATEGORY_NEWS(2, "/news", R.string.lets_corp_category_news),
    LETS_CORP_CATEGORY_VIEW(3, "/view", R.string.lets_corp_category_view),
    LETS_CORP_CATEGORY_POLITICS(4, "/politics", R.string.lets_corp_category_politics),
    LETS_CORP_CATEGORY_GALLERY(5, "/gallery", R.string.lets_corp_category_gallery),
    LETS_CORP_CATEGORY_RUMOR(6, "/rumor", R.string.lets_corp_category_rumor),
    LETS_CORP_CATEGORY_TECH(7, "/tech", R.string.lets_corp_category_tech),
    LETS_CORP_CATEGORY_HISTORY(8, "/history", R.string.lets_corp_category_history);

    public final int id;
    
    public final String namePath;
    
    @StringRes
    public final int categoryNameRes;

    CategoryInfo(int id, String namePath, int categoryNameRes) {
        this.id = id;
        this.namePath = namePath;
        this.categoryNameRes = categoryNameRes;
    }

    public static CategoryInfo getCategoryInfoByOrdinal(int position) {
        for (CategoryInfo categoryInfo : values()) {
            if (categoryInfo.ordinal() == position) {
                return categoryInfo;
            }
        }
        throw new IllegalArgumentException("Invalid position " + position);
    }

    public String getPostListUrl(int page) {
        return Constants.LETSCORP_HOST + "/archives/category" + this.namePath + "/page/" + page;
    }
}
