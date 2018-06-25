package me.shouheng.letscorp.model.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * @author shouh
 * @version $Id: Article, v 0.1 2018/6/24 13:13 shouh Exp$
 */
@Entity
public class Article {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int articleId;

    private String title;

    private String href;

    private String img;

    private String preview;

    private String content;

    private String author;

    private String tags;

    private String categories;

    private long timestamp;

    private Date addedTime;

    public Article(int articleId, String title, String href, String img, String preview, String content,
                   String author, String tags, String categories, long timestamp, Date addedTime) {
        this.articleId = articleId;
        this.title = title;
        this.href = href;
        this.img = img;
        this.preview = preview;
        this.content = content;
        this.author = author;
        this.tags = tags;
        this.categories = categories;
        this.timestamp = timestamp;
        this.addedTime = addedTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Date getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(Date addedTime) {
        this.addedTime = addedTime;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", articleId=" + articleId +
                ", title='" + title + '\'' +
                ", href='" + href + '\'' +
                ", img='" + img + '\'' +
                ", content='" + content + '\'' +
                ", preview='" + preview + '\'' +
                ", author='" + author + '\'' +
                ", tags='" + tags + '\'' +
                ", categories='" + categories + '\'' +
                ", timestamp=" + timestamp +
                ", addedTime=" + addedTime +
                '}';
    }
}
