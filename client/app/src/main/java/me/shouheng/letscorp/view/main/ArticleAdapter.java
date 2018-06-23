package me.shouheng.letscorp.view.main;

import android.text.Html;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.Date;
import java.util.LinkedList;

import me.shouheng.commons.util.TimeUtils;
import me.shouheng.letscorp.R;
import me.shouheng.letscorp.model.article.PostItem;

/**
 * @author shouh
 * @version $Id: ArticleAdapter, v 0.1 2018/6/23 19:11 shouh Exp$
 */
public class ArticleAdapter extends BaseQuickAdapter<PostItem, BaseViewHolder> {

    public ArticleAdapter() {
        super(R.layout.item_article, new LinkedList<>());
    }

    @Override
    protected void convert(BaseViewHolder helper, PostItem item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_tags, Html.fromHtml(item.getContent()));
        if (item.getTimestamp() <= 0) {
            helper.getView(R.id.tv_date).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.tv_date).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_date, TimeUtils.getPrettyTime(new Date(item.getTimestamp())));
        }
    }
}
