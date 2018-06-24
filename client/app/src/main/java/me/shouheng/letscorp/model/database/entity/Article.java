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

    @PrimaryKey
    private int id;

    private int articleId;

    private String title;

    private String href;

    private String img;

    private String content;

    private long timestamp;

    private int category;

    private Date addedTime;

    public Article(int id, int articleId, String title, String href, String img, String content, long timestamp, int category, Date addedTime) {
        this.id = id;
        this.articleId = articleId;
        this.title = title;
        this.href = href;
        this.img = img;
        this.content = content;
        this.timestamp = timestamp;
        this.category = category;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
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
                ", timestamp=" + timestamp +
                ", category=" + category +
                ", addedTime=" + addedTime +
                '}';
    }
}
