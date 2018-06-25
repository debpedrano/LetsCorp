package me.shouheng.letscorp.view.favorite;

import android.text.Html;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.LinkedList;

import me.shouheng.commons.util.TimeUtils;
import me.shouheng.letscorp.R;
import me.shouheng.letscorp.common.Util;
import me.shouheng.letscorp.model.database.entity.Article;

/**
 * Created by WngShhng on 2018/6/25.*/
public class FavoriteAdapter extends BaseQuickAdapter<Article, BaseViewHolder> {

    FavoriteAdapter() {
        super(R.layout.item_favorite, new LinkedList<>());
    }

    @Override
    protected void convert(BaseViewHolder helper, Article item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_tags,
                item.getAuthor()
                        + " | " + item.getCategories().replace(Util.SPLIT, " ")
                        + " | " + item.getTags().replace(Util.SPLIT, " ")
                        + " | " + TimeUtils.getPrettyTime(item.getAddedTime()));
        helper.setText(R.id.tv_content, Html.fromHtml(item.getPreview()));
    }
}
