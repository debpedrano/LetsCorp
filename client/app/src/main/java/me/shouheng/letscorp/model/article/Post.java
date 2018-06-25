package me.shouheng.letscorp.model.article;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

/**
 * @author shouh
 * @version $Id: Post, v 0.1 2018/6/23 22:45 shouh Exp$
 */
public class Post implements Serializable {
    private int id;
    private String href;
    private String title;
    private String content;
    private List<String> tags;
    private List<String> categories;
    private long timestamp;
    private String author;
    private List<Comment> comments;

    public static List<Comment> parseComments(String data) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Comment>>() {}.getType();
        return gson.fromJson(data, listType);
    }

    public Post(int id, String href, String title, String content, List<String> tags, List<String> categories, long timestamp, String author, List<Comment> comments) {
        this.id = id;
        this.href = href;
        this.title = title;
        this.content = content;
        this.tags = tags;
        this.categories = categories;
        this.timestamp = timestamp;
        this.author = author;
        this.comments = comments;
    }

    public String getDatetime() {
        if (timestamp <= 0) {
            return "";
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return formatter.format(timestamp);
    }

    public int commentCount() {
        int size = 0;
        for (Comment c : comments) {
            size += c.size();
        }
        return size;
    }

    public String getComments() {
        Gson gson = new Gson();
        return gson.toJson(comments);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", href='" + href + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", tags=" + tags +
                ", categories=" + categories +
                ", timestamp=" + timestamp +
                ", author='" + author + '\'' +
                ", comments=" + comments +
                '}';
    }
}
