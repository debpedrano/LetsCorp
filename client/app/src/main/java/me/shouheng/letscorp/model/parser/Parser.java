package me.shouheng.letscorp.model.parser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

import me.shouheng.letscorp.common.Util;
import me.shouheng.letscorp.model.PostItem;

/**
 * @author shouh
 * @version $Id: Parser, v 0.1 2018/6/23 18:51 shouh Exp$
 */
public class Parser {

    public static List<PostItem> parsePostItems(Document doc, int category) {
        List<PostItem> items = new ArrayList<>();
        for (Element article : doc.select("article.post")) {
            PostItem item = parsePostItem(article, category);
            if (item != null) {
                items.add(item);
            }
        }
        return items;
    }

    public static PostItem parsePostItem(Element article, int category) {
        int id = Util.parseInt(article.id().substring(5));
        String title = article.select(">header>div.entry-title a").text();
        String href = article.select(">header>div.entry-title a").attr("href");
        String img = article.select(">header img").attr("data-original");
        int commentCount = Util.parseInt(article.select(">footer div.comments-link a").text());
        long timestamp = Util.parseDateTime(article.select(">footer time.entry-date.published").attr("datetime"));
        Element contentElement = article.select("div.entry-content").first();
        if (contentElement == null) {
            if ((contentElement = article.select("div.entry-summary").first()) == null) {
                return null;
            }
        }
        for (Element t : contentElement.getAllElements()) {
            if ("a".equals(t.tagName()) || t.text().isEmpty()) {
                t.remove();
            }
        }
        String content = contentElement.html();
        return new PostItem(id, title, href, img, content, commentCount, timestamp, category);
    }
}
