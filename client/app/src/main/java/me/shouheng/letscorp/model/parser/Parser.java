package me.shouheng.letscorp.model.parser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

import me.shouheng.letscorp.common.Util;
import me.shouheng.letscorp.model.article.Comment;
import me.shouheng.letscorp.model.article.Post;
import me.shouheng.letscorp.model.article.PostItem;

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

    public static Post parsePost(Element doc, String href) {
        Element article = doc.select("article.post").first();
        int id = Util.parseInt(article.id().substring(5));
        String title = article.select("div.entry-title > h2").text();
        String content = article.select("div.entry-content").html();
        ArrayList<String> tags = new ArrayList<>();
        for (Element a : article.select("p.tags > a")) {
            tags.add(a.text());
        }
        ArrayList<String> categories = new ArrayList<>();
        for (Element t : article.select("p.categories > a")) {
            categories.add(t.text());
        }
        String datetime = article.select("p.date time.entry-date.published").attr("datetime");
        long timestamp = Util.parseDateTime(datetime);
        String author = article.select("p.date a[rel=author]").text();

        List<Comment> comments = new ArrayList<>();

        for (Element li : doc.select("div.comments-area > ol.comment-list > li.comment")) {
            Comment comment = parseComment(li);
            if (comment != null) {
                comments.add(comment);
            }
        }
        return new Post(id, href, title, content, tags, categories, timestamp, author, comments);
    }

    public static Comment parseComment(Element li) {
        try {
            Element article = li.select(">article").first();
            int id = Util.parseInt(article.attr("id").substring(12));
            String avatar = article.select("div.vcard > img").first().attr("data-original");
            String username = article.select("div.vcard > b.fn").first().text();
            long timestamp = Util.parseDateTime(article.select("time").attr("datetime"));
            String content = article.select("div.comment-content > p").outerHtml();
            Comment.CommentCite commentCite = null;
            Element blockquote = article.select("blockquote").first();
            if (blockquote != null) {
                commentCite = parseCommentCite(blockquote);
            }
            List<Comment> children = parseCommentChildren(li.select(">ol.children").first());
            return new Comment(id, username, avatar, timestamp, content, commentCite, children);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static Comment.CommentCite parseCommentCite(Element e) {
        try {
            String id = e.attr("cite").substring(1);
            String username = e.select("strong a").first().ownText();

            e = e.clone();
            e.select("strong").remove();
            e.select("br").remove();
            String content = e.html();
            return new Comment.CommentCite(id, username, content);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static List<Comment> parseCommentChildren(Element ol) {
        List<Comment> children = new ArrayList<>();
        if (ol == null) {
            return children;
        }
        for (Element li : ol.select(">li.comment")) {
            Comment c = parseComment(li);
            if (c != null) {
                children.add(c);
            }
        }
        return children;
    }
}
